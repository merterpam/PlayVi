package com.merpam.onenight.persistence.service;

import com.merpam.onenight.persistence.model.Party;

public interface PartyService {

    Party saveParty(Party party);

    Party getParty(String id);
}
