package com.sbhandare.pawdopt.RoomDB;

import com.sbhandare.pawdopt.Model.SecurityUser;
import com.sbhandare.pawdopt.Model.User;
import com.sbhandare.pawdopt.RoomDB.DAO.SecurityUserDAO;
import com.sbhandare.pawdopt.RoomDB.DAO.UserDAO;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {SecurityUser.class, User.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
        public abstract SecurityUserDAO getSecurityUserDAO();
        public abstract UserDAO getUserDAO();
}
