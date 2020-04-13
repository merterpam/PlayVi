package com.merpam.onenight.controller;

import com.merpam.onenight.dto.UserDTO;
import com.merpam.onenight.persistence.facade.UserFacade;
import com.merpam.onenight.persistence.model.UserModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/users")
@Api(tags = "Users")
public class UsersController {

    private UserFacade userFacade;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(nickname = "createUser",
            value = "Create a user.",
            notes = "Creates a new user.",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO createUser(@ApiParam(value = "User object.", required = true) @RequestBody @Valid UserDTO userDTO,
                              HttpServletResponse response) {
        UserDTO createdUser = getUserFacade().createUser(userDTO);
        getUserFacade().setCurrentUser(createdUser.getId(), response);

        return createdUser;
    }

    @PutMapping("/party/{partyId}")
    @ApiOperation(nickname = "replaceParty",
            value = "Replaces the party.",
            notes = "Replaces the party for the current user.",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void replaceParty(@PathVariable String partyId,
                             HttpServletRequest request) {
        UserModel currentUser = getUserFacade().getCurrentUser(request);
        getUserFacade().replaceParty(partyId, currentUser);
    }

    protected UserFacade getUserFacade() {
        return userFacade;
    }

    @Autowired
    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }
}
