package com.dk.mycontacts.presentation.list;

import com.dk.mycontacts.model.Contact;

import java.util.List;

interface ListView {
    void showContactsList(List<Contact> contacts);
    void showContactDetails(Contact contact);
}
