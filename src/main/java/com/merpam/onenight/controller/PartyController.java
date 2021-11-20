package com.merpam.onenight.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.merpam.onenight.persistence.facade.PartyFacade;
import com.merpam.onenight.persistence.facade.UserFacade;
import com.merpam.onenight.persistence.model.PartyModel;
import com.merpam.onenight.persistence.model.UserModel;
import com.merpam.onenight.session.SecurityConstants;
import com.merpam.onenight.session.SessionUser;
import com.merpam.onenight.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/party")
@CrossOrigin(origins = {"https://dbilgili.github.io/", "https://dbilgili.github.io", "https://github.io", "https://github.io/"},
        allowCredentials = "true",
        exposedHeaders = {SecurityConstants.TOKEN_HEADER})
public class PartyController {

    private PartyFacade partyFacade;
    private UserFacade userFacade;


    @PostMapping("/create")
    public PartyModel createParty(@RequestParam("username") String username, HttpServletResponse response) throws JsonProcessingException {
        PartyModel party = partyFacade.createParty(username);

        SessionUser sessionUser = SessionUtils.generateSessionUser(party.getCreator().getId(), party.getId(), username);
        SessionUtils.setSessionUser(response, sessionUser);

        return party;
    }

    @PostMapping
    public UserModel joinParty(@RequestParam("pin") String pin,
                                @RequestParam("username") String username,
                                HttpServletResponse response) throws JsonProcessingException {
        PartyModel party = partyFacade.getPartyByPin(pin);
        UserModel user = null;

        if (party != null) {
             user = userFacade.createUser(username);

            SessionUser sessionUser = SessionUtils.generateSessionUser(user.getId(), party.getId(), username);
            SessionUtils.setSessionUser(response, sessionUser);
        }

        return user;
    }

    @GetMapping
    public PartyModel getParty(HttpServletRequest request) throws IOException {
        return Optional
                .ofNullable(SessionUtils.getSessionUser(request))
                .map(s -> partyFacade.getParty(s.getPartyId()))
                .orElse(null);
    }

    @PostMapping("/addSong")
    public PartyModel addSongToParty(@RequestParam("songId") String songId, HttpServletRequest request) throws IOException {
        String partyId = null;
        String userId = null;

        SessionUser sessionUser = SessionUtils.getSessionUser(request);
        if (sessionUser != null) {
            partyId = sessionUser.getPartyId();
            userId = sessionUser.getId();
        }

        return partyFacade.addSong(userId, partyId, songId);
    }

    @DeleteMapping("/removeSong")
    public PartyModel removeSongFromParty(@RequestParam("songId") String songId, @RequestParam("position") int position, HttpServletRequest request) throws IOException {
        String partyId = null;
        String userId = null;

        SessionUser sessionUser = SessionUtils.getSessionUser(request);
        if (sessionUser != null) {
            partyId = sessionUser.getPartyId();
            userId = sessionUser.getId();
        }

        return partyFacade.removeSong(userId, partyId, songId, position);
    }

    @Autowired
    public void setPartyFacade(PartyFacade partyFacade) {
        this.partyFacade = partyFacade;
    }

    @Autowired
    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }
}
