package de.dal3x.koga;

import android.content.Context;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.List;

import de.dal3x.koga.data.room.example.AppDatabase;
import de.dal3x.koga.data.room.example.User;
import de.dal3x.koga.data.room.example.UserDao;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DataBaseTest {

    @Test
    public void testDB() throws InterruptedException {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AppDatabase db = Room.databaseBuilder(appContext, AppDatabase.class, "database-name").build();
        UserDao userDao = db.userDao();
        CompletableObserver insertObserver = new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}
            @Override
            public void onComplete() {}
            @Override
            public void onError(@NonNull Throwable e) {}
        };
        SingleObserver<List<User>> queryObserver = new SingleObserver<>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onSuccess(@NonNull List<User> users) {
                for (int i = 0; i < users.size(); i++) {
                    assertEquals(i+1, users.get(i).uid);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                fail();
            }
        };

        // Add User 1
        User u = new User(1, "test1", "test2");
        Runnable runnable = () -> {
            assertNotNull(u);
            userDao.insert(u).subscribe(insertObserver);
        };
        Thread thread = new Thread(runnable);
        thread.start();
        thread.join();


        // Check if one user is present
        Runnable runnable2 = () -> {
            @NonNull SingleObserver<List<User>> x = userDao.getAll().subscribeWith(queryObserver);
        };
        thread = new Thread(runnable2);
        thread.start();
        thread.join();

        // Add User 2
        User u2 = new User(2, "test1", "test2");
        Runnable runnable3 = () -> {
            assertNotNull(u2);
            userDao.insert(u2).subscribe(insertObserver);
        };
        thread = new Thread(runnable3);
        thread.start();
        thread.join();

        // Check if both users are present
        Runnable runnable4 = () -> {
            @NonNull SingleObserver<List<User>> x = userDao.getAll().subscribeWith(queryObserver);
        };
        thread = new Thread(runnable4);
        thread.start();
        thread.join();


    }
}