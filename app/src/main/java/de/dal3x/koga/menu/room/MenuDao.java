package de.dal3x.koga.menu.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import de.dal3x.koga.menu.Menu;

@Dao
interface MenuDao {

    @Query("SELECT * FROM Menu")
    LiveData<List<Menu>> getAll();

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insert(Menu menu);

    @Delete
    void delete(Menu menu);

    @Update
    void update(Menu menu);

    @Query("DELETE FROM Menu")
    void deleteAll();
}
