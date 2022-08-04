package com.cambrian.jav1001_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ContactsRecyclerViewAdapterInterface {

    private RecyclerView contactsRecyclerView;
    private ContactsRecyclerViewAdapter contactsRecyclerViewAdapter;
    private ContactsViewModel viewModel;
    private ActivityResultLauncher<Intent> createNewContactActivityLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().setTitle("Contacts");

        viewModel = ViewModelProviders.of(this).get(ContactsViewModel.class);
        viewModel.getAllContacts().observe(this, new Observer<List<ContactModel>>() {
            @Override
            public void onChanged(@Nullable List<ContactModel> contactModels) {
                contactsRecyclerViewAdapter.setContactModels(viewModel.getAllContacts().getValue());
            }
        });

        contactsRecyclerView = findViewById(R.id.idContactsRecyclerView);
        setupContactsRecyclerView();

        setupActivityLauncher();

        FloatingActionButton floatingActionButton = findViewById(R.id.idAddContactButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createNewContactIntent = new Intent(MainActivity.this, CreateNewContactActivity.class);
                createNewContactActivityLauncher.launch(createNewContactIntent);
            }
        });
    }

    private void setupContactsRecyclerView() {
        contactsRecyclerViewAdapter = new ContactsRecyclerViewAdapter(this);
        contactsRecyclerViewAdapter.setContactModels(viewModel.getAllContacts().getValue());
        contactsRecyclerView.setAdapter(contactsRecyclerViewAdapter);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(contactsRecyclerViewAdapter));
        itemTouchHelper.attachToRecyclerView(contactsRecyclerView);
    }

    private void setupActivityLauncher() {
        createNewContactActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == R.string.result_ok_new) {
                            // There are no request codes
                            Intent data = result.getData();
                            final ContactModel model = (ContactModel) data.getSerializableExtra("NewContactModel");
                            viewModel.insert(model);
                        } else if (result.getResultCode() == R.string.result_ok_update) {
                            Intent data = result.getData();
                            final ContactModel model = (ContactModel) data.getSerializableExtra("NewContactModel");
                            viewModel.delete(model);
                            viewModel.insert(model);
                        } else if (result.getResultCode() == R.string.result_ok_delete) {
                            Intent data = result.getData();
                            final ContactModel model = (ContactModel) data.getSerializableExtra("DeleteContactModel");
                            viewModel.delete(model);
                        }
                    }
                });
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
    }

    @Override
    public void deleteContact(ContactModel contactModel) {
        viewModel.delete(contactModel);
    }

    @Override
    public void contactDidTap(ContactModel contactModel) {
        Intent viewContactIntent = new Intent(MainActivity.this, ViewContactActivity.class);
        viewContactIntent.putExtra("ContactModel", contactModel);
        createNewContactActivityLauncher.launch(viewContactIntent);
    }

    private void filterContactList(String text) {
        List<ContactModel> filteredModels = new ArrayList<ContactModel>();
        for (ContactModel model:
             viewModel.getAllContacts().getValue()) {
            final String fullName = model.getFirstName() + " " + model.getLastName();
            if (fullName.toLowerCase().contains(text.toLowerCase())) {
                filteredModels.add(model);
            }
        }
        contactsRecyclerViewAdapter.setContactModels(text.isEmpty() ? viewModel.getAllContacts().getValue() : filteredModels);
    }
}