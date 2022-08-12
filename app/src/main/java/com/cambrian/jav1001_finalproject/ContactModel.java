package com.cambrian.jav1001_finalproject;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * This is Model class for Contact that implements serializable
 */
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
    @ColumnInfo(name = "contact_image", typeAffinity = ColumnInfo.BLOB)
    private byte[] contactImage;

    /**
     * Constructor to initialise class
     * @param firstName: String
     * @param lastName: String
     * @param number: String
     * @param email: String
     * @return ContactModel Object
     */
    public ContactModel(String firstName, String lastName, String number, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.email = email;
    }

    /**
     * This method returns first name of contact
     * @param: nothing
     * @return String
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * This method sets first name of contact
     * @param name: String
     * @return nothing
     */
    public void setFirstName(String name) {
        this.firstName = name;
    }

    /**
     * This method returns last name of contact
     * @param: nothing
     * @return String
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * This method sets last name of contact
     * @param name: String
     * @return nothing
     */
    public void setLastName(String name) {
        this.lastName = name;
    }

    /**
     * This method returns number of contact
     * @param: nothing
     * @return String
     */
    public String getNumber() {
        return this.number;
    }

    /**
     * This method sets number of contact
     * @param number: String
     * @return nothing
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * This method returns email of contact
     * @param: nothing
     * @return String
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * This method sets email of contact
     * @param email: String
     * @return nothing
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method sets image of contact
     * @param contactImage: Bytes array
     * @return nothing
     */
    public void setContactImage(byte[] contactImage) { this.contactImage = contactImage; };

    /**
     * This method returns image of contact
     * @param: nothing
     * @return bytes array
     */
    public byte[] getContactImage() { return this.contactImage; };
}
