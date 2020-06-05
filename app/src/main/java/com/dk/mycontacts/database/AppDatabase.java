package com.dk.mycontacts.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.dk.mycontacts.model.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();
}
