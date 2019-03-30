package com.merpam.onenight.scheduler;

import com.merpam.onenight.constants.Constants;
import com.merpam.onenight.persistence.service.PartyService;
import com.merpam.onenight.spotify.service.SpotifyWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ExpiredPlaylistDeletionScheduler {

    private PartyService partyService;
    private SpotifyWebService spotifyWebService;

    @Scheduled(cron = "0 0 0 */1 * *")
    public void runExpiredPlaylistDeletionScheduler() {

        Date today = new Date();
        partyService.findAll()
                .stream()
                .filter(party -> party.getTimestamp() < today.getTime() - Constants.EXPIRATION_INTERVAL)
                .forEach(party -> {
                            partyService.delete(party);
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
