package com.merpam.onenight.persistence.facade;

import com.merpam.onenight.persistence.model.PartyModel;
import com.merpam.onenight.persistence.model.UserModel;

import javax.servlet.http.HttpServletRequest;

public interface PartyFacade {

    /**
     * Creates a new party with user as the creator
     *
     * @param user is the given user
     * @return the created party
     */
    PartyModel createParty(UserModel user);

    /**
     * Gets the party which the current user is part o
     *
     * @param request is the request which contains token about the current user
     * @return the current party
     */
    PartyModel getCurrentParty(HttpServletRequest request);

    /**
     * Gets the party by pin
     *
     * @param pin is the pin
     * @return the party
     */
    PartyModel getPartyByPin(String pin);

    /**
     * Removes the song from the party
     *
     * @param user     is the user who removes the song
     * @param party    is the party of which the song is removed
     * @param songId   is the id of the song
     * @param position is the position of the song in playlist
     * @return the updated party
     */
    PartyModel removeSong(UserModel user, PartyModel party, String songId, int position);

    /**
     * Adds the song to the party
     *
     * @param user   is the user who removes the song
     * @param party  is the party of which the song will be added
     * @param songId is the id of the song
     * @return the updated party
     */
    PartyModel addSong(UserModel user, PartyModel party, String songId);
}
