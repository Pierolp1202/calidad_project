package com.example.pierolpw10.serviexpress.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private static final String PREFERENCES_NAME = "AndroidEzi";
    private static final String PREFERENCE_SESSION = "session";

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

    public void setPrefenceSession(String session){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(PREFERENCE_SESSION, session);
        editor.apply();
    }

    public String getPreferenceSession(){
        return mPreferences.getString(PREFERENCE_SESSION, "");
    }
}
