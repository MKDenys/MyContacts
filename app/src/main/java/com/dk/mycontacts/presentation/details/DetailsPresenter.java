package com.dk.mycontacts.presentation.details;

import com.dk.mycontacts.model.Contact;
import com.dk.mycontacts.model.DataProvider;

class DetailsPresenter {
    private DetailsView view;
    private DataProvider dataProvider;
    private Contact contact;

    public DetailsPresenter(DetailsView view, Contact contact, DataProvider dataProvider) {
        this.view = view;
        this.contact = contact;
        this.dataProvider = dataProvider;
        showContact();
    }

    public void detach(){
        view = null;
        dataProvider = null;
    }

    public void onSaveAction(String firstName, String lastName, String email){
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setEmail(email);
        dataProvider.saveContact(contact);
        view.showContactList();
    }

    private void showContact(){
        view.showFirstName(contact.getFirstName());
        view.showLastName(contact.getLastName());
        view.showEmail(contact.getEmail());
        view.showAvatar();
    }
}
