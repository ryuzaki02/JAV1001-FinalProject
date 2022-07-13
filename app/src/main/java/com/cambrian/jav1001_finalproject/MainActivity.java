package com.cambrian.jav1001_finalproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ContactModel> contactModels;
    private RecyclerView contactsRecyclerView;
    private ContactsRecyclerViewAdapter contactsRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactModels = new ArrayList<ContactModel>();
        contactModels.add(new ContactModel("Aman", "1234555", "a@a.com"));
        contactModels.add(new ContactModel("Aman", "1234555", "a@a.com"));

        contactsRecyclerView = findViewById(R.id.contactsRecyclerView);

        setupContactsRecyclerView();
    }

    private void setupContactsRecyclerView() {
        contactsRecyclerViewAdapter = new ContactsRecyclerViewAdapter(this, contactModels);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactsRecyclerView.setAdapter(contactsRecyclerViewAdapter);
    }
}