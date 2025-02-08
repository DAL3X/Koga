package de.dal3x.koga.data.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import de.dal3x.koga.menu.Menu;

@Dao
public interface MenuDAO {

    @Query("SELECT * FROM Menu")
    List<Menu> getAll();

    @Insert
    void insert(Menu menu);

    @Delete
    void delete(Menu menu);
}
