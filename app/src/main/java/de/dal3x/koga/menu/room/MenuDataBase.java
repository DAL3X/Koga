package de.dal3x.koga.menu.room;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.dal3x.koga.menu.Menu;
import de.dal3x.koga.util.Names;

@Database(entities = {Menu.class}, version = 1, exportSchema = false)
@TypeConverters({RecipeConverter.class})
abstract class MenuDataBase extends RoomDatabase {

    private static volatile MenuDataBase instance;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    abstract MenuDao menuDao();

    public static MenuDataBase getDatabase(final Application application) {
        if (instance == null) {
            synchronized (MenuDataBase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(application.getApplicationContext(), MenuDataBase.class, Names.MENUSTORE.string).build();
                }
            }
        }
        return instance;
    }


}
