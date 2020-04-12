package com.android.application.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class Location {
    @PrimaryKey(autoGenerate = true)
    private int locationKey;
    private String city;
    private String state;
    private String country;

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

}
