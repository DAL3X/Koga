package de.dal3x.koga.menu.room;


import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import de.dal3x.koga.menu.Menu;


public class MenuRepository {

    private final MenuDao menuDao;
    private final LiveData<List<Menu>> menus;


    public MenuRepository(Context context) {
        MenuDatabase db = MenuDatabase.getDatabase(context);
        menuDao = db.menuDao();
        menus = menuDao.getAll();
    }

    public void addMenu(Menu word) {
        MenuDatabase.databaseWriteExecutor.execute(() -> {menuDao.insert(word);});
    }

    public void deleteMenu(Menu menu) {
        MenuDatabase.databaseWriteExecutor.execute(() -> {menuDao.delete(menu);});
    }

    public void updateMenu(Menu menu) {
        MenuDatabase.databaseWriteExecutor.execute(() -> {menuDao.update(menu);});
    }

    public LiveData<List<Menu>> getAllMenus() {
        return menus;
    }

    public void deleteAllMenus() {
        MenuDatabase.databaseWriteExecutor.execute(menuDao::deleteAll);
    }
}
