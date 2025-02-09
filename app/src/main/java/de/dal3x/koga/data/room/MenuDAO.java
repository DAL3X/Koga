package de.dal3x.koga.data.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import de.dal3x.koga.menu.Menu;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MenuDAO {

    @Query("SELECT * FROM Menu")
    Single<List<Menu>> getAll();

    @Insert
    Completable insert(Menu menu);

    @Delete
    Completable delete(Menu menu);
}
