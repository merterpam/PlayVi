package com.merpam.onenight.controller;

import com.merpam.onenight.persistence.facade.PartyFacade;
import com.merpam.onenight.persistence.facade.UserFacade;
import com.merpam.onenight.persistence.model.Party;
import com.merpam.onenight.persistence.model.User;
import com.merpam.onenight.session.SessionUser;
import com.merpam.onenight.utils.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/party")
public class PartyController {

    private PartyFacade partyFacade;
    private UserFacade userFacade;

    @PostMapping("/create")
    public Party createParty(@RequestParam("username") String username, HttpServletRequest request) {
        Party party = partyFacade.createParty(username);

        SessionUser sessionUser = SessionUtils.generateSessionUser(party.getCreator().getId(), party.getId(), username);
        SessionUtils.setSessionUser(request, sessionUser);

        return party;
    }

    @PostMapping
    public Party joinParty(@RequestParam("pin") String pin,
                           @RequestParam("username") String username,
                           HttpServletRequest request) {
        Party party = partyFacade.getPartyByPin(pin);

        if(party != null) {
            User user = userFacade.createUser(username);

            SessionUser sessionUser = SessionUtils.generateSessionUser(user.getId(), party.getId(), username);
            SessionUtils.setSessionUser(request, sessionUser);
        }

        return party;
    }

    @GetMapping
    public Party getParty(HttpServletRequest request) {
        return Optional
                .ofNullable(SessionUtils.getSessionUser(request))
                .map(s -> partyFacade.getParty(s.getPartyId()))
                .orElse(null);
    }

    @PostMapping("/addSong")
    public Party addSongToParty(@RequestParam("songId") String songId, HttpServletRequest request) {
        String partyId = Optional
                .ofNullable(SessionUtils.getSessionUser(request))
                .map(SessionUser::getPartyId)
                .orElse(null);

        return partyFacade.addSong(partyId, songId);
    }

    @PutMapping("/removeSong")
    public Party removeSongFromParty(@RequestParam("songId") String songId, HttpServletRequest request) {
        String partyId = Optional
                .ofNullable(SessionUtils.getSessionUser(request))
                .map(SessionUser::getPartyId)
                .orElse(null);

        return partyFacade.removeSong(partyId, songId);
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
