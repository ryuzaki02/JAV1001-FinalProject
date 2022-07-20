package com.cambrian.jav1001_finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //private MutableLiveData<ArrayList<ContactModel>> contactModels = new MutableLiveData<ArrayList<ContactModel>>();
    private ArrayList<ContactModel> contactModels = new ArrayList<ContactModel>();
    private RecyclerView contactsRecyclerView;
    private ContactsRecyclerViewAdapter contactsRecyclerViewAdapter;
    private ContactsViewModel viewModel = new ContactsViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();

                        }
                    }
                });

        this.getSupportActionBar().setTitle("Contacts");

        contactsRecyclerView = findViewById(R.id.idContactsRecyclerView);
        setupContactsRecyclerView();

        contactModels.add(new ContactModel("Aman", "123123", "a@a.com"));
        viewModel.contactModels.setValue(contactModels);

        viewModel.contactModels.observe(this, new Observer<ArrayList<ContactModel>>() {
            @Override
            public void onChanged(ArrayList<ContactModel> contactModels) {
                contactsRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.idAddContactButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createNewContactIntent = new Intent(MainActivity.this, CreateNewContactActivity.class);
                someActivityResultLauncher.launch(createNewContactIntent);
            }
        });
    }

    private void setupContactsRecyclerView() {
        contactsRecyclerViewAdapter = new ContactsRecyclerViewAdapter(this, viewModel);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Aman", String.valueOf(resultCode));
    }

    private void filterContactList(String text) {
        ArrayList<ContactModel> filteredContactList = new ArrayList<ContactModel>();
        for (ContactModel model : viewModel.getContacts()) {
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