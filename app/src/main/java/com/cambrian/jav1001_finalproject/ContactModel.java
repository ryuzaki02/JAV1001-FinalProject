package com.cambrian.jav1001_finalproject;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "contact_table")
public class ContactModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;
    @NonNull
    @ColumnInfo(name = "first_name")
    private String firstName;
    @NonNull
    @ColumnInfo(name = "last_name")
    private String lastName;
    @ColumnInfo(name = "number")
    private String number;
    @ColumnInfo(name = "email")
    private String email;

    public ContactModel(String firstName, String lastName, String number, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.email = email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String name) {
        this.lastName = name;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
