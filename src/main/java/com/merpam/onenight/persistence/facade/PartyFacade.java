package com.merpam.onenight.persistence.facade;

import com.merpam.onenight.persistence.model.Party;

public interface PartyFacade {

    Party createParty(String creatorUsername);

    Party getParty(String id);

    Party getPartyByPin(String pin);

    Party removeSong(String id, String songId);

    Party addSong(String id, String songId);
}
