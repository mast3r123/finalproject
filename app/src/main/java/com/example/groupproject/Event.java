package com.example.groupproject;

import java.util.List;

public class Event {

    private String id;
    private String name;
    private String group;
    private String host_name;
    private String host_image;
    private String host_description;
    private String duration;
    private String description;
    private List<String> images;

    public Event(String id, String name, String group, String host_name, String host_image, String host_description, String duration, String description, List<String> images) {
        this.id = id;
        this.name = name;
        this.group = group;
        this.host_name = host_name;
        this.host_image = host_image;
        this.host_description = host_description;
        this.duration = duration;
        this.description = description;
//        this.images = images;
    }

    public Event() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getHost_name() {
        return host_name;
    }

    public void setHost_name(String host_name) {
        this.host_name = host_name;
    }

    public String getHost_image() {
        return host_image;
    }

    public void setHost_image(String host_image) {
        this.host_image = host_image;
    }

    public String getHost_description() {
        return host_description;
    }

    public void setHost_description(String host_description) {
        this.host_description = host_description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
