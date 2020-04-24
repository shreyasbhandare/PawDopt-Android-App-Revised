package com.sbhandare.pawdopt.RoomDB.DAO;

import com.sbhandare.pawdopt.Model.User;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDAO {
    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User users);

    @Query("DELETE FROM user")
    void deleteAll();

    @Query("SELECT * FROM user")
    List<User> getUsers();

    @Query("SELECT * FROM user WHERE id = :email")
    User getUserByEmail(String email);
}
