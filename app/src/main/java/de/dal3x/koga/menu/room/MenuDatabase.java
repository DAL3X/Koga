package de.dal3x.koga.menu.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.dal3x.koga.menu.Menu;
import de.dal3x.koga.util.Names;


@Database(entities = {Menu.class}, version = 1, exportSchema = false)
@TypeConverters({IngredientsConverter.class})
abstract class MenuDatabase extends RoomDatabase {

    private static volatile MenuDatabase instance;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    abstract MenuDao menuDao();

    static MenuDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (MenuDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), MenuDatabase.class, Names.MENUSTORE.name()).build();
                }
            }
        }
        return instance;
    }
}
