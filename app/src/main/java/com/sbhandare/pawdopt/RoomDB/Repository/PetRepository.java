package com.sbhandare.pawdopt.RoomDB.Repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

import com.sbhandare.pawdopt.Model.Pet;
import com.sbhandare.pawdopt.RoomDB.AppDatabase;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PetRepository {
    private static final String DB_NAME = "db_pawdopt";
    private static AppDatabase appDatabase;

    public PetRepository(Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }

    public void insertPet(Pet pet) {
        insertPetTask(pet);
    }

    private static void insertPetTask(final Pet pet) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.getPetDAO().insertPet(pet);
                return null;
            }
        }.execute();
    }

    public void updatePet(Pet pet){
        updatePetTask(pet);
    }

    private static void updatePetTask(final Pet pet) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.getPetDAO().updatePet(pet);
                return null;
            }
        }.execute();
    }

    public void deletePet(long id){
        deletePetTask(id);
    }

    public static void deletePetTask(final long id) {
        final Pet pet = appDatabase.getPetDAO().getPetWithId(id);
        if(pet != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    appDatabase.getPetDAO().deletePet(pet);
                    return null;
                }
            }.execute();
        }
    }

    public void deleteAllPets(){
        delAllPetsTask();
    }

    private static void delAllPetsTask(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.getPetDAO().deleteAllPets();
                return null;
            }
        }.execute();
    }

    public Pet getPetWithId(long id){
        return appDatabase.getPetDAO().getPetWithId(id);
    }

    public Pet getPetWithPetfinderId(long pfId){
        return appDatabase.getPetDAO().getPetWithPetfinderId(pfId);
    }

    public List<Pet> getAllPets(){
        return appDatabase.getPetDAO().getAllPets();
    }

    public Set<Long> getAllPetIds() {
        return new HashSet<>(appDatabase.getPetDAO().getAllPetIds());
    }
}
