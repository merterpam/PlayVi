package com.merpam.onenight.scheduler;

import com.merpam.onenight.constants.Constants;
import com.merpam.onenight.persistence.service.PartyService;
import com.merpam.onenight.spotify.service.SpotifyWebService;
import com.merpam.onenight.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class ExpiredPlaylistDeletionScheduler {

    private PartyService partyService;
    private SpotifyWebService spotifyWebService;

    @PostConstruct
    public void onStartup() {
        deleteExpiredPlaylists();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void runExpiredPlaylistDeletionScheduler() {
        deleteExpiredPlaylists();
    }

    private void deleteExpiredPlaylists() {
        Date today = new Date();
        partyService.findAll()
                .stream()
                .filter(party -> party.getTimestamp() < DateUtils.getCurrentTimestampInSeconds() - Constants.EXPIRATION_INTERVAL)
                .forEach(party -> {
                            partyService.deleteById(party.getId());
                            spotifyWebService.deletePlaylist(party.getId());
                        }
                );
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
