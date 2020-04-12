package com.android.application.model.converts;

import androidx.room.TypeConverter;

import com.android.application.model.Name;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class NameConverter {
    @TypeConverter
    public static Name fromString(String value) {
        Type listType = new TypeToken<Name>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(Name list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
