package com.merpam.onenight.persistence.facade.impl;

import com.merpam.onenight.persistence.facade.PartyFacade;
import com.merpam.onenight.persistence.facade.UserFacade;
import com.merpam.onenight.persistence.model.Party;
import com.merpam.onenight.persistence.service.PartyService;
import com.merpam.onenight.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class PartyFacadeImpl implements PartyFacade {

    private PartyService partyService;
    private UserFacade userFacade;

    @Override
    public Party createParty(String creatorUsername) {
        Party party = new Party();
        party.setAccessLink("");
        party.setCreator(userFacade.createUser(creatorUsername));
        party.setSongList(Collections.emptyList());
        party.setPin("");
        party.setTimestamp(DateUtils.getCurrentTimestampInSeconds());

        return partyService.saveParty(party);
    }

    @Override
    public Party getParty(String id) {
        return partyService.getParty(id);
    }

    @Override
    public Party removeSong(String id, String songId) {
        Party party = partyService.getParty(id);

        if (party == null) {
            return null;
        }

        party.getSongList().removeIf(song -> StringUtils.equals(song.getId(), songId));
        return partyService.saveParty(party);

    }

    @Autowired
    public void setPartyService(PartyService partyService) {
        this.partyService = partyService;
    }

    @Autowired
    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }
}
