package com.cambrian.jav1001_finalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CreateNewContactActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, phoneEditText, emailEditText;
    private ImageButton imageButton, deleteImageButton;
    private ImageView contactImageView;
    private Button addContactButton;
    private ContactModel contactModel;
    private ActivityResultLauncher<Intent> photoPickerActivityLauncher;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_contact);

        // Customize Action bar
        this.getSupportActionBar().setTitle("Add Contact");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Initialise views from their ids
        firstNameEditText = findViewById(R.id.idEnterFirstNameEditText);
        lastNameEditText = findViewById(R.id.idEnterLastNameEditText);
        phoneEditText = findViewById(R.id.idEnterPhoneEditText);
        emailEditText = findViewById(R.id.idEnterEmailEditText);
        imageButton = findViewById(R.id.idEditImageButton);
        contactImageView = findViewById(R.id.idContactImageView);
        deleteImageButton = findViewById(R.id.idDeleteImageButton);

        // Add listeners to views
        addImageButtonListener();
        deleteImageButtonListener();
        setupActivityLauncher();

        setupView();
    }

    /**
     * Setup on click listener to image button
     * @param: nothing
     * @return: nothing
     */
    private void addImageButtonListener() {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Opens gallery picker for profile picture
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                photoPickerActivityLauncher.launch(intent);
            }
        });
    }

    /**
     * Setup on click listener to delete image
     * @param: nothing
     * @return: nothing
     */
    private void deleteImageButtonListener() {
        deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (contactModel != null) {
                    contactModel.setContactImage(null);
                }
                contactImageView.setImageResource(R.drawable.ic_account);
            }
        });
    }

    /**
     * Setup views for the activity and set data
     * @param: nothing
     * @return: nothing
     */
    private void setupView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            contactModel = (ContactModel) getIntent().getSerializableExtra("ContactModel");
            firstNameEditText.setText(contactModel.getFirstName());
            lastNameEditText.setText(contactModel.getLastName());
            phoneEditText.setText(contactModel.getNumber());
            emailEditText.setText(contactModel.getEmail());
            if (contactModel.getContactImage() != null) {
                Bitmap bmp = BitmapFactory.decodeByteArray(contactModel.getContactImage(), 0, contactModel.getContactImage().length);
                contactImageView.setImageBitmap(Bitmap.createScaledBitmap(bmp, 1000, 1000, false));
            }
        }
    }

    /**
     * Setup activity launcher for photo picker
     * @param: nothing
     * @return: nothing
     */
    private void setupActivityLauncher() {
        photoPickerActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent intent = result.getData();
                        imageUri = intent.getData();
                        contactImageView.setImageURI(imageUri);
                    }
                });
    }

    /**
     * On options item selected listener for menus
     * @param item: MenuItem
     * @return: boolean
     */
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

    /**
     * Setup menu for action bar
     * @param menu: Menu
     * @return: boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tick_menu, menu);
        return true;
    }

    /**
     * Validates whether contact information is valid or not
     * @param: nothing
     * @return: ContactModel
     */
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
            final ContactModel model = new ContactModel(firstNameEditText.getText().toString(), lastNameEditText.getText().toString(), phoneEditText.getText().toString(), emailEditText.getText().toString());
            if (imageUri != null) {
                model.setContactImage(saveImageToContactModel());
            }
            return model;
        } else {
            contactModel.setFirstName(firstNameEditText.getText().toString());
            contactModel.setLastName(lastNameEditText.getText().toString());
            contactModel.setEmail(emailEditText.getText().toString());
            contactModel.setNumber(phoneEditText.getText().toString());
            if (imageUri != null) {
                contactModel.setContactImage(saveImageToContactModel());
            }
            return contactModel;
        }
    }

    /**
     * Save image to current contact model
     * @param: nothing
     * @return: Bytes array
     */
    public byte[] saveImageToContactModel() {
        try {
            InputStream iStream = getContentResolver().openInputStream(imageUri);
            byte[] inputData = getBytes(iStream);
            return inputData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Provides byte array of input stream for image
     * @param inputStream: InputStream
     * @return: Byte arrays
     */
    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}