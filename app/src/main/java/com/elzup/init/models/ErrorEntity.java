package com.elzup.init.models;

import android.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;

public class ErrorEntity extends BaseObservable {
    @SerializedName("error")
    private final String message;

    public String getMessage() {
        return message;
    }

    public ErrorEntity(String message) {
        this.message = message;
    }
}


