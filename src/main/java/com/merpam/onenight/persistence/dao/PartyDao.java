package com.merpam.onenight.persistence.dao;

import com.merpam.onenight.persistence.model.PartyModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface PartyDao extends MongoRepository<PartyModel, String> {

    /**
     * Finds the party by pin
     *
     * @param pin is the given pin
     * @return the party
     */
    PartyModel findByPin(String pin);

    /**
     * Checks if the given pin already exists
     *
     * @param pin is the given pin
     * @return true if the pin already exists, false otherwise
     */
    boolean existsByPin(String pin);
}
