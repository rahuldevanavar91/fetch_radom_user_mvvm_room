package com.android.application.model.converts;

import androidx.room.TypeConverter;

import com.android.application.model.Dob;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class DobConverter {
    @TypeConverter
    public static Dob fromString(String value) {
        Type listType = new TypeToken<Dob>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(Dob list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
