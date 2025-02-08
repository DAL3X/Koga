package de.dal3x.koga.data.room.example;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    Single<List<User>> getAll();

    @Insert
    Completable insert(User users);

    @Delete
    Completable delete(User user);
}
