package com.merpam.onenight.persistence.service;

import com.merpam.onenight.persistence.model.Party;

import java.util.List;

public interface PartyService {

    List<Party> findAll();

    void delete(Party party);

    Party createParty(String id, String spotifyId, String accessLink, String spotifyName, String creatorUsername);

    Party saveParty(Party party);

    Party getParty(String id);

    Party getPartyByPin(String pin);
}
