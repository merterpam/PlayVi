package com.merpam.onenight.persistence.service.impl;

import com.merpam.onenight.persistence.dao.PartyDao;
import com.merpam.onenight.persistence.model.PartyModel;
import com.merpam.onenight.persistence.service.PartyService;
import com.merpam.onenight.persistence.service.UserService;
import com.merpam.onenight.utils.DateUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PartyServiceImpl implements PartyService {

    private static final int PIN_LENGTH = 5;

    private UserService userService;
    private PartyDao partyDao;

    @Override
    public List<PartyModel> findAll() {
        return partyDao.findAll();
    }

    @Override
    public void delete(PartyModel party) {
        partyDao.delete(party);
    }

    @Override
    public PartyModel createParty(String id, String accessLink, String spotifyName, String creatorUsername) {
        PartyModel party = new PartyModel();
        party.setId(id);
        party.setAccessLink(accessLink);
        party.setSpotifyName(spotifyName);
        party.setCreator(userService.createUser(creatorUsername));
        party.setPin(generateUniquePin());
        party.setSongList(Collections.emptyList());
        party.setTimestamp(DateUtils.getCurrentTimestampInSeconds());

        return partyDao.insert(party);
    }

    private String generateUniquePin() {
        String pin;
        do {
            pin = RandomStringUtils.randomNumeric(PIN_LENGTH);
        } while (partyDao.existsByPin(pin));

        return pin;
    }

    @Override
    public PartyModel saveParty(PartyModel party) {
        return partyDao.save(party);
    }

    @Override
    public PartyModel getParty(String id) {
        if(id == null) {
            return null;
        }

        return partyDao.findById(id).orElse(null);
    }

    @Override
    public PartyModel getPartyByPin(String pin) {
        if(pin == null) {
            return null;
        }

        return partyDao.findByPin(pin);
    }

    @Autowired
    public void setPartyDao(PartyDao partyDao) {
        this.partyDao = partyDao;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
