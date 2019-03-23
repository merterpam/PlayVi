package com.merpam.onenight.persistence.service.impl;

import com.merpam.onenight.persistence.dao.PartyDao;
import com.merpam.onenight.persistence.model.Party;
import com.merpam.onenight.persistence.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartyServiceImpl implements PartyService {

    private PartyDao partyDao;

    @Override
    public Party saveParty(Party party) {
        return partyDao.save(party);
    }

    @Override
    public Party getParty(String id) {
        return partyDao.findById(id).orElse(null);
    }

    @Autowired
    public void setPartyDao(PartyDao partyDao) {
        this.partyDao = partyDao;
    }
}
