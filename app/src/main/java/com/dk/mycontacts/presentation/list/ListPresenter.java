package com.dk.mycontacts.presentation.list;

import com.dk.mycontacts.model.Contact;
import com.dk.mycontacts.model.DataProvider;
import com.dk.mycontacts.model.DataProviderObserver;

import java.util.List;

class ListPresenter implements DataProviderObserver {
    private ListView view;
    private DataProvider dataProvider;

    public ListPresenter(ListView view, DataProvider dataProvider) {
        this.view = view;
        this.dataProvider = dataProvider;
        this.dataProvider.registerObserver(this);
        this.dataProvider.startLoadContacts();
    }

    public void detach(){
        view = null;
        dataProvider.removeObserver(this);
        dataProvider = null;
    }

    public void onContactSelected(Contact contact){
        view.showContactDetails(contact);
    }

    public void onAddContactAction(){
        view.showContactDetails(new Contact());
    }

    public void onDeleteContactAction(Contact contact){
        dataProvider.deleteContact(contact);
    }

    @Override
    public void onContactsUpdate(List<Contact> contacts) {
        view.showContactsList(contacts);
    }

    public void onRestoreDataAction(){
        dataProvider.deleteAll();
        createFakeContacts();
    }

    private void createFakeContacts(){
        dataProvider.saveContact(createContact("Charlie", "Brooker"));
        dataProvider.saveContact(createContact("Konnie", "Huq"));
        dataProvider.saveContact(createContact("Jesse", "Armstrong"));
        dataProvider.saveContact(createContact("Rashida", "Jones"));
        dataProvider.saveContact(createContact("Michael", "Armstrong"));
        dataProvider.saveContact(createContact("William", "Bridges"));
    }

    private Contact createContact(String firstName, String lastName){
        Contact contact = new Contact();
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setEmail(String.format("%s.%s@gm.com", firstName.toLowerCase(), lastName.toLowerCase()));
        return contact;
    }
}
