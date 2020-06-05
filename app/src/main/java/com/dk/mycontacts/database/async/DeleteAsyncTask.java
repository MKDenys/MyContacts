package com.dk.mycontacts.database.async;

import android.os.AsyncTask;

import com.dk.mycontacts.database.ContactDao;
import com.dk.mycontacts.model.Contact;

public class DeleteAsyncTask extends AsyncTask<Contact, Void, Void> {
    private ContactDao contactDao;

    public DeleteAsyncTask(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    @Override
    protected Void doInBackground(Contact... contacts) {
        this.contactDao.delete(contacts);
        return null;
    }
}
