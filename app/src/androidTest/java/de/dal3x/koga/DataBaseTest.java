package de.dal3x.koga;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.HashMap;

import de.dal3x.koga.menu.MenuRepository;
import de.dal3x.koga.menu.room.Recipe;
import de.dal3x.koga.menu.Carbohydrate;
import de.dal3x.koga.menu.HealthScore;
import de.dal3x.koga.menu.Menu;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DataBaseTest {

    @Test
    public void testRepository() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        MenuRepository.initializeInstance(appContext);
        MenuRepository menus = MenuRepository.getInstance();

        // Add Menu 1
        Menu menu = new Menu("test1", new Recipe(new HashMap<>()), 3, HealthScore.NORMAL, true, Carbohydrate.BREAD, "");
        menus.addMenu(menu);

        // Check if one user is present
        assertEquals(1, menus.getAllMenus().size());
        assertTrue(menus.getAllMenus().contains(menu));

        // Add Menu 2
        Menu menu2 = new Menu("test2", new Recipe(new HashMap<>()), 2, HealthScore.NORMAL, true, Carbohydrate.PASTA, "");
        menus.addMenu(menu2);

        // Check if both users are present
        assertEquals(2, menus.getAllMenus().size());
        assertTrue(menus.getAllMenus().contains(menu));
        assertTrue(menus.getAllMenus().contains(menu2));

        // Remove both users and check if list is empty
        menus.deleteMenu(menu.getName());
        menus.deleteMenu(menu2.getName());
        assertTrue(menus.getAllMenus().isEmpty());
    }
}