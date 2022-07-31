package com.cambrian.jav1001_finalproject;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactsViewModel extends AndroidViewModel {
    private ContactDao contactDao;
    private LiveData<List<ContactModel>> contactModels;
    public List<ContactModel> filteredContactModels;

    public ContactsViewModel(Application application) {
        super(application);
        ContactRoomDatabase db = ContactRoomDatabase.getDatabase(application);
        contactDao = db.contactDao();
        contactModels = contactDao.getAllContacts();
    }

    LiveData<List<ContactModel>> getAllContacts() {
        return contactModels;
    }

    public void insert (ContactModel contactModel) {
        new insertAsyncTask(contactDao).execute(contactModel);
    }

    public void delete (ContactModel contactModel) {
        new deleteAsyncTask(contactDao).execute(contactModel);
    }

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
