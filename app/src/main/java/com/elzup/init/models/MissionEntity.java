package com.elzup.init.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.google.gson.annotations.SerializedName;

public class MissionEntity extends BaseObservable {
    private int id;
    @Bindable
    private String title;
    @Bindable
    private String description;
    private UserEntity author;
    @SerializedName("is_completed")
    private boolean isCompleted;
    private boolean isSync;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    @Bindable
    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
        notifyPropertyChanged(BR.completed);
    }

    @Bindable
    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean isSync) {
        this.isSync = isSync;
        notifyPropertyChanged(BR.sync);
    }

    public MissionEntity(int id, String title, String description, UserEntity author, boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.isCompleted = isCompleted;
        this.isSync = false;
    }
}
