package com.merpam.onenight.persistence.service.impl;

import com.merpam.onenight.persistence.dao.PartyDao;
import com.merpam.onenight.persistence.model.PartyModel;
import com.merpam.onenight.persistence.model.SongModel;
import com.merpam.onenight.persistence.model.UserModel;
import com.merpam.onenight.persistence.service.PartyService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartyServiceImpl implements PartyService {

    private static final int PIN_LENGTH = 4;

    private PartyDao partyDao;

    @Override
    public List<PartyModel> findAll() {
        return getPartyDao().findAll();
    }

    @Override
    public void deleteById(String partyId) {
        getPartyDao().deleteById(partyId);
    }

    @Override
    public PartyModel create(String accessLink, UserModel creator, String spotifyId, String spotifyName) {

        String pin = generateUniquePin();

        PartyModel party = new PartyModel(accessLink, creator.getId(), spotifyId, pin, spotifyName);

        return getPartyDao().insert(party);
    }

    @Override
    public PartyModel removeSong(PartyModel party, int position) {
        party.getSongList().remove(position);
        return getPartyDao().save(party);
    }


    @Override
    public PartyModel addSong(PartyModel party, SongModel song) {
        party.getSongList().add(song);
        return getPartyDao().save(party);
    }

    private String generateUniquePin() {
        String pin;
        do {
            pin = RandomStringUtils.randomNumeric(PIN_LENGTH);
        } while (getPartyDao().existsByPin(pin));

        return pin;
    }

    @Override
    public Optional<PartyModel> findById(String id) {
        return Optional.ofNullable(id).flatMap(getPartyDao()::findById);
    }

    @Override
    public Optional<PartyModel> findByPin(String pin) {
        return Optional.ofNullable(pin).map(getPartyDao()::findByPin);
    }

    @Autowired
    public void setPartyDao(PartyDao partyDao) {
        this.partyDao = partyDao;
    }

    protected PartyDao getPartyDao() {
        return partyDao;
    }
}
