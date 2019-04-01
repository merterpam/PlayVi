package com.merpam.onenight.persistence.facade;

import com.merpam.onenight.persistence.model.PartyModel;

public interface PartyFacade {

    PartyModel createParty(String creatorUsername);

    PartyModel getParty(String id);

    PartyModel getPartyByPin(String pin);

    PartyModel removeSong(String userId, String partyId, String songId, int position);

    PartyModel addSong(String userId, String partyId, String songId);
}
