package com.merpam.onenight.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(value = "User", description = "Representation of a user object")
public class UserDTO {

    @ApiModelProperty(value = "Unique identifier of user")
    private String id;

    @ApiModelProperty(value = "Name of the user")
    @NotNull
    @Size(min = 1)
    private String username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
