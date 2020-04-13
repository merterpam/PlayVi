package com.merpam.onenight.controller;

import com.merpam.onenight.persistence.facade.PartyFacade;
import com.merpam.onenight.persistence.facade.UserFacade;
import com.merpam.onenight.persistence.model.PartyModel;
import com.merpam.onenight.persistence.model.UserModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;

@RestController
@RequestMapping("/parties")
@Api(tags = "Parties")
public class PartiesController {

    private PartyFacade partyFacade;

    private UserFacade userFacade;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(nickname = "createParty",
            value = "Create a party",
            notes = "Creates a new party for the current user.",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PartyModel createParty(HttpServletRequest request) {
        UserModel currentUser = getUserFacade().getCurrentUser(request);
        PartyModel party = getPartyFacade().createParty(currentUser);

        getUserFacade().replaceParty(party.getId(), currentUser);

        return party;
    }

    @GetMapping
    @ApiOperation(nickname = "getParty",
            value = "Get a party",
            notes = "Gets a party based on the given pin.",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PartyModel getParty(@QueryParam("pin") String pin) {
        return getPartyFacade().getPartyByPin(pin);
    }

    @PostMapping("/songs")
    @ApiOperation(nickname = "addSong",
            value = "Add a song",
            notes = "Adds a song to the current party.",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PartyModel addSong(@RequestParam("songId") String songId,
                              HttpServletRequest request) {

        UserModel currentUser = getUserFacade().getCurrentUser(request);
        PartyModel currentParty = getPartyFacade().getCurrentParty(request);

        return getPartyFacade().addSong(currentUser, currentParty, songId);
    }

    @DeleteMapping("/songs")
    @ApiOperation(nickname = "removeSong",
            value = "Remove a song",
            notes = "Removes a song from the current party.",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PartyModel removeSong(@RequestParam("songId") String songId,
                                 @RequestParam("position") int position,
                                 HttpServletRequest request) {
        UserModel currentUser = getUserFacade().getCurrentUser(request);
        PartyModel currentParty = getPartyFacade().getCurrentParty(request);

        return getPartyFacade().removeSong(currentUser, currentParty, songId, position);
    }

    @Autowired
    public void setPartyFacade(PartyFacade partyFacade) {
        this.partyFacade = partyFacade;
    }

    @Autowired
    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    protected PartyFacade getPartyFacade() {
        return partyFacade;
    }

    protected UserFacade getUserFacade() {
        return userFacade;
    }
}
