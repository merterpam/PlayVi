package com.merpam.onenight.persistence.service;

import com.merpam.onenight.persistence.model.PartyModel;

import java.util.List;

public interface PartyService {

    List<PartyModel> findAll();

    void delete(PartyModel party);

    PartyModel createParty(String id, String accessLink, String spotifyName, String creatorUsername);

    PartyModel saveParty(PartyModel party);

    PartyModel getParty(String id);

    PartyModel getPartyByPin(String pin);
}
