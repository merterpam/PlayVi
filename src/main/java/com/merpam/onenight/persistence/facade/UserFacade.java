package com.merpam.onenight.persistence.facade;

import com.merpam.onenight.persistence.model.UserModel;

public interface UserFacade {
    UserModel createUser(String username);
}
