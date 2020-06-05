package com.dk.mycontacts.database;

import androidx.lifecycle.LiveData;

import com.dk.mycontacts.App;
import com.dk.mycontacts.database.async.DeleteAsyncTask;
import com.dk.mycontacts.database.async.InsertOrUpdateAsyncTask;
import com.dk.mycontacts.model.Contact;
import com.dk.mycontacts.model.LocalRepository;

import java.util.List;

public class LocalRepositoryImpl implements LocalRepository {
    private ContactDao contactDao;

    public LocalRepositoryImpl() {
        this.contactDao = App.getInstance().getAppDatabase().contactDao();
    }

    @Override
    public LiveData<List<Contact>> getAllContacts() {
        return contactDao.getAll();
    }

    @Override
    public void saveContact(Contact contact) {
        new InsertOrUpdateAsyncTask(contactDao).execute(contact);
    }

    @Override
    public void deleteContact(Contact contact) {
        new DeleteAsyncTask(contactDao).execute(contact);
    }
}
