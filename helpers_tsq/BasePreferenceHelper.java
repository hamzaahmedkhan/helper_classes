package com.app.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.ingic.garagediscount.activities.MainActivity;
import com.ingic.garagediscount.entities.User;

import java.util.Locale;

public class BasePreferenceHelper extends PreferenceHelper {

    protected static final String KEY_LOGIN_STATUS = "islogin";
    protected static final String KEY_LOGIN_STATUS_SOCIAL = "osSocial";
    protected static final String KEY_USER = "user";
    public static final String KEY_DEVICE_TOKEN = "device_token";
    public static final String KEY_DEFAULT_LANG = "language_key";
    public static final String KEY_DEFAULT_SETTING = "language_key+sseting";
    public static final String KEY_LOCATION = "location";
    private Context context;
    private static final String FILENAME = "preferences";
    private static final String FILENAME_Lang = "preferencesLang";

    public BasePreferenceHelper(Context c) {
        this.context = c;
    }

    public SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(FILENAME, Activity.MODE_PRIVATE);
    }

    public void removeUser() {
        removePreference(context, FILENAME, KEY_USER);
    }

    public void setLoginStatus(boolean isLogin) {
        putBooleanPreference(context, FILENAME, KEY_LOGIN_STATUS, isLogin);
    }

    public boolean getLoginStatus() {
        return getBooleanPreference(context, FILENAME, KEY_LOGIN_STATUS);
    }


    public void setLoginStatusSocial(boolean isLogin) {
        putBooleanPreference(context, FILENAME, KEY_LOGIN_STATUS_SOCIAL, isLogin);
    }

    public boolean getLoginStatusSocial() {
        return getBooleanPreference(context, FILENAME, KEY_LOGIN_STATUS_SOCIAL);
    }


    public void putDeviceToken(String token) {
        putStringPreference(context, FILENAME, KEY_DEVICE_TOKEN, token);
    }


    public String getDeviceToken() {
        return getStringPreference(context, FILENAME, KEY_DEVICE_TOKEN);
    }


    public void putUser(User user) {
        putStringPreference(context, FILENAME, KEY_USER, new GsonBuilder()
                .create().toJson(user));
    }

    public User getUser() {
        return new GsonBuilder().create().fromJson(
                getStringPreference(context, FILENAME, KEY_USER), User.class);
    }

    public void clear() {
        removePreference(context, FILENAME, KEY_LOGIN_STATUS);

    }


    public void putLang(Activity activity, String lang, boolean isRestartRequired) {
        Log.v("lang", "|" + lang);
        Resources resources = context.getResources();

        if (lang.equals("ar"))
            lang = "ar";
        else
            lang = "en";

        putStringPreference(context, FILENAME, KEY_DEFAULT_LANG, lang);

        DisplayMetrics dm = resources.getDisplayMetrics();
        android.content.res.Configuration conf = resources.getConfiguration();
        conf.locale = new Locale(lang);
        resources.updateConfiguration(conf, dm);

        activity.recreate();

        if (isRestartRequired) {
            Intent intent = new Intent(activity, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            activity.finish();
            activity.startActivity(intent);
        }
    }

    public void putLang1(Activity activity, String lang, boolean isRestartRequired) {
        Log.v("lang", "|" + lang);
        Resources resources = context.getResources();

        if (lang.equals("ar"))
            lang = "ar";
        else
            lang = "en";

        putStringPreference(context, FILENAME, KEY_DEFAULT_LANG, lang);

        DisplayMetrics dm = resources.getDisplayMetrics();
        android.content.res.Configuration conf = resources.getConfiguration();
        conf.locale = new Locale(lang);
        resources.updateConfiguration(conf, dm);


        if (isRestartRequired) {
            activity.recreate();

        }
    }


    public String getLang() {
        return getStringPreference(context, FILENAME, KEY_DEFAULT_LANG);
    }

    public boolean isLanguageArabic() {
        return getLang().equalsIgnoreCase("ar");
    }


    public void putLocaleFromSetting(boolean isFromSetting) {
        putBooleanPreference(context, FILENAME, KEY_DEFAULT_SETTING, isFromSetting);
    }

    public boolean isLocaleFromSetting() {
        return getBooleanPreference(context, FILENAME, KEY_DEFAULT_SETTING);
    }
}
