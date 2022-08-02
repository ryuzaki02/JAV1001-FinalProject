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
    private ContactModel contactModel;

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

        setupView();
    }

    private void setupView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            contactModel = (ContactModel) getIntent().getSerializableExtra("ContactModel");
            firstNameEditText.setText(contactModel.getFirstName());
            lastNameEditText.setText(contactModel.getLastName());
            phoneEditText.setText(contactModel.getNumber());
            emailEditText.setText(contactModel.getEmail());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.idSaveContact) {
            final ContactModel newContact = getValidContact();
            if (newContact != null) {
                Intent dataPassIntent = new Intent();
                dataPassIntent.putExtra("NewContactModel", newContact);
                setResult(contactModel == null ? R.string.result_ok_new : R.string.result_ok_update, dataPassIntent);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tick_menu, menu);
        return true;
    }

    private ContactModel getValidContact() {
        final EditText firstNameEditText = (EditText) findViewById(R.id.idEnterFirstNameEditText);
        final EditText lastNameEditText = findViewById(R.id.idEnterLastNameEditText);
        final EditText phoneEditText = findViewById(R.id.idEnterPhoneEditText);
        final EditText emailEditText = findViewById(R.id.idEnterEmailEditText);

        EditText []editTexts = new EditText[4];
        editTexts[0] = firstNameEditText;
        editTexts[1] = lastNameEditText;
        editTexts[2] = phoneEditText;
        editTexts[3] = emailEditText;

        for (EditText et:
             editTexts) {
            if (et.getText().toString().isEmpty()) {
                final String text = et == firstNameEditText ? "First name" : (et == lastNameEditText ? "Last name" : (et == phoneEditText ? "Phone number" : "Email"));
                final String errorString = (text + " should not be empty");
                Dialog.instance.showDialog(this, errorString,false, "", null);
                return null;
            }
        }
        if (contactModel == null) {
            return new ContactModel(firstNameEditText.getText().toString(), lastNameEditText.getText().toString(), phoneEditText.getText().toString(), emailEditText.getText().toString());
        } else {
            contactModel.setFirstName(firstNameEditText.getText().toString());
            contactModel.setLastName(lastNameEditText.getText().toString());
            contactModel.setEmail(emailEditText.getText().toString());
            contactModel.setNumber(phoneEditText.getText().toString());
            return contactModel;
        }

    }
}