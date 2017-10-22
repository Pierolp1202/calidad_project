package com.example.pierolpw10.serviexpress.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private static final String PREFERENCES_NAME = "AndroidEzi";

    private static PreferenceManager self;
    private final SharedPreferences mPreferences;
    private final Context context;

    public PreferenceManager(Context context) {
        this.context = context;
        mPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static PreferenceManager getInstance(Context context) {
        if (self == null) {
            self = new PreferenceManager(context);
        }

        return self;
    }
}
