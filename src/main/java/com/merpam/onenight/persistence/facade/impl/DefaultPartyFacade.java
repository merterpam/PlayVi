package com.merpam.onenight.persistence.facade.impl;

import com.merpam.onenight.persistence.facade.PartyFacade;
import com.merpam.onenight.persistence.model.PartyModel;
import com.merpam.onenight.persistence.service.PartyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DefaultPartyFacade implements PartyFacade {

    @Resource
    private PartyService partyService;

    @Override
    public PartyModel createParty(String creatorUsername) {
        return partyService.createParty(creatorUsername);
    }

    @Override
    public PartyModel getPartyById(String id) {
        return partyService.findPartyById(id);
    }

    @Override
    public PartyModel getPartyByPin(String pin) {
        return partyService.findPartyByPin(pin);
    }

    @Override
    public PartyModel removeSong(String userId, String partyId, String songId, int position) {
        return partyService.removeSong(userId, partyId, songId, position);
    }

    @Override
    public PartyModel addSong(String userId, String partyId, String songId) {
        return partyService.addSong(userId, partyId, songId);
    }

}
