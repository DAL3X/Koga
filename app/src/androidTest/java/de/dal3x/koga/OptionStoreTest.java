package de.dal3x.koga;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import de.dal3x.koga.options.Options;
import de.dal3x.koga.options.datastore.OptionsDataStore;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class OptionStoreTest {
    @Test
    public void testDS() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        OptionsDataStore.initializeInstance(appContext);
        OptionsDataStore dataStore = OptionsDataStore.getInstance();

        assertEquals(0, dataStore.getOptions().getNumberDays());

        Options opt = new Options(1, 1, 1, 1.1, 1);
        dataStore.storeOptions(opt);

        assertEquals(1, dataStore.getOptions().getNumberDays());
    }
}