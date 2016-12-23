package com.elzup.init.models;

public class UserEntity {
    private final int id;
    private final String email;

    public UserEntity(int id, String email) {
        this.id = id;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public boolean equals(UserEntity user) {
        return this.id == user.id;
    }
}

