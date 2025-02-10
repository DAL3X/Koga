package de.dal3x.koga.menu;


import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;


public class MenuRepository {

    private MenuDao menuDao;
    private LiveData<List<Menu>> menus;


    public MenuRepository(Application application) {
        MenuDatabase db = MenuDatabase.getDatabase(application);
        menuDao = db.menuDao();
        menus = menuDao.getAlphabetizedWords();
    }

    public LiveData<List<Menu>> getAllMenus() {
        return menus;
    }

    public void insert(Menu word) {
        MenuDatabase.databaseWriteExecutor.execute(() -> {
            menuDao.insert(word);
        });
    }

    public void deleteAll() {
        MenuDatabase.databaseWriteExecutor.execute(() -> {
            menuDao.deleteAll();
        });
    }
}
