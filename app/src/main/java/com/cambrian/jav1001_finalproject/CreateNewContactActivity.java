package com.cambrian.jav1001_finalproject;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CreateNewContactActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, phoneEditText, emailEditText;
    private Button addContactButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_contact);

        this.getSupportActionBar().setTitle("Add Contact");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        firstNameEditText = findViewById(R.id.idEnterFirstNameEditText);
        lastNameEditText = findViewById(R.id.idEnterLastNameEditText);
        phoneEditText = findViewById(R.id.idEnterPhoneEditText);
        emailEditText = findViewById(R.id.idEnterEmailEditText);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.tick_menu, menu);

//        MenuItem saveItem = menu.findItem(R.id.idSaveContact);
//        final ImageButton saveButton = (ImageButton) MenuItemCompat.getActionView(saveItem);
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
        return true;
    }
}