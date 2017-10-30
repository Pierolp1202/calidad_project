package com.example.pierolpw10.serviexpress.Managers;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private static final String PREFERENCES_NAME = "serviexpress";
    private static final String PREFERENCE_SESSION = "session";
    private static final String PREFERENCE_MAIL = "mail";
    private static final String PREFERENCE_USERNAME ="username";

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

    public void setPreferenceMail(String mail){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(PREFERENCE_MAIL, mail);
        editor.apply();
    }

    public String getPreferenceMail(){
        return mPreferences.getString(PREFERENCE_MAIL, "");
    }

    public void setPreferenceUsername(String username){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(PREFERENCE_USERNAME, username);
        editor.apply();
    }

    public String getPreferenceUsername(){
        return mPreferences.getString(PREFERENCE_USERNAME, "");
    }
}
