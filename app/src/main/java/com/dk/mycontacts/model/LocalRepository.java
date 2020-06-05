package com.dk.mycontacts.model;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface LocalRepository {
    LiveData<List<Contact>> getAllContacts();
    void saveContact(Contact contact);
    void deleteContact(Contact contact);
}
