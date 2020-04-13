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

    /**
     * Finds the party by id
     *
     * @param id is the given id
     * @return the party
     */
    Optional<PartyModel> findById(String id);

    /**
     * Finds the party by pin
     *
     * @param pin is the given pin
     * @return the party
     */
    Optional<PartyModel> findByPin(String pin);

    /**
     * Deletes the party with the given id
     *
     * @param partyId is the given id
     */
    void deleteById(String partyId);

    /**
     * Creates a new party
     *
     * @param accessLink  is the access link
     * @param creator     is the creator
     * @param spotifyId   is the spotify id
     * @param spotifyName is the spotify name
     * @return the new party
     */
    PartyModel create(String accessLink, UserModel creator, String spotifyId, String spotifyName);

    /**
     * Removes a song from party
     *
     * @param party    is the party
     * @param position is the position of the song to be removed
     * @return the updated party
     */
    PartyModel removeSong(PartyModel party, int position);

    /**
     * Adds a song to party
     *
     * @param party is the party
     * @param song  is the song to be added
     * @return the updated party
     */
    PartyModel addSong(PartyModel party, SongModel song);
}
