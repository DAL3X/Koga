package de.dal3x.koga.options.datastore;

import android.content.Context;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava2.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava2.RxDataStore;

import de.dal3x.koga.options.Options;
import de.dal3x.koga.util.Names;
import io.reactivex.Flowable;
import io.reactivex.Single;

/** Uses the RXJava library
 * Data storage implemented as a singleton to make sure only one RXDataStore is used per application.
 * Holds options in RAM but keeps them consistent with background storage.
 */
public class OptionsDataStore {

    private static OptionsDataStore instance;

    public static OptionsDataStore getInstance(Context appContext) {
        if (instance == null) {
            instance = new OptionsDataStore(appContext);
            instance.loadDataStoreOptions();
        }
        return instance;
    }

    private final RxDataStore<Preferences> rxDataStore;
    private Options options;

    private OptionsDataStore(Context context) {
        options = new Options();
        rxDataStore = new RxPreferenceDataStoreBuilder(context, Names.OPTIONSSTORE.string).build();
    }

    public Options getOptions() {
        return options;
    }

    public void storeOptions(Options options) {
        this.options = options;
        storeInt(options.getNumberDays(), Names.OPTIONS_DAYS.string);
        storeInt(options.getNumberMeat(), Names.OPTIONS_MEAT.string);
        storeInt(options.getMaxDuplicate(), Names.OPTIONS_DUPLICATE.string);
        storeDouble(options.getMaxHealthScore(), Names.OPTIONS_HEALTH.string);
        storeInt(options.getMaxCarbDuplicates(), Names.OPTIONS_CARBS.string);
    }

    private void loadDataStoreOptions() {
        options = new Options(
                loadInt(Names.OPTIONS_DAYS.string),
                loadInt(Names.OPTIONS_DAYS.string),
                loadInt(Names.OPTIONS_DUPLICATE.string),
                loadDouble(Names.OPTIONS_HEALTH.string),
                loadInt(Names.OPTIONS_CARBS.string)
        );
    }

    private void storeInt(int value, String key) {
        Preferences.Key<Integer> intKey = PreferencesKeys.intKey(key);
        rxDataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(intKey, value);
            return Single.just(mutablePreferences);
        });
    }

    private int loadInt(String key) {
        Preferences.Key<Integer> intKey = PreferencesKeys.intKey(key);
        Flowable<Integer> flow = rxDataStore.data().map(prefs -> prefs.get(intKey));
        try {
            return flow.blockingSingle();
        } catch (NullPointerException e) {
           return 0; // If the value does not exist yet, return a default value of zero.
        }
    }

    private void storeDouble(double value, String key) {
        Preferences.Key<Double> doubleKey = PreferencesKeys.doubleKey(key);
        rxDataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(doubleKey, value);
            return Single.just(mutablePreferences);
        });
    }

    private double loadDouble(String key) {
        Preferences.Key<Double> doubleKey = PreferencesKeys.doubleKey(key);
        Flowable<Double> flow = rxDataStore.data().map(prefs -> prefs.get(doubleKey));
        try {
            return flow.blockingFirst();
        } catch (NullPointerException e) {
            return 0.0; // If the value does not exist yet, return a default value of zero.
        }
    }


}
