package com.example.sehatqassignment.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    public static final String EWILL_SHAREDPREF = "sehatqSp";
    public SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context) {
        sp = context.getSharedPreferences(EWILL_SHAREDPREF, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void setUserLoggedIn(boolean bool) {
        spEditor.putBoolean("is_logged_in", bool).commit();
    }

    public boolean isUserLoggedIn() {
        return sp.getBoolean("is_logged_in", false);
    }
}
