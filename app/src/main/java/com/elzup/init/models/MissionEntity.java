package com.elzup.init.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;

public class MissionEntity extends BaseObservable {
    private int id;
    private String title;
    private String description;
    @SerializedName("author_id")
    private int authorId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public MissionEntity(int id, String title, String description, int authorId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.authorId = authorId;
    }
}
