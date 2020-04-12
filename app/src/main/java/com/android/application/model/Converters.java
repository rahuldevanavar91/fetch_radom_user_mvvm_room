package com.android.application.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    @TypeConverter
    public static List<ResultItem> fromString(String value) {
        Type listType = new TypeToken<List<ResultItem>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<ResultItem> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
