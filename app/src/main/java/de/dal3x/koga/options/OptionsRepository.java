package de.dal3x.koga.options;

import android.content.Context;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava2.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava2.RxDataStore;

import de.dal3x.koga.util.constants.Names;
import io.reactivex.Flowable;
import io.reactivex.Single;

/** Uses the RXJava library
 * Data storage attribute implemented as a singleton to make sure only one RXDataStore is used per application.
 * Holds options in RAM but keeps them consistent with background storage.
 */
public class OptionsRepository {

    private static RxDataStore<Preferences> rxDataStore;

    private static void initializeDataStore(Context appContext) {
        if (rxDataStore == null) {
            rxDataStore = new RxPreferenceDataStoreBuilder(appContext, Names.OPTIONSSTORE.name()).build();
        }
    }

    private Options options;

    public OptionsRepository(Context context) {
        initializeDataStore(context);
        loadDataStoreOptions();
    }

    public Options getOptions() {
        return options;
    }

    public void storeOptions(Options options) {
        this.options = options;
        storeInt(options.getNumberDays(), Names.OPTIONS_DAYS.name());
        storeInt(options.getNumberMeat(), Names.OPTIONS_MEAT.name());
        storeInt(options.getMaxDuplicate(), Names.OPTIONS_DUPLICATE.name());
        storeDouble(options.getMinHealthScore(), Names.OPTIONS_HEALTH.name());
        storeInt(options.getMaxCarbDuplicates(), Names.OPTIONS_CARBS.name());
    }

    private void loadDataStoreOptions() {
        options = new Options();
        options.setNumberDays(loadIntFlow(Names.OPTIONS_DAYS.name()).blockingFirst());
        options.setNumberMeat(loadIntFlow(Names.OPTIONS_MEAT.name()).blockingFirst());
        options.setMaxDuplicate(loadIntFlow(Names.OPTIONS_DUPLICATE.name()).blockingFirst());
        options.setMinHealthScore(loadDoubleFlow(Names.OPTIONS_HEALTH.name()).blockingFirst());
        options.setMaxCarbDuplicates(loadIntFlow(Names.OPTIONS_CARBS.name()).blockingFirst());
    }

    private void storeInt(int value, String key) {
        Preferences.Key<Integer> intKey = PreferencesKeys.intKey(key);
        rxDataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(intKey, value);
            return Single.just(mutablePreferences);
        });
    }

    private Flowable<Integer> loadIntFlow(String key) {
        Preferences.Key<Integer> intKey = PreferencesKeys.intKey(key);
        return rxDataStore.data().map(prefs -> prefs.get(intKey));
    }

    private void storeDouble(double value, String key) {
        Preferences.Key<Double> doubleKey = PreferencesKeys.doubleKey(key);
        rxDataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(doubleKey, value);
            return Single.just(mutablePreferences);
        });
    }

    private Flowable<Double> loadDoubleFlow(String key) {
        Preferences.Key<Double> doubleKey = PreferencesKeys.doubleKey(key);
        return rxDataStore.data().map(prefs -> prefs.get(doubleKey));
    }


}
