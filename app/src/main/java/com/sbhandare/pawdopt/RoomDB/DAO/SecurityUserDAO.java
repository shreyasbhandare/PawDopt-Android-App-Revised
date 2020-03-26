package com.sbhandare.pawdopt.RoomDB.DAO;

import com.sbhandare.pawdopt.Model.SecurityUser;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SecurityUserDAO {
    @Insert
    public void insert(SecurityUser... securityUsers);

    @Update
    public void update(SecurityUser... securityUsers);

    @Delete
    public void delete(SecurityUser securityUser);

    @Query("DELETE FROM security_user")
    public void deleteAll();

    @Query("SELECT * FROM security_user")
    public List<SecurityUser> getSecurityUsers();

    @Query("SELECT * FROM security_user WHERE username = :username")
    public SecurityUser getSecurityUserWithUsername(String username);
}
