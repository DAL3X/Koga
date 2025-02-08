package de.dal3x.koga.data.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import de.dal3x.koga.menu.Menu;

@Database(entities = {Menu.class}, version = 1, exportSchema = false)
public abstract class MenuDataBase extends RoomDatabase {
    public abstract MenuDAO menuDAO();
}
