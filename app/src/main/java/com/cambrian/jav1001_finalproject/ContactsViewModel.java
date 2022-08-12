package com.cambrian.jav1001_finalproject;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * View model class that holds all business logic
 */
public class ContactsViewModel extends AndroidViewModel {
    private ContactDao contactDao;
    private LiveData<List<ContactModel>> contactModels;

    /**
     * Constructor to create object of view model
     * @param application: Application
     * @return: nothing
     */
    public ContactsViewModel(Application application) {
        super(application);
        ContactRoomDatabase db = ContactRoomDatabase.getDatabase(application);
        contactDao = db.contactDao();
        contactModels = contactDao.getAllContacts();
    }

    /**
     * This provides live data of list of contact models
     * @return: List of contact models
     */
    LiveData<List<ContactModel>> getAllContacts() {
        return contactModels;
    }

    /**
     * Insert method to asynchronously insert contact to database
     * @param contactModel: ContactModel
     * @return: nothing
     */
    public void insert (ContactModel contactModel) {
        new insertAsyncTask(contactDao).execute(contactModel);
    }

    /**
     * Delete method to asynchronously delete contact from database
     * @param contactModel: ContactModel
     * @return: nothing
     */
    public void delete (ContactModel contactModel) {
        new deleteAsyncTask(contactDao).execute(contactModel);
    }

    /**
     * This method provides searching from contact list and returns live data of list of contacts
     * @param searchText: String
     * @return: Live data of list of contact models
     */
    public LiveData<List<ContactModel>> search (String searchText) {
        return contactDao.getContacts(searchText);
    }

    /**
     * Static Class to asynchronously insert contact to database
     * @return: nothing
     */
    private static class insertAsyncTask extends AsyncTask<ContactModel, Void, Void> {
        private ContactDao asyncTaskDao;
        insertAsyncTask(ContactDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ContactModel... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

    /**
     * Static Class to asynchronously delete contact to database
     * @return: nothing
     */
    private static class deleteAsyncTask extends AsyncTask<ContactModel, Void, Void> {
        private ContactDao asyncTaskDao;
        deleteAsyncTask(ContactDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ContactModel... params) {
            asyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
