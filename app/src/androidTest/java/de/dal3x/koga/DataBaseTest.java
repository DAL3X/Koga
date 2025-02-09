package de.dal3x.koga;

import android.content.Context;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.LinkedList;
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

    private List<User> users = new LinkedList<>();

    private void addUser(UserDao dao, User u) throws InterruptedException {
        CompletableObserver insertObserver = new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}
            @Override
            public void onComplete() {}
            @Override
            public void onError(@NonNull Throwable e) {}
        };
        Runnable runnable = () -> {
            assertNotNull(u);
            dao.insert(u).subscribe(insertObserver);
        };
        Thread thread = new Thread(runnable);
        thread.start();
        thread.join();
        getUsers(dao);
    }

    private void getUsers(UserDao dao) throws InterruptedException {
        SingleObserver<List<User>> queryObserver = new SingleObserver<>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onSuccess(@NonNull List<User> usersList) {
                users = usersList;
            }

            @Override
            public void onError(@NonNull Throwable e) {
                fail();
            }
        };
        Runnable runnable = () -> {
            dao.getAll().subscribe(queryObserver);
        };
        Thread thread = new Thread(runnable);
        thread.start();
        thread.join();
    }

    private void deleteUser(UserDao dao, User u) throws InterruptedException {
        CompletableObserver deleteObserver = new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}
            @Override
            public void onComplete() {}
            @Override
            public void onError(@NonNull Throwable e) {}
        };
        Runnable runnable = () -> {
            assertNotNull(u);
            dao.delete(u).subscribe(deleteObserver);
        };
        Thread thread = new Thread(runnable);
        thread.start();
        thread.join();
        getUsers(dao);
    }

    @Test
    public void testDB() throws InterruptedException {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AppDatabase db = Room.databaseBuilder(appContext, AppDatabase.class, "database-name").build();
        UserDao userDao = db.userDao();

        // Add User 1
        User u = new User(1, "test1", "test2");
        addUser(userDao, u);
        // Check if one user is present
        getUsers(userDao);
        assertEquals(1, users.size());
        assertEquals("test1", users.get(0).firstName);
        // Add User 2
        User u2 = new User(2, "test3", "test4");
        addUser(userDao, u2);
        // Check if both users are present
        getUsers(userDao);
        assertEquals(2, users.size());
        assertEquals("test1", users.get(0).firstName);
        assertEquals(2, users.size());
        assertEquals("test3", users.get(1).firstName);
        // Remove both users
        deleteUser(userDao, u);
        deleteUser(userDao, u2);
        // Check if users list is empty
        assertEquals(0, users.size());
    }
}