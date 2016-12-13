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
    @SerializedName("author_id")
    private int authorId;
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

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
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

    public MissionEntity(int id, String title, String description, int authorId, boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.authorId = authorId;
        this.isCompleted = isCompleted;
        this.isSync = false;
    }
}
