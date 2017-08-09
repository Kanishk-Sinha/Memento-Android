package com.kanishk.code.shutterfly.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kanishk on 12/7/17.
 */

public class SharedPrefManager {
    private static SharedPrefManager sharePref = new SharedPrefManager();
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private static final String PAST_SEARCH = "Past_Search";

    private SharedPrefManager() {}

    private static SharedPreferences getInstance(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public static void saveSearchStringList(Context context, String string) {
        SharedPreferences sharedPreferences = SharedPrefManager.getInstance(context);
        editor = sharedPreferences.edit();
        editor.putString(PAST_SEARCH, string);
        editor.apply();
    }

    public static String getSearchStringList(Context context) {
        SharedPreferences sharedPreferences = SharedPrefManager.getInstance(context);
        return sharedPreferences.getString(PAST_SEARCH, "");
    }

    public void clearAll() {
        editor.clear();
        editor.commit();
    }
}
