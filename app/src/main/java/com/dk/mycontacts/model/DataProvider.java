package com.dk.mycontacts.model;

public interface DataProvider {
    void registerObserver(DataProviderObserver observer);
    void removeObserver(DataProviderObserver observer);
    void notifyObservers();
    void startLoadContacts();
    void deleteContact(Contact contact);
    void saveContact(Contact contact);
    void deleteAll();
}
