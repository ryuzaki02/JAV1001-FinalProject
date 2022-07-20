package com.cambrian.jav1001_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
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

//        Bundle bundle = getIntent().getExtras();
//        viewModel = (ContactsViewModel) getIntent().getSerializableExtra("ContactModels");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.idSaveContact) {
            ContactModel x = new ContactModel("Abc", "123123", "b@b.com");
            Intent dataPassIntent = new Intent();
            dataPassIntent.putExtra("ContactModel", x);
            setResult(200, dataPassIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tick_menu, menu);
        return true;
    }
}