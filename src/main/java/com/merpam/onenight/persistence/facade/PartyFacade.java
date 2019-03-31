package com.merpam.onenight.persistence.facade;

import com.merpam.onenight.persistence.model.Party;

public interface PartyFacade {

    Party createParty(String creatorUsername);

    Party getParty(String id);

    Party getPartyByPin(String pin);

    Party removeSong(String userId, String partyId, String songId, int position);

    Party addSong(String userId, String partyId, String songId);
}
