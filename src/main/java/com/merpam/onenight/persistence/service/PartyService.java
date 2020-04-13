package com.merpam.onenight.persistence.service;

import com.merpam.onenight.persistence.model.PartyModel;
import com.merpam.onenight.persistence.model.SongModel;
import com.merpam.onenight.persistence.model.UserModel;

import java.util.List;
import java.util.Optional;

public interface PartyService {

    /**
     * Finds and returns all parties
     *
     * @return all parties in
     */
    List<PartyModel> findAll();

    Optional<PartyModel> findById(String id);

    Optional<PartyModel> findByPin(String pin);

    /**
     * Deletes the party with the given id
     *
     * @param partyId is the given id
     */
    void deleteById(String partyId);

    PartyModel create(String accessLink, UserModel creator, String spotifyId, String spotifyName);

    PartyModel removeSong(PartyModel song, int position);

    PartyModel addSong(PartyModel party, SongModel song);
}
