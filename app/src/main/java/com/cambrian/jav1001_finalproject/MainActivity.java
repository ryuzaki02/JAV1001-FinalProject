package com.cambrian.jav1001_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ContactModel> contactModels;
    private RecyclerView contactsRecyclerView;
    private ContactsRecyclerViewAdapter contactsRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getSupportActionBar().setTitle("Contacts");

        contactModels = new ArrayList<ContactModel>();
        contactModels.add(new ContactModel("Aman", "1234555", "a@a.com"));
        contactModels.add(new ContactModel("Roll", "9998", "b@b.com"));

        contactsRecyclerView = findViewById(R.id.idContactsRecyclerView);

        setupContactsRecyclerView();

        FloatingActionButton floatingActionButton = findViewById(R.id.idAddContactButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createNewContactIntent = new Intent(MainActivity.this, CreateNewContactActivity.class);
                startActivity(createNewContactIntent);
            }
        });
    }

    private void setupContactsRecyclerView() {
        contactsRecyclerViewAdapter = new ContactsRecyclerViewAdapter(this, contactModels);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactsRecyclerView.setAdapter(contactsRecyclerViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);

        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterContactList(s.toLowerCase());
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void filterContactList(String text) {
        ArrayList<ContactModel> filteredContactList = new ArrayList<ContactModel>();
        for (ContactModel model : contactModels) {
            if (model.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredContactList.add(model);
            }
        }
        // on below line we are checking if the filtered list is empty or not.
        if (filteredContactList.isEmpty()) {
            Toast.makeText(this, "No Contact Found", Toast.LENGTH_SHORT).show();
        } else {
            // passing this filtered list to our adapter with filter list method.
            contactsRecyclerViewAdapter.filterList(filteredContactList);
        }
    }
}