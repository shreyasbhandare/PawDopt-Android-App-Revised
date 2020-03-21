package com.sbhandare.pawdopt.RoomDB;

import com.sbhandare.pawdopt.Model.Oauth2Token;
import com.sbhandare.pawdopt.Model.SecurityUser;
import com.sbhandare.pawdopt.RoomDB.DAO.SecurityUserDAO;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {SecurityUser.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
        public abstract SecurityUserDAO getSecurityUserDAO();
}
