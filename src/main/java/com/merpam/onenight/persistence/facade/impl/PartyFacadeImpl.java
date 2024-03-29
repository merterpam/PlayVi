package com.merpam.onenight.persistence.facade.impl;

import com.merpam.onenight.persistence.facade.PartyFacade;
import com.merpam.onenight.persistence.model.PartyModel;
import com.merpam.onenight.persistence.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartyFacadeImpl implements PartyFacade {

    private PartyService partyService;

    @Override
    public PartyModel createParty(String creatorUsername) {
        return partyService.createParty(creatorUsername);
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
        return partyService.removeSong(userId, partyId, songId, position);
    }

    @Override
    public PartyModel addSong(String userId, String partyId, String songId) {
        return partyService.addSong(userId, partyId, songId);
    }

    @Autowired
    public void setPartyService(PartyService partyService) {
        this.partyService = partyService;
    }
}
