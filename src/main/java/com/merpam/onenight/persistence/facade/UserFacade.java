package com.merpam.onenight.persistence.facade;

import com.merpam.onenight.persistence.model.User;

public interface UserFacade {
    User createUser(String username);
}
