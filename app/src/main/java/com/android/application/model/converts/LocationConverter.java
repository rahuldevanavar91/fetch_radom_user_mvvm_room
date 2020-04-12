package com.android.application.model.converts;

import androidx.room.TypeConverter;

import com.android.application.model.Location;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class LocationConverter {
    @TypeConverter
    public static Location fromString(String value) {
        Type listType = new TypeToken<Location>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(Location list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
