package com.merpam.onenight.persistence.dao;

import com.merpam.onenight.persistence.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface UserDao extends MongoRepository<UserModel, String> {

}
