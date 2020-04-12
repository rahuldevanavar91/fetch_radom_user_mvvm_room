package com.android.application.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.android.application.model.converts.DobConverter;
import com.android.application.model.converts.LocationConverter;
import com.android.application.model.converts.NameConverter;
import com.android.application.model.converts.PictureConverter;

@Entity(tableName = "result_item")
public class ResultItem {

    @PrimaryKey(autoGenerate = true)
    private int key;

    @Ignore
    private int viewType;

    @ColumnInfo(name = "name")
    @TypeConverters(NameConverter.class)
    private Name name;

    @ColumnInfo(name = "location")
    @TypeConverters(LocationConverter.class)
    private Location location;


    @ColumnInfo(name = "dob")
    @TypeConverters(DobConverter.class)
    private Dob dob;

    @ColumnInfo(name = "picture")
    @TypeConverters(PictureConverter.class)
    private Pictures picture;

    @ColumnInfo(name = "member_request_status")
    private int memberRequestStatus;


    public Dob getDob() {
        return dob;
    }

    public int getMemberRequestStatus() {
        return memberRequestStatus;
    }

    public void setMemberRequestStatus(int memberRequestStatus) {
        this.memberRequestStatus = memberRequestStatus;
    }

    public Location getLocation() {
        return location;
    }

    public Name getName() {
        return name;
    }

    public Pictures getPicture() {
        return picture;
    }


    public void setLocation(Location location) {
        this.location = location;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setPicture(Pictures picture) {
        this.picture = picture;
    }

    public void setDob(Dob dob) {
        this.dob = dob;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}

