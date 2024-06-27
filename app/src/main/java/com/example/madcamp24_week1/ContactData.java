package com.example.madcamp24_week1;

import java.util.ArrayList;
import java.util.List;

public class ContactData {
    public static List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("김철호", "0104156780"));
        contacts.add(new Contact("김철호1", "01065143210"));
        contacts.add(new Contact("김철호2", "0105555555"));
        return contacts;
    }
}