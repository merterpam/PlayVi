package com.merpam.onenight.persistence.service;

import com.merpam.onenight.persistence.model.PartyModel;

import java.util.List;

public interface PartyService {

    List<PartyModel> findAll();

    void delete(PartyModel party);

    PartyModel createParty(String creatorUsername);

    PartyModel removeSong(String userId, String partyId, String songId, int position);

    PartyModel addSong(String userId, String partyId, String songId);

    PartyModel getParty(String id);

    PartyModel getPartyByPin(String pin);
}
