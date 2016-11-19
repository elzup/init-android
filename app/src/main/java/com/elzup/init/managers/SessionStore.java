package com.elzup.init.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInstaller;

import com.elzup.init.models.RefreshRequestEntity;
import com.elzup.init.models.SessionEntity;

public class SessionStore {
    public static final String PREFERENCES_FILE_NAME = "preference";
    public static Context context = null;

    public static void setContext(Context context) {
        SessionStore.context = context;
    }

    public static boolean isLogin() {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        return settings != null && settings.contains("token");
    }

    public static SessionEntity getSession() {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        int id = settings.getInt("id", 0);
        String username = settings.getString("username", "");
        String token = settings.getString("token", "");
        String password = settings.getString("password", "");
        return new SessionEntity(id, username, token, password);
    }

    public static RefreshRequestEntity getRefreshRequestEntity() {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        String username = settings.getString("username", "");
        String password = settings.getString("password", "");
        return new RefreshRequestEntity(username, password);
    }

    public static void saveSession(SessionEntity sessionEntity) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("id", sessionEntity.getId());
        editor.putString("username", sessionEntity.getUsername());
        editor.putString("token", sessionEntity.getAccessToken());
        editor.putString("password", sessionEntity.getAccessToken());
        editor.apply();
    }
}
