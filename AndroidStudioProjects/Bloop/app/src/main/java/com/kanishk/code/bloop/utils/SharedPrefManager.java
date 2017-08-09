package com.kanishk.code.bloop.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kanishk.code.bloop.model.Tag;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by kanishk on 12/7/17.
 */

public class SharedPrefManager {
    private static SharedPrefManager sharePref = new SharedPrefManager();
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private static final String CURRENT_FONT = "Current_Font";
    private static final String TAG_LIST = "Tag_List";

    private SharedPrefManager() {}

    private static SharedPreferences getInstance(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public static void saveTagList(Context context, List<Tag> list) {
        SharedPreferences sharedPreferences = SharedPrefManager.getInstance(context);
        Gson gson = new Gson();
        editor = sharedPreferences.edit();
        editor.putString(TAG_LIST, gson.toJson(list));
        editor.apply();
    }

    public static List<Tag> getTagList(Context context) {
        SharedPreferences sharedPreferences = SharedPrefManager.getInstance(context);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Tag>>(){}.getType();
        return gson.fromJson(sharedPreferences.getString(TAG_LIST, ""), type);
    }

    public static void saveCurrentFont(Context context, String name) {
        SharedPreferences sharedPreferences = SharedPrefManager.getInstance(context);
        editor = sharedPreferences.edit();
        editor.putString(CURRENT_FONT, name);
        editor.apply();
    }

    public static String getCurrentFont(Context context) {
        SharedPreferences sharedPreferences = SharedPrefManager.getInstance(context);
        return sharedPreferences.getString(CURRENT_FONT, "");
    }

    public void clearAll() {
        editor.clear();
        editor.commit();
    }
}
