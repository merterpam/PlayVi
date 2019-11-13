package com.merpam.onenight.persistence.service;

import com.merpam.onenight.persistence.model.PartyModel;

import java.util.List;

public interface PartyService {

    /**
     * Finds and returns all parties
     * @return all parties in
     */
    List<PartyModel> findAll();

    /**
     * Deletes the party with the given id
     * @param partyId is the given id
     */
    void deleteById(String partyId);

    PartyModel createParty(String creatorUsername);

    PartyModel removeSong(String userId, String partyId, String songId, int position);

    PartyModel addSong(String userId, String partyId, String songId);

    PartyModel findPartyById(String id);

    PartyModel findPartyByPin(String pin);
}
