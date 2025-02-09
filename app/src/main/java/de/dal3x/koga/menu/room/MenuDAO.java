package de.dal3x.koga.menu.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import de.dal3x.koga.menu.Menu;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MenuDAO {

    @Query("SELECT * FROM Menu")
    Single<List<Menu>> getAll();

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insert(Menu menu);

    @Delete
    void delete(Menu menu);
}
