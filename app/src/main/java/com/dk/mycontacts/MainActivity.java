package com.dk.mycontacts;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.dk.mycontacts.database.LocalRepositoryImpl;
import com.dk.mycontacts.model.Contact;
import com.dk.mycontacts.model.DataProvider;
import com.dk.mycontacts.model.DataProviderObserver;
import com.dk.mycontacts.model.LocalRepository;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DataProvider {
    private List<DataProviderObserver> observers;
    private List<Contact> contacts;
    private LocalRepository localRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contacts = Collections.emptyList();
        localRepository = new LocalRepositoryImpl();
        observers = new LinkedList<>();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return navController.popBackStack();
    }

    @Override
    public void registerObserver(DataProviderObserver observer) {
        observers.add(observer);
        observer.onContactsUpdate(contacts);
    }

    @Override
    public void removeObserver(DataProviderObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (DataProviderObserver observer: observers) {
            observer.onContactsUpdate(contacts);
        }
    }

    @Override
    public void deleteContact(Contact contact) {
        localRepository.deleteContact(contact);
    }

    @Override
    public void saveContact(Contact contact) {
        localRepository.saveContact(contact);
    }

    @Override
    public void deleteAll() {
        if (!contacts.isEmpty()) {
            for (Contact contact : contacts) {
                localRepository.deleteContact(contact);
            }
        }
    }

    @Override
    public void startLoadContacts() {
        LiveData<List<Contact>> contactsLiveData = localRepository.getAllContacts();
        contactsLiveData.observe(this, observer);
    }

    private Observer<List<Contact>> observer = new Observer<List<Contact>>() {
        @Override
        public void onChanged(List<Contact> contacts) {
            setNotes(contacts);
        }
    };

    private void setNotes(List<Contact> contacts) {
        this.contacts = contacts;
        notifyObservers();
    }
}