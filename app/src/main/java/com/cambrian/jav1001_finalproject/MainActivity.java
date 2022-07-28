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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //private MutableLiveData<ArrayList<ContactModel>> contactModels = new MutableLiveData<ArrayList<ContactModel>>();
    private RecyclerView contactsRecyclerView;
    private ContactsRecyclerViewAdapter contactsRecyclerViewAdapter;
    private ContactsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().setTitle("Contacts");

        viewModel = ViewModelProviders.of(this).get(ContactsViewModel.class);
        viewModel.getAllContacts().observe(this, new Observer<List<ContactModel>>() {
            @Override
            public void onChanged(@Nullable List<ContactModel> contactModels) {
                Log.d("Aman", "----------blah blah");
                contactsRecyclerViewAdapter.setContactModels(viewModel.getAllContacts().getValue());
            }
        });

        contactsRecyclerView = findViewById(R.id.idContactsRecyclerView);
        setupContactsRecyclerView();


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

        FloatingActionButton floatingActionButton = findViewById(R.id.idAddContactButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent createNewContactIntent = new Intent(MainActivity.this, CreateNewContactActivity.class);
//                someActivityResultLauncher.launch(createNewContactIntent);

                viewModel.insert(new ContactModel("aaa", "12312321", "a@gmail.com"));
            }
        });
    }

    private void setupContactsRecyclerView() {
        contactsRecyclerViewAdapter = new ContactsRecyclerViewAdapter(this);
        contactsRecyclerViewAdapter.setContactModels(viewModel.getAllContacts().getValue());
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
                //filterContactList(s.toLowerCase());
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

//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//        super.onPointerCaptureChanged(hasCapture);
//    }

//    private void filterContactList(String text) {
//        ArrayList<ContactModel> filteredContactList = new ArrayList<ContactModel>();
//        for (ContactModel model : viewModel.getContacts()) {
//            if (model.getName().toLowerCase().contains(text.toLowerCase())) {
//                filteredContactList.add(model);
//            }
//        }
//        // on below line we are checking if the filtered list is empty or not.
//        if (filteredContactList.isEmpty()) {
//            Toast.makeText(this, "No Contact Found", Toast.LENGTH_SHORT).show();
//        } else {
//            // passing this filtered list to our adapter with filter list method.
//            contactsRecyclerViewAdapter.filterList(filteredContactList);
//        }
//    }
}