package com.example.viewflipper_cricleindicator;
import java.io.Serializable;
import java.util.List;

public class ImagesSlider implements Serializable {
    private int id;
    private int position;
    private String avatar;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
