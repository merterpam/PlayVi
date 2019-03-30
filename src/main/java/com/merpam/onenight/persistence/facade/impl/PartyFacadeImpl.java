package com.merpam.onenight.persistence.facade.impl;

import com.merpam.onenight.persistence.facade.PartyFacade;
import com.merpam.onenight.persistence.model.Party;
import com.merpam.onenight.persistence.model.Song;
import com.merpam.onenight.persistence.service.PartyService;
import com.merpam.onenight.spotify.service.SpotifyWebService;
import com.merpam.onenight.spotify.service.model.CreatePlaylistResponse;
import com.merpam.onenight.spotify.service.model.SongResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartyFacadeImpl implements PartyFacade {

    private static final int PARTY_NAME_LENGTH = 10;

    private PartyService partyService;

    private SpotifyWebService spotifyWebService;

    @Override
    public Party createParty(String creatorUsername) {
        String spotifyName = RandomStringUtils.randomAlphanumeric(PARTY_NAME_LENGTH);

        CreatePlaylistResponse createPlaylistResponse = spotifyWebService.createPlayList(spotifyName);

        return partyService.createParty(createPlaylistResponse.getId(),
                createPlaylistResponse.getUri(),
                createPlaylistResponse.getId(),
                spotifyName,
                creatorUsername);
    }

    @Override
    public Party getParty(String id) {
        return partyService.getParty(id);
    }

    @Override
    public Party getPartyByPin(String pin) {
        return partyService.getPartyByPin(pin);
    }

    @Override
    public Party removeSong(String id, String songId) {
        Party party = partyService.getParty(id);

        if (party == null) {
            return null;
        }

        spotifyWebService.removeSongFromPlaylist(id, songId);
        party.getSongList().removeIf(song -> StringUtils.equals(song.getId(), songId));
        return partyService.saveParty(party);

    }

    @Override
    public Party addSong(String id, String songId) {
        Party party = partyService.getParty(id);

        if (party == null) {
            return null;
        }

        spotifyWebService.addSongToPlaylist(party.getSpotifyName(), songId);
        SongResponse songResponse = spotifyWebService.getSong(songId); // TODO defensive programming
        Song song = new Song();
        song.setId(songResponse.getId());
        song.setName(songResponse.getName()); //TODO wrap the logic
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
}
