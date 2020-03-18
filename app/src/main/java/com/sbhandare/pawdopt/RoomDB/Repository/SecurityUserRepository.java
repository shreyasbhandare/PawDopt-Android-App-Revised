package com.sbhandare.pawdopt.RoomDB.Repository;

import android.content.Context;
import android.os.AsyncTask;

import com.sbhandare.pawdopt.Model.SecurityUser;
import com.sbhandare.pawdopt.RoomDB.AppDatabase;

import java.util.List;

import androidx.room.Room;

public class SecurityUserRepository {
    private static final String DB_NAME = "db_pawdopt";
    private static AppDatabase appDatabase;

    public SecurityUserRepository(Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).allowMainThreadQueries().build();
    }

    public void insertSecurityUser(String username,
                           String token,
                           String refreshToken,
                           String password) {
        SecurityUser securityUser = new SecurityUser();
        securityUser.setUsername(username);
        securityUser.setToken(token);
        securityUser.setRefreshToken(refreshToken);
        securityUser.setPassword(password);

        insertSecurityUser(securityUser);
    }

    private static void insertSecurityUser(final SecurityUser securityUser) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.getSecurityUserDAO().insert(securityUser);
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

    public static void updateSecurityUser(final SecurityUser securityUser) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.getSecurityUserDAO().update(securityUser);
                return null;
            }
        }.execute();
    }

    public static void deleteSecurityUser(final String username) {
        final SecurityUser securityUser = appDatabase.getSecurityUserDAO().getSecurityUserWithUsername(username);
        if(securityUser != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    appDatabase.getSecurityUserDAO().delete(securityUser);
                    return null;
                }
            }.execute();
        }
    }

    public SecurityUser getSecurityUserByUsername(String username){
        return appDatabase.getSecurityUserDAO().getSecurityUserWithUsername(username);
    }

    public List<SecurityUser> getAllSecurityUsers(){
        return appDatabase.getSecurityUserDAO().getSecurityUsers();
    }
}
