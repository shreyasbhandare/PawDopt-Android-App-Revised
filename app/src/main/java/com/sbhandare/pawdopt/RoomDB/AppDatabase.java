package com.sbhandare.pawdopt.RoomDB;

import com.sbhandare.pawdopt.Model.Pet;
import com.sbhandare.pawdopt.RoomDB.DAO.PetDAO;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Pet.class}, version = 5, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
        public abstract PetDAO getPetDAO();
}