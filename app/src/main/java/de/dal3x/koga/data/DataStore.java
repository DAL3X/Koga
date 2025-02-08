package de.dal3x.koga.data;

import android.content.Context;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava2.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava2.RxDataStore;

import de.dal3x.koga.util.Names;
import io.reactivex.Flowable;
import io.reactivex.Single;

// DataStore wrapper implementing a singleton to make sure only one RXDataStore is used per application.
public class DataStore {

    private static DataStore instance;

    private RxDataStore<Preferences> rxDataStore;

    private DataStore(){} // Never use this constructor
    private DataStore(Context context) {
        rxDataStore = new RxPreferenceDataStoreBuilder(context, Names.DATASTORE.string).build();
    }

    public static DataStore getInstance(Context context) {
        if (instance == null) {
            instance = new DataStore(context);
        }
        return instance;
    }


    public Single<Preferences> storeInt(int value, String key) {
        Preferences.Key<Integer> intKey = PreferencesKeys.intKey(key);
        return rxDataStore.updateDataAsync(prefsIn -> {
           MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
          mutablePreferences.set(intKey, value);
          return Single.just(mutablePreferences);
        });
    }

    public int loadInt(String key) {
        Preferences.Key<Integer> intKey = PreferencesKeys.intKey(key);
        Flowable<Integer> test_flow = rxDataStore.data().map(prefs -> prefs.get(intKey));
        return test_flow.blockingFirst();
    }


}
