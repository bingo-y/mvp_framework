package com.bingo.base.data.prefernce;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bingo.base.constant.GlobalConfig;
import com.f2prateek.rx.preferences2.Preference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;

import java.util.Set;

import timber.log.Timber;

import static android.os.Build.VERSION_CODES.HONEYCOMB;

/**
 * Created by tuyx on 2017/4/20.
 * Description :
 */

public class PreferencesManager {
    private volatile static PreferencesManager instance;

    private SharedPreferences preferences;
    private RxSharedPreferences rxPreferences;

    private PreferencesManager(Context context) {
        preferences = context.getSharedPreferences(GlobalConfig.SHARE_PREFERENCES_NAME, Context.MODE_PRIVATE);
        rxPreferences = RxSharedPreferences.create(preferences);
    }

    public static void init(Context context) {
        if (instance == null) {
            synchronized (PreferencesManager.class) {
                if (instance == null) {
                    instance = new PreferencesManager(context);
                }
            }
        }
    }

    public static PreferencesManager getInstance() {
        if (instance == null) {
            Timber.e("class do not init!");
            throw new RuntimeException("class must init!");
        }
        return instance;
    }

    @CheckResult
    @NonNull
    public Preference<Boolean> getRxBoolean(@NonNull String key) {
        return rxPreferences.getBoolean(key);
    }

    /** Create a boolean preference for {@code key} with a default of {@code defaultValue}. */
    @CheckResult @NonNull
    public Preference<Boolean> getRxBoolean(@NonNull String key, @Nullable Boolean defaultValue) {
        return rxPreferences.getBoolean(key, defaultValue);
    }

    /** Create an enum preference for {@code key}. Default is {@code null}. */
    @CheckResult @NonNull
    public <T extends Enum<T>> Preference<T> getRxEnum(@NonNull String key,
                                                     @NonNull Class<T> enumClass) {
        return rxPreferences.getEnum(key, enumClass);
    }

    /** Create an enum preference for {@code key} with a default of {@code defaultValue}. */
    @CheckResult @NonNull
    public <T extends Enum<T>> Preference<T> getRxEnum(@NonNull String key, @Nullable T defaultValue,
                                                     @NonNull Class<T> enumClass) {
        return rxPreferences.getEnum(key, defaultValue, enumClass);
    }

    /** Create a float preference for {@code key}. Default is {@code 0}. */
    @CheckResult @NonNull
    public Preference<Float> getRxFloat(@NonNull String key) {
        return rxPreferences.getFloat(key);
    }

    /** Create a float preference for {@code key} with a default of {@code defaultValue}. */
    @CheckResult @NonNull
    public Preference<Float> getRxFloat(@NonNull String key, @Nullable Float defaultValue) {
        return rxPreferences.getFloat(key, defaultValue);
    }

    /** Create an integer preference for {@code key}. Default is {@code 0}. */
    @CheckResult @NonNull
    public Preference<Integer> getRxInteger(@NonNull String key) {
        //noinspection UnnecessaryBoxing
        return rxPreferences.getInteger(key);
    }

    /** Create an integer preference for {@code key} with a default of {@code defaultValue}. */
    @CheckResult @NonNull
    public Preference<Integer> getRxInteger(@NonNull String key, @Nullable Integer defaultValue) {
        return rxPreferences.getInteger(key, defaultValue);
    }

    /** Create a long preference for {@code key}. Default is {@code 0}. */
    @CheckResult @NonNull
    public Preference<Long> getRxLong(@NonNull String key) {
        //noinspection UnnecessaryBoxing
        return rxPreferences.getLong(key);
    }

    /** Create a long preference for {@code key} with a default of {@code defaultValue}. */
    @CheckResult @NonNull
    public Preference<Long> getRxLong(@NonNull String key, @Nullable Long defaultValue) {
        return rxPreferences.getLong(key, defaultValue);
    }

    /** Create a preference of type {@code T} for {@code key}. Default is {@code null}. */
    @CheckResult @NonNull
    public <T> Preference<T> getRxObject(@NonNull String key, @NonNull Preference.Adapter<T> adapter) {
        return rxPreferences.getObject(key, adapter);
    }

    /**
     * Create a preference for type {@code T} for {@code key} with a default of {@code defaultValue}.
     */
    @CheckResult @NonNull
    public <T> Preference<T> getRxObject(@NonNull String key, @Nullable T defaultValue,
                                       @NonNull Preference.Adapter<T> adapter) {
        return rxPreferences.getObject(key, defaultValue, adapter);
    }

    /** Create a string preference for {@code key}. Default is {@code null}. */
    @CheckResult @NonNull
    public Preference<String> getRxString(@NonNull String key) {
        return rxPreferences.getString(key);
    }

    /** Create a string preference for {@code key} with a default of {@code defaultValue}. */
    @CheckResult @NonNull
    public Preference<String> getRxString(@NonNull String key, @Nullable String defaultValue) {
        return rxPreferences.getString(key, defaultValue);
    }

    /** Create a string set preference for {@code key}. Default is an empty set. */
    @TargetApi(HONEYCOMB)
    @CheckResult @NonNull
    public Preference<Set<String>> getRxStringSet(@NonNull String key) {
        return rxPreferences.getStringSet(key);
    }

    /** Create a string set preference for {@code key} with a default of {@code defaultValue}. */
    @TargetApi(HONEYCOMB)
    @CheckResult @NonNull
    public Preference<Set<String>> getRxStringSet(@NonNull String key,
                                                @NonNull Set<String> defaultValue) {
        return rxPreferences.getStringSet(key, defaultValue);
    }

    /*--------------- normal ---------------*/

    public void putString(@NonNull String key, @NonNull String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value).apply();
    }

    public String getString(@NonNull String key, @NonNull String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    public void putInt(@NonNull String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value).apply();
    }

    public  int getInt(@NonNull String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    public void putLong(@NonNull String key, long value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value).apply();
    }

    public long getLong(@NonNull String key, long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }

    public void putBoolean(@NonNull String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value).apply();
    }

    public boolean getBoolean(@NonNull String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    public void putFloat(@NonNull String key, float value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value).apply();
    }

    public float getFloat(@NonNull String key, float defaultValue) {
        return preferences.getFloat(key, defaultValue);
    }
}
