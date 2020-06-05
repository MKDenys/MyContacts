package com.dk.mycontacts.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.dk.mycontacts.model.Contact;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM Contact ORDER BY firstName ASC")
    LiveData<List<Contact>> getAll();

    @Insert(onConflict = REPLACE)
    void insertOrUpdate(Contact... contacts);

    @Delete
    void delete(Contact... contacts);
}
