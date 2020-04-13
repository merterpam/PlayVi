package com.merpam.onenight.persistence.facade;

import com.merpam.onenight.persistence.model.PartyModel;
import com.merpam.onenight.persistence.model.UserModel;

import javax.servlet.http.HttpServletRequest;

public interface PartyFacade {

    PartyModel createParty(UserModel creatorUser);

    PartyModel getCurrentParty(HttpServletRequest request);

    PartyModel getPartyById(String id);

    PartyModel getPartyByPin(String pin);

    PartyModel removeSong(UserModel user, PartyModel party, String songId, int position);

    PartyModel addSong(UserModel user, PartyModel party, String songId);
}
