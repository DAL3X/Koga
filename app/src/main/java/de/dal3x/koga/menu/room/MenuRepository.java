package de.dal3x.koga.menu.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import de.dal3x.koga.menu.Menu;

public class MenuRepository {

    private final LiveData<List<Menu>> menus;
    private final MenuDao dao;

    public MenuRepository(Application app) {
        MenuDataBase database = MenuDataBase.getDatabase(app);
        dao = database.menuDao();
        menus = dao.getAll();
    }

    public void addMenu(Menu menu) {
        MenuDataBase.executor.execute(() -> {dao.insert(menu);});
    }

    public void deleteMenu(Menu menu) {
        MenuDataBase.executor.execute(() -> {dao.delete(menu);});
    }

    public void updateMenu(Menu menu) {
        MenuDataBase.executor.execute(() -> {dao.update(menu);});
    }

    public LiveData<List<Menu>> getAllMenus() {
        return menus;
    }

    public void deleteAllMenus() {
        MenuDataBase.executor.execute(dao::deleteAll);
    }
}
