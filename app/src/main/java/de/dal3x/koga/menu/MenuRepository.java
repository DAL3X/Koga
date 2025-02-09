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
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

/** Uses the room library.
 * Menu repository implements as a singleton to ensure only one database dao for menus exists.
 * It holds a menu map in RAM but keeps it consistent with the background database storage.
 * Before MenuRepository can be used, it has to be initialized with an app context to allow database access.
 * This is done to enable pre-loading of needed resources.
*/
public class MenuRepository {

    private static MenuRepository instance;
    public static void initializeInstance(Context appContext) {
        instance = new MenuRepository(appContext);
    }
    public static MenuRepository getInstance() {
        return instance;
    }

    private final Map<String, Menu> menus = new HashMap<>();
    private final MenuDAO dao;

    public void addMenu(Menu menu) {
        menus.put(menu.getName(), menu);
        addDataBaseMenu(menu);
    }

    public void deleteMenu(String name) {
        menus.remove(name);
        Menu shadowMenu = new Menu(name);
        deleteDataBaseMenu(shadowMenu);
    }

    public List<Menu> getAllMenus() {
        return new LinkedList<>(menus.values());
    }

    private MenuRepository(Context appContext) {
        MenuDataBase database = Room.databaseBuilder(appContext, MenuDataBase.class, Names.MENUSTORE.string).build();
        dao = database.menuDAO();
        getDataBaseMenus();
    }

    private void addDataBaseMenu(Menu menu) {
        CompletableObserver insertObserver = new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}
            @Override
            public void onComplete() {}
            @Override
            public void onError(@NonNull Throwable e) {}
        };
        dao.insert(menu).subscribe(insertObserver);
    }

    private void deleteDataBaseMenu(Menu menu) {
        CompletableObserver deleteObserver = new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}
            @Override
            public void onComplete() {}
            @Override
            public void onError(@NonNull Throwable e) {}
        };
        dao.delete(menu).subscribe(deleteObserver);
    }

    private void getDataBaseMenus() {
        SingleObserver<List<Menu>> queryObserver = new SingleObserver<>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}
            @Override
            public void onSuccess(@NonNull List<Menu> menuList) {
                for (Menu menu : menuList) {
                    menus.put(menu.getName(), menu);
                }
            }
            @Override
            public void onError(@NonNull Throwable e) {}
        };
        dao.getAll().subscribe(queryObserver);
    }
}
