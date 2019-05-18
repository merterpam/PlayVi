package com.merpam.onenight.persistence.facade.impl;

import com.github.javafaker.Faker;
import com.merpam.onenight.persistence.facade.PartyFacade;
import com.merpam.onenight.persistence.model.ArtistModel;
import com.merpam.onenight.persistence.model.PartyModel;
import com.merpam.onenight.persistence.model.SongModel;
import com.merpam.onenight.persistence.model.UserModel;
import com.merpam.onenight.persistence.service.PartyService;
import com.merpam.onenight.persistence.service.UserService;
import com.merpam.onenight.spotify.service.SpotifyWebService;
import com.merpam.onenight.spotify.service.model.CreatePlaylistResponse;
import com.merpam.onenight.spotify.service.model.ImageResponse;
import com.merpam.onenight.spotify.service.model.SongResponse;
import com.merpam.onenight.utils.WebServiceUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class PartyFacadeImpl implements PartyFacade {

    private PartyService partyService;
    private UserService userService;

    private SpotifyWebService spotifyWebService;

    @Override
    public PartyModel createParty(String creatorUsername) {

        Faker faker = new Faker();

        String spotifyName = faker.color().name() + faker.cat().name();

        CreatePlaylistResponse createPlaylistResponse = spotifyWebService.createPlayList(spotifyName);
        String accessLink = createPlaylistResponse.getUri();
        if (StringUtils.isBlank(accessLink)) {
            throw new IllegalArgumentException("accessLink cannot be blank");
        }

        return partyService.createParty(createPlaylistResponse.getId(),
                accessLink,
                spotifyName,
                creatorUsername);
    }

    @Override
    public PartyModel getParty(String id) {
        return partyService.getParty(id);
    }

    @Override
    public PartyModel getPartyByPin(String pin) {
        return partyService.getPartyByPin(pin);
    }

    @Override
    public PartyModel removeSong(String userId, String partyId, String songId, int position) {
        PartyModel party = partyService.getParty(partyId);
        UserModel user = userService.findById(userId);

        if (party == null || user == null || WebServiceUtils.isSongRemovable(party, songId, position, user)) {
            return null;
        }

        spotifyWebService.removeSongFromPlaylist(partyId, songId, position);
        party.getSongList().remove(position);
        return partyService.saveParty(party);

    }

    @Override
    public PartyModel addSong(String userId, String partyId, String songId) {
        PartyModel party = partyService.getParty(partyId);
        UserModel user = userService.findById(userId);

        if (party == null || user == null) {
            return null;
        }

        if (party.getSongList().stream().anyMatch(song -> StringUtils.equals(song.getId(), songId))) {
            return party; //Avoid adding duplicate songs, request from front-end
        }

        SongResponse songResponse = spotifyWebService.getSong(songId); // TODO defensive programming
        spotifyWebService.addSongToPlaylist(party.getId(), songResponse.getUri());

        SongModel song = new SongModel();
        song.setId(songResponse.getId());
        song.setName(songResponse.getName()); //TODO wrap the logic to populator
        song.setUri(songResponse.getUri());
        song.setCreator(user);
        song.setDuration(songResponse.getDuration_ms());
        song.setArtists(Arrays.stream(songResponse.getArtists()).map(artist -> new ArtistModel(artist.getId(), artist.getName())).collect(Collectors.toList()));
        song.setAlbumCoverUrl(Arrays.stream(songResponse.getAlbum().getImages()).findFirst().map(ImageResponse::getUrl).orElse(StringUtils.EMPTY));
        party.getSongList().add(song);
        return partyService.saveParty(party);
    }

    @Autowired
    public void setPartyService(PartyService partyService) {
        this.partyService = partyService;
    }

    @Autowired
    public void setSpotifyWebService(SpotifyWebService spotifyWebService) {
        this.spotifyWebService = spotifyWebService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
