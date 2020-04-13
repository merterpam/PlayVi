package com.merpam.onenight.session.model;

public class SessionUser {

    private String id;

    public SessionUser() {
        // deliberately empty constructor for serialization
    }

    public SessionUser(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
