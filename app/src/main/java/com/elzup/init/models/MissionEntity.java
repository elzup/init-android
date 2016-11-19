package com.elzup.init.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;

public class MissionEntity extends BaseObservable {
    private static int id;
    private static String title;
    private static String description;
    @SerializedName("author_id")
    private static int authorId;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        MissionEntity.id = id;
    }

    @Bindable
    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        MissionEntity.title = title;
    }

    @Bindable
    public static String getDescription() {
        return description;
    }

    public static void setDescription(String description) {
        MissionEntity.description = description;
    }

    public static int getAuthorId() {
        return authorId;
    }

    public static void setAuthorId(int authorId) {
        MissionEntity.authorId = authorId;
    }

    public MissionEntity(int id, String title, String description, int authorId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.authorId = authorId;
    }
}
