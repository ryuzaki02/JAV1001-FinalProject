package com.cambrian.jav1001_finalproject;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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

    @Query("SELECT * from contact_table ORDER BY name ASC")
    LiveData<List<ContactModel>> getAllContacts();
}
