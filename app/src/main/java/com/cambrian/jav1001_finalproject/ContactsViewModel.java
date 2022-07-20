package com.cambrian.jav1001_finalproject;

import androidx.lifecycle.MutableLiveData;

import java.io.Serializable;
import java.util.ArrayList;

public class ContactsViewModel implements Serializable {
    public MutableLiveData<ArrayList<ContactModel>> contactModels = new MutableLiveData<ArrayList<ContactModel>>();

    public ArrayList<ContactModel> getContacts() {
        return contactModels.getValue();
    }
}
