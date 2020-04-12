package com.android.application.model;

import androidx.room.Entity;

@Entity(tableName = "id")
public class Id {
    String value;

    public String getValue() {
        return value;
    }
}
