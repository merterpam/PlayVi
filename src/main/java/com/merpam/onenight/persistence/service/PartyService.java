package com.merpam.onenight.persistence.service;

import com.merpam.onenight.persistence.model.Party;

public interface PartyService {

    Party createParty(String id, String accessLink, String spotifyName, String creatorUsername);

    Party saveParty(Party party);

    Party getParty(String id);

    Party getPartyByPin(String pin);
}
