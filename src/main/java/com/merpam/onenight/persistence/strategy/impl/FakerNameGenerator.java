package com.merpam.onenight.persistence.strategy.impl;

import com.github.javafaker.Faker;
import com.merpam.onenight.persistence.strategy.NameGenerator;
import org.springframework.stereotype.Service;

@Service
public class FakerNameGenerator implements NameGenerator {

    @Override
    public String generateName() {
        final Faker faker = new Faker();
        return faker.color().name() + faker.cat().name();
    }
}
