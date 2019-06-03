package com.merpam.onenight.persistence.service.impl;

import com.github.javafaker.Faker;
import com.merpam.onenight.persistence.dao.PartyDao;
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
import com.merpam.onenight.utils.DateUtils;
import com.merpam.onenight.utils.WebServiceUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartyServiceImpl implements PartyService {

    private static final int PIN_LENGTH = 5;

    private UserService userService;

    private PartyDao partyDao;

    private SpotifyWebService spotifyWebService;

    @Override
    public List<PartyModel> findAll() {
        return partyDao.findAll();
    }

    @Override
    public void delete(PartyModel party) {
        partyDao.delete(party);
    }

    @Override
    public PartyModel createParty(String creatorUsername) {

        Faker faker = new Faker();

        String spotifyName = faker.color().name() + faker.cat().name();

        CreatePlaylistResponse createPlaylistResponse = spotifyWebService.createPlayList(spotifyName);
        String accessLink = createPlaylistResponse.getUri();
        if (StringUtils.isBlank(accessLink)) {
            throw new IllegalArgumentException("accessLink cannot be blank");
        }

        PartyModel party = new PartyModel();
        party.setId(createPlaylistResponse.getId());
        party.setAccessLink(accessLink);
        party.setSpotifyName(spotifyName);
        party.setCreator(userService.createUser(creatorUsername));
        party.setPin(generateUniquePin());
        party.setSongList(Collections.emptyList());
        party.setTimestamp(DateUtils.getCurrentTimestampInSeconds());

        return partyDao.insert(party);
    }

    @Override
    public PartyModel removeSong(String userId, String partyId, String songId, int position) {
        synchronized (partyId.intern()) {
            PartyModel party = getParty(partyId);
            UserModel user = userService.findById(userId);

            if (party == null || user == null || WebServiceUtils.isSongRemovable(party, songId, position, user)) {
                return null;
            }

            boolean deleted = spotifyWebService.removeSongFromPlaylist(partyId, songId, position);
            if (deleted) {
                party.getSongList().remove(position);
                party = partyDao.save(party);
            }

            return party;
        }
    }

    @Override
    public PartyModel addSong(String userId, String partyId, String songId) {
        synchronized (partyId.intern()) {
            PartyModel party = getParty(partyId);
            UserModel user = userService.findById(userId);

            if (party == null || user == null) {
                return null;
            }

            if (party.getSongList().stream().anyMatch(song -> StringUtils.equals(song.getId(), songId))) {
                return party; //Avoid adding duplicate songs, request from front-end
            }

            SongResponse songResponse = spotifyWebService.getSong(songId);
            spotifyWebService.addSongToPlaylist(party.getId(), songResponse.getUri());

            SongModel song = new SongModel();
            song.setId(songResponse.getId());
            song.setName(songResponse.getName());
            song.setUri(songResponse.getUri());
            song.setCreator(user);
            song.setDuration(songResponse.getDuration_ms());
            song.setArtists(Arrays.stream(songResponse.getArtists()).map(artist -> new ArtistModel(artist.getId(), artist.getName())).collect(Collectors.toList()));
            song.setAlbumCoverUrl(Arrays.stream(songResponse.getAlbum().getImages()).findFirst().map(ImageResponse::getUrl).orElse(StringUtils.EMPTY));

            party = getParty(partyId);
            party.getSongList().add(song);
            return partyDao.save(party);
        }
    }

    private String generateUniquePin() {
        String pin;
        do {
            pin = RandomStringUtils.randomNumeric(PIN_LENGTH);
        } while (partyDao.existsByPin(pin));

        return pin;
    }

    @Override
    public PartyModel getParty(String id) {
        if (id == null) {
            return null;
        }

        return partyDao.findById(id).orElse(null);
    }

    @Override
    public PartyModel getPartyByPin(String pin) {
        if (pin == null) {
            return null;
        }

        return partyDao.findByPin(pin);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPartyDao(PartyDao partyDao) {
        this.partyDao = partyDao;
    }

    @Autowired
    public void setSpotifyWebService(SpotifyWebService spotifyWebService) {
        this.spotifyWebService = spotifyWebService;
    }
}
