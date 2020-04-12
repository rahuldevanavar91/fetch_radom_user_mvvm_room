package com.android.application.model.converts;

import androidx.room.TypeConverter;

import com.android.application.model.Dob;
import com.android.application.model.Pictures;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class PictureConverter {
    @TypeConverter
    public static Pictures fromString(String value) {
        Type listType = new TypeToken<Pictures>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(Pictures list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
