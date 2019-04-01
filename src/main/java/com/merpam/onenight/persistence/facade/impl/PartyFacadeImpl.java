package com.merpam.onenight.persistence.facade.impl;

import com.merpam.onenight.persistence.facade.PartyFacade;
import com.merpam.onenight.persistence.model.PartyModel;
import com.merpam.onenight.persistence.model.SongModel;
import com.merpam.onenight.persistence.model.UserModel;
import com.merpam.onenight.persistence.service.PartyService;
import com.merpam.onenight.persistence.service.UserService;
import com.merpam.onenight.spotify.service.SpotifyWebService;
import com.merpam.onenight.spotify.service.model.CreatePlaylistResponse;
import com.merpam.onenight.spotify.service.model.SongResponse;
import com.merpam.onenight.utils.WebServiceUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartyFacadeImpl implements PartyFacade {

    private static final int PARTY_NAME_LENGTH = 10;

    private PartyService partyService;
    private UserService userService;

    private SpotifyWebService spotifyWebService;

    @Override
    public PartyModel createParty(String creatorUsername) {
        String spotifyName = RandomStringUtils.randomAlphanumeric(PARTY_NAME_LENGTH);

        CreatePlaylistResponse createPlaylistResponse = spotifyWebService.createPlayList(spotifyName);

        return partyService.createParty(createPlaylistResponse.getId(),
                createPlaylistResponse.getUri(),
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
        SongResponse songResponse = spotifyWebService.getSong(songId); // TODO defensive programming
        spotifyWebService.addSongToPlaylist(party.getId(), songResponse.getUri());

        SongModel song = new SongModel();
        song.setId(songResponse.getId());
        song.setName(songResponse.getName()); //TODO wrap the logic
        song.setUri(songResponse.getUri());
        song.setCreator(user);
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
