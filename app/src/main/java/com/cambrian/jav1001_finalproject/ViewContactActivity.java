package com.cambrian.jav1001_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class ViewContactActivity extends AppCompatActivity {

    private TextView nameTextView, phoneTextView, emailTextView;
    private ContactModel contactModel;
    private ActivityResultLauncher<Intent> editContactActivityLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        this.getSupportActionBar().setTitle("View Contact");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setupView();
        setupActivityLauncher();
    }

    private void setupView() {
        nameTextView = findViewById(R.id.idNameTextView);
        phoneTextView = findViewById(R.id.idPhoneTextView);
        emailTextView = findViewById(R.id.idMailTextView);

        Bundle bundle = getIntent().getExtras();
        contactModel = (ContactModel) getIntent().getSerializableExtra("ContactModel");
        nameTextView.setText(contactModel.getFirstName() + ' ' + contactModel.getLastName());
        phoneTextView.setText(contactModel.getNumber());
        emailTextView.setText(contactModel.getEmail());
    }

    private void setupActivityLauncher() {
        editContactActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == R.string.result_ok_update) {
                            Intent data = result.getData();
                            final ContactModel model = (ContactModel) data.getSerializableExtra("NewContactModel");
                            Intent dataPassIntent = new Intent();
                            dataPassIntent.putExtra("NewContactModel", model);
                            setResult(R.string.result_ok_update, dataPassIntent);
                            finish();
                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.idEditContactMenuItem) {
            Intent createNewContactIntent = new Intent(ViewContactActivity.this, CreateNewContactActivity.class);
            createNewContactIntent.putExtra("ContactModel", contactModel);
            editContactActivityLauncher.launch(createNewContactIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_contact_menu, menu);
        return true;
    }
}