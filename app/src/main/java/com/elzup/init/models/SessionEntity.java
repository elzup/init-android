package com.elzup.init.models;

import com.google.gson.annotations.SerializedName;

public class SessionEntity {
    private final int id;
    private final String email;

    @SerializedName("token_type")
    private final String tokenType;

    @SerializedName("access_token")
    private final String accessToken;

    public SessionEntity(int id, String email, String tokenType, String accessToken) {
        this.id = id;
        this.email = email;
        this.tokenType = tokenType;
        this.accessToken = accessToken;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }
}

