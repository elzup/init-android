package com.elzup.init.models;

public class RefreshRequestEntity {
    private final String username;
    private final String password;

    public RefreshRequestEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
