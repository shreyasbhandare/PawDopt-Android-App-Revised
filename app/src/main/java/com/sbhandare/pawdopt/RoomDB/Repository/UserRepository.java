package com.sbhandare.pawdopt.RoomDB.Repository;

import android.content.Context;
import android.os.AsyncTask;

import com.sbhandare.pawdopt.Model.User;
import com.sbhandare.pawdopt.RoomDB.AppDatabase;

import java.util.List;

import androidx.room.Room;

public class UserRepository {
    private static final String DB_NAME = "db_pawdopt";
    private static AppDatabase appDatabase;

    public UserRepository(Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }

    public void insertUser(long id,
                                   String email,
                                   String firstName,
                                   String lastName,
                                   String img) {
        User user = new User();

        user.setId(id);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setImage(img);

        insertUserTask(user);
    }

    private static void insertUserTask(final User user) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.getUserDAO().insert(user);
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute();
    }

    public void updateSecurityUser(User user){
        updateUserTask(user);
    }

    private static void updateUserTask(final User user) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.getUserDAO().update(user);
                return null;
            }
        }.execute();
    }

    public static void deleteUser(final String email) {
        final User user = appDatabase.getUserDAO().getUserByEmail(email);
        if(user != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    appDatabase.getUserDAO().delete(user);
                    return null;
                }
            }.execute();
        }
    }

    public void deleteAllUsers(){
        delAllUsersTask();
    }

    private static void delAllUsersTask(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.getSecurityUserDAO().deleteAll();
                appDatabase.getUserDAO().deleteAll();
                return null;
            }
        }.execute();
    }

    public User getUserByEmail(String email){
        return appDatabase.getUserDAO().getUserByEmail(email);
    }

    public List<User> getAllUsers(){
        return appDatabase.getUserDAO().getUsers();
    }
}
