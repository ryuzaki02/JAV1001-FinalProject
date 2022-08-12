package com.cambrian.jav1001_finalproject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Class to define methods for interaction with database
 */
@Dao
public interface ContactDao {
    /**
     * This method inserts a model to database
     * @param model: Contact Model
     */
    @Insert
    void insert(ContactModel model);

    /**
     * This method deletes a model from database
     * @param model: Contact Model
     */
    @Delete
    void delete(ContactModel model);

    /**
     * This method updates already present model from database
     * @param model: Contact Model
     */
    @Update
    void update(ContactModel model);

    /**
     * This method deletes all the contacts from database
     * @param: no param
     */
    @Query("DELETE FROM contact_table")
    void deleteAll();

    /**
     * This method fetches all the contacts from database
     * @param: no param
     */
    @Query("SELECT * from contact_table ORDER BY first_name, last_name ASC")
    LiveData<List<ContactModel>> getAllContacts();

    /**
     * This method fetches all the contacts sorted by first and last name ASC from database
     * @param searchText: String
     */
    @Query("SELECT * from contact_table WHERE first_name LIKE :searchText ORDER BY first_name, last_name ASC")
    LiveData<List<ContactModel>> getContacts(String searchText);
}
