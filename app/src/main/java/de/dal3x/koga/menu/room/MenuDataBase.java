package de.dal3x.koga.menu.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.dal3x.koga.menu.Menu;

@Database(entities = {Menu.class}, version = 1, exportSchema = false)
@TypeConverters({RecipeConverter.class})
public abstract class MenuDataBase extends RoomDatabase {
    public static final ExecutorService executor = Executors.newFixedThreadPool(4);
    public abstract MenuDAO menuDAO();
}
