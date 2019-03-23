package com.merpam.onenight.persistence.facade;

import com.merpam.onenight.persistence.model.Party;

public interface PartyFacade {

    Party createParty(String creatorUsername);

    Party getParty(String id);

    Party removeSong(String id, String songId);
}
