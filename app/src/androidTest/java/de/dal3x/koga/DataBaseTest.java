package de.dal3x.koga;

import android.content.Context;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import de.dal3x.koga.data.room.MenuDAO;
import de.dal3x.koga.data.room.MenuDataBase;
import de.dal3x.koga.data.room.Recipe;
import de.dal3x.koga.menu.Carbohydrate;
import de.dal3x.koga.menu.HealthScore;
import de.dal3x.koga.menu.Menu;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DataBaseTest {

    private List<Menu> menus = new LinkedList<>();

    private void addMenu(MenuDAO dao, Menu menu) throws InterruptedException {
        CompletableObserver insertObserver = new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}
            @Override
            public void onComplete() {}
            @Override
            public void onError(@NonNull Throwable e) {}
        };
        assertNotNull(menu);
        dao.insert(menu).subscribe(insertObserver);
        getMenus(dao);
    }

    private void getMenus(MenuDAO dao) throws InterruptedException {
        SingleObserver<List<Menu>> queryObserver = new SingleObserver<>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onSuccess(@NonNull List<Menu> menuMap) {
                menus = menuMap;
            }

            @Override
            public void onError(@NonNull Throwable e) {
                fail();
            }
        };
        dao.getAll().subscribe(queryObserver);
    }

    private void deleteMenu(MenuDAO dao, Menu menu) throws InterruptedException {
        CompletableObserver deleteObserver = new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}
            @Override
            public void onComplete() {}
            @Override
            public void onError(@NonNull Throwable e) {}
        };
        assertNotNull(menu);
        dao.delete(menu).subscribe(deleteObserver);
        getMenus(dao);
    }

    @Test
    public void testDB() throws InterruptedException {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        MenuDataBase db = Room.databaseBuilder(appContext, MenuDataBase.class, "database-name").build();
        MenuDAO menuDao = db.menuDAO();

        // Add User 1
        Menu menu = new Menu("test1", new Recipe(new LinkedList<>()), 3, HealthScore.NORMAL, true, Carbohydrate.BREAD, "");
        addMenu(menuDao, menu);
        // Check if one user is present
        assertEquals(1, menus.size());
        assertEquals("test1", menus.get(0).getName());
        // Add User 2
        Menu menu2 = new Menu("test2", new Recipe(new LinkedList<>()), 3, HealthScore.NORMAL, true, Carbohydrate.BREAD, "");
        addMenu(menuDao, menu2);
        // Check if both users are present
        assertEquals(2, menus.size());
        assertEquals("test1", menus.get(0).getName());
        assertEquals(2, menus.size());
        assertEquals("test2", menus.get(1).getName());
        // Remove both users
        deleteMenu(menuDao, menu);
        deleteMenu(menuDao, menu2);
        // Check if users list is empty
        assertEquals(0, menus.size());
    }
}