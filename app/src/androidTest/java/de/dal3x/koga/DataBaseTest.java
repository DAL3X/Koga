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

        Runnable runnable1 = () -> {
            User u = new User(1, "test1", "test2");
            userDao.insert(u);
            assertNotNull(u);
        };
        Thread thread1 = new Thread(runnable1);
        thread1.start();
        thread1.join();

        SingleObserver<List<User>> observer = new SingleObserver<>() {
            public List<User> users;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onSuccess(@NonNull List<User> users) {
                assertFalse(users.isEmpty());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                fail();
            }
        };
        Runnable runnable2 = () -> {
            @NonNull SingleObserver<List<User>> x = userDao.getAll().subscribeWith(observer);
        };
        Thread thread2 = new Thread(runnable2);
        thread2.start();
        thread2.join();
    }
}