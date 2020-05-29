package com.sbhandare.pawdopt.RoomDB.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.sbhandare.pawdopt.Model.Pet;

import java.util.List;
import java.util.Set;

@Dao
public interface PetDAO {
    @Insert
    void insertPet(Pet... pets);

    @Update
    void updatePet(Pet... pets);

    @Delete
    void deletePet(Pet pet);

    @Query("DELETE FROM pet")
    void deleteAllPets();

    @Query("SELECT * FROM pet")
    List<Pet> getAllPets();

    @Query("SELECT * FROM pet WHERE petid = :id")
    Pet getPetWithId(long id);

    @Query("SELECT * FROM pet WHERE petfinderid = :pfId")
    Pet getPetWithPetfinderId(long pfId);

    @Query("SELECT petid FROM pet")
    List<Long> getAllPetIds();
}