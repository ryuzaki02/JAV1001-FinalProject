package com.cambrian.jav1001_finalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private ImageView contactImageView;
    private ImageButton phoneButton, emailButton, messageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        // Customizes action bar
        this.getSupportActionBar().setTitle(R.string.view_contact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Setup view
        setupView();
        setupActivityLauncher();
    }

    /**
     * Method to setup view for activity
     * @param: nothing
     * @return: nothing
     */
    private void setupView() {
        nameTextView = findViewById(R.id.idNameTextView);
        phoneTextView = findViewById(R.id.idPhoneTextView);
        emailTextView = findViewById(R.id.idMailTextView);
        contactImageView = findViewById(R.id.idViewContactImageView);
        phoneButton = findViewById(R.id.idPhoneImageButton);
        messageButton = findViewById(R.id.idMessageImageButton);
        emailButton = findViewById(R.id.idMailImageButton);
        setupButtonListeners();

        Bundle bundle = getIntent().getExtras();
        contactModel = (ContactModel) getIntent().getSerializableExtra("ContactModel");
        nameTextView.setText(contactModel.getFirstName() + ' ' + contactModel.getLastName());
        phoneTextView.setText(contactModel.getNumber());
        emailTextView.setText(contactModel.getEmail());
        if (contactModel.getContactImage() != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(contactModel.getContactImage(), 0, contactModel.getContactImage().length);
            contactImageView.setImageBitmap(Bitmap.createScaledBitmap(bmp, 1000, 1000, false));
        }
    }

    /**
     * Method to setup listeners for all views
     */
    private void setupButtonListeners() {
        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(contactModel.getNumber().toString()));
                startActivity(callIntent);
            }
        });

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent to push to edit contact view
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.putExtra("sms_body", "");
                sendIntent.setType("vnd.android-dir/mms-sms");
                startActivity(sendIntent);
            }
        });

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String to = contactModel.getEmail().toString();
                String subject = "";
                String message = "";
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);
                // need this to prompts email client only
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client"));
            }
        });
    }

    /**
     * Method to setup activity launcher to open edit contact intent and handle callback action
     */
    private void setupActivityLauncher() {
        editContactActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // Checks if result is ok or not, if ok then pass it to main activity to save
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

    /**
     * On options item selected for menu of action bar
     * @param: item: MenuItem
     * @return: boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.idEditContactMenuItem) {
            // Intent to launch new contact activity
            Intent createNewContactIntent = new Intent(ViewContactActivity.this, CreateNewContactActivity.class);
            createNewContactIntent.putExtra("ContactModel", contactModel);
            editContactActivityLauncher.launch(createNewContactIntent);
        } else if (item.getItemId() == R.id.idMenuDeleteContact) {
            Dialog.instance.showDialog(this, "Do you want to delete this contact?", true, "Cancel", new DialogResultHandler() {
                @Override
                public void firstButtonClicked() {
                    Intent dataPassIntent = new Intent();
                    dataPassIntent.putExtra("DeleteContactModel", contactModel);
                    setResult(R.string.result_ok_delete, dataPassIntent);
                    finish();
                }

                @Override
                public void secondButtonClicked() {

                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Creates menu for action bar
     * @param menu: Menu
     * @return: boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_contact_menu, menu);
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return true;
    }
}