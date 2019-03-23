package com.merpam.onenight.session;

public class SessionUser {

    private String id;

    private String partyId;

    private String username;

    public SessionUser(String id, String partyId, String username) {
        this.id = id;
        this.partyId = partyId;
        this.username = username;
    }


    public String getId() {
        return id;
    }

    public String getPartyId() {
        return partyId;
    }

    public String getUsername() {
        return username;
    }
}
