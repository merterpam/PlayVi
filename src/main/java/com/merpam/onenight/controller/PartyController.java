package com.merpam.onenight.controller;

import com.merpam.onenight.persistence.facade.PartyFacade;
import com.merpam.onenight.persistence.facade.UserFacade;
import com.merpam.onenight.persistence.model.PartyModel;
import com.merpam.onenight.persistence.model.UserModel;
import com.merpam.onenight.session.SessionUser;
import com.merpam.onenight.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/party")
@CrossOrigin(exposedHeaders = {"JSESSONID"}, allowCredentials = "true")
public class PartyController {

    private PartyFacade partyFacade;
    private UserFacade userFacade;

    @PostMapping("/create")
    public PartyModel createParty(@RequestParam("username") String username, HttpServletRequest request) {
        PartyModel party = partyFacade.createParty(username);

        SessionUser sessionUser = SessionUtils.generateSessionUser(party.getCreator().getId(), party.getId(), username);
        SessionUtils.setSessionUser(request, sessionUser);

        return party;
    }

    @PostMapping
    public PartyModel joinParty(@RequestParam("pin") String pin,
                                @RequestParam("username") String username,
                                HttpServletRequest request) {
        PartyModel party = partyFacade.getPartyByPin(pin);

        if (party != null) {
            UserModel user = userFacade.createUser(username);

            SessionUser sessionUser = SessionUtils.generateSessionUser(user.getId(), party.getId(), username);
            SessionUtils.setSessionUser(request, sessionUser);
        }

        return party;
    }

    @GetMapping
    public PartyModel getParty(HttpServletRequest request) {
        return Optional
                .ofNullable(SessionUtils.getSessionUser(request))
                .map(s -> partyFacade.getParty(s.getPartyId()))
                .orElse(null);
    }

    @PostMapping("/addSong")
    public PartyModel addSongToParty(@RequestParam("songId") String songId, HttpServletRequest request) {
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
    public PartyModel removeSongFromParty(@RequestParam("songId") String songId, @RequestParam("position") int position, HttpServletRequest request) {
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
