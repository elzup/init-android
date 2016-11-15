package com.elzup.init.models;

public class MissionEntity {
    public int id;
    public String title;
    public String description;
    public int author_id;

    public MissionEntity(int id, String title, String description, int author_id) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author_id = author_id;
    }
}
