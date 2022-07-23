package com.cambrian.jav1001_finalproject;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import androidx.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "contact_table")
public class ContactModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int contactId;
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "number")
    private String number;
    @ColumnInfo(name = "email")
    private String email;

    public ContactModel(String name, String number, String email) {
        this.name = name;
        this.number = number;
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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
