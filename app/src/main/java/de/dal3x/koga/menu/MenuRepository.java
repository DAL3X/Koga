package de.dal3x.koga.menu;

import android.content.Context;

import androidx.room.Room;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.dal3x.koga.menu.room.MenuDAO;
import de.dal3x.koga.menu.room.MenuDataBase;
import de.dal3x.koga.util.Names;

/** Uses the room library.
 * Menu repository implements as a singleton to ensure only one database dao for menus exists.
 * It holds a menu map in RAM but keeps it consistent with the background database storage.
*/
public class MenuRepository {

    private static MenuRepository instance;

    public static MenuRepository getInstance(Context appContext) {
        if (instance == null) {
            instance = new MenuRepository(appContext);
        }
        return instance;
    }

    private final Map<String, Menu> menus = new HashMap<>();
    private final MenuDAO dao;
    private final MenuDataBase database;

    public void addMenu(Menu menu) {
        menus.put(menu.getName(), menu);
        MenuDataBase.executor.execute(() -> {dao.insert(menu);});
    }

    public void deleteMenu(String name) {
        menus.remove(name);
        Menu shadowMenu = new Menu(name);
        MenuDataBase.executor.execute(() -> {dao.delete(shadowMenu);});
    }

    public List<Menu> getAllMenus() {
        return new LinkedList<>(menus.values());
    }

    private MenuRepository(Context appContext) {
        database = Room.databaseBuilder(appContext, MenuDataBase.class, Names.MENUSTORE.string).build();
        dao = database.menuDAO();
        getDataBaseMenus();
    }

    private void getDataBaseMenus() {
        MenuDataBase.executor.execute(() -> {
            List<Menu> menuList = dao.getAll().blockingGet();
            for (Menu menu : menuList) {
                menus.put(menu.getName(), menu);
            }
        });
    }
}
