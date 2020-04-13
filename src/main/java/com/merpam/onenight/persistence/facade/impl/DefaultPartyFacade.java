package com.merpam.onenight.persistence.facade.impl;

import com.merpam.onenight.persistence.facade.PartyFacade;
import com.merpam.onenight.persistence.model.ArtistModel;
import com.merpam.onenight.persistence.model.PartyModel;
import com.merpam.onenight.persistence.model.SongModel;
import com.merpam.onenight.persistence.model.UserModel;
import com.merpam.onenight.persistence.service.PartyService;
import com.merpam.onenight.persistence.service.UserService;
import com.merpam.onenight.persistence.strategy.NameGenerator;
import com.merpam.onenight.spotify.service.SpotifyWebService;
import com.merpam.onenight.spotify.service.model.CreatePlaylistResponse;
import com.merpam.onenight.spotify.service.model.ImageResponse;
import com.merpam.onenight.spotify.service.model.SongResponse;
import com.merpam.onenight.utils.WebServiceUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DefaultPartyFacade implements PartyFacade {

    @Autowired
    protected PartyService partyService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected SpotifyWebService spotifyWebService;

    @Autowired
    protected NameGenerator nameGenerator;

    @Override
    public PartyModel createParty(UserModel creator) {
        String spotifyName = nameGenerator.generateName();
        CreatePlaylistResponse createPlaylistResponse = spotifyWebService.createPlayList(spotifyName);

        if (StringUtils.isBlank(createPlaylistResponse.getUri())) {
            throw new IllegalArgumentException("accessLink cannot be blank");
        }

        return partyService.create(createPlaylistResponse.getUri(),
                creator,
                createPlaylistResponse.getId(),
                spotifyName);
    }

    @Override
    public PartyModel getCurrentParty(final HttpServletRequest request) {
        final Optional<UserModel> currentUser = userService.getCurrentUser(request);

        return currentUser
                .map(UserModel::getPartyId)
                .flatMap(partyService::findById)
                .orElseThrow(() -> new NotFoundException("Current party cannot be found"));
    }

    @Override
    public PartyModel getPartyById(String id) {
        return partyService.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public PartyModel getPartyByPin(String pin) {
        return partyService.findByPin(pin).orElseThrow(NotFoundException::new);
    }

    @Override
    public PartyModel removeSong(UserModel user, PartyModel party, String songId, int position) {
        if (party == null) {
            throw new IllegalArgumentException("Party cannot be null");
        }

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (!WebServiceUtils.isSongRemovable(party, songId, position, user.getId())) {
            throw new IllegalArgumentException("Song cannot be removed");
        }

        synchronized (party.getId().intern()) {
            boolean deleted = spotifyWebService.removeSongFromPlaylist(party.getSpotifyId(), songId, position);
            if (deleted) {
                party = partyService.removeSong(party, position);
            }

            return party;
        }
    }

    @Override
    public PartyModel addSong(UserModel user, PartyModel party, String songId) {
        if (party == null) {
            throw new IllegalArgumentException("Party cannot be null");
        }

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        synchronized (party.getId().intern()) {

            if (party.getSongList().stream().anyMatch(song -> StringUtils.equals(song.getId(), songId))) {
                return party; //Avoid adding duplicate songs, request from front-end
            }

            SongResponse songResponse = spotifyWebService.getSong(songId);
            spotifyWebService.addSongToPlaylist(party.getSpotifyId(), songResponse.getUri());

            // TODO converter
            SongModel song = new SongModel();
            song.setId(songResponse.getId());
            song.setName(songResponse.getName());
            song.setUri(songResponse.getUri());
            song.setCreatorId(user.getId());
            song.setDuration(songResponse.getDuration_ms());
            song.setArtists(Arrays.stream(songResponse.getArtists())
                    .map(artist -> new ArtistModel(artist.getId(), artist.getName()))
                    .collect(Collectors.toList()));
            song.setAlbumCoverUrl(Arrays.stream(songResponse.getAlbum().getImages())
                    .findFirst()
                    .map(ImageResponse::getUrl)
                    .orElse(StringUtils.EMPTY));

            return partyService.addSong(party, song);
        }
    }

}
