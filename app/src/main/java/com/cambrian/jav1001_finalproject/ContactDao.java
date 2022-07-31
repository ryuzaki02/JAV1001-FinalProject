package com.cambrian.jav1001_finalproject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {
    @Insert
    void insert(ContactModel model);

    @Delete
    void delete(ContactModel model);

    @Update
    void update(ContactModel model);

    @Query("DELETE FROM contact_table")
    void deleteAll();

    @Query("SELECT * from contact_table ORDER BY first_name, last_name ASC")
    LiveData<List<ContactModel>> getAllContacts();
}
