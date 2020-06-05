package com.dk.mycontacts.model;

import java.util.List;

public interface DataProviderObserver {
    void onContactsUpdate(List<Contact> contacts);
}
