package com.merpam.onenight.persistence.dao;

import com.merpam.onenight.persistence.model.PartyModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface PartyDao extends MongoRepository<PartyModel, String> {

    PartyModel findByPin(String pin);

    boolean existsByPin(String pin);
}
