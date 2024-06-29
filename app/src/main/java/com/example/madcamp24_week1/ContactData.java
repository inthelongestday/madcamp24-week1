package com.example.madcamp24_week1;

import java.util.ArrayList;
import java.util.List;

public class ContactData {

    private static List<ContactDTO> contacts = new ArrayList<>();
    private static int nextId = 1;

    public static List<ContactDTO> getContacts() {
        return contacts;
    }

    public static void addContact(ContactDTO contact) {
        contact.setId(nextId++);
        contacts.add(contact);
        notifyDataChanged();
    }

    public static void updateContact(ContactDTO updatedContact) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId() == updatedContact.getId()) {
                contacts.set(i, updatedContact);
                notifyDataChanged();
                return;
            }
        }
    }

    public static void deleteContact(int id) {
        contacts.removeIf(contact -> contact.getId() == id);
        notifyDataChanged();
    }

    public static ContactDTO getContactById(int id) {
        for (ContactDTO contact : contacts) {
            if (contact.getId() == id) {
                return contact;
            }
        }
        return null;
    }

    private static void notifyDataChanged() {
        if (listener != null) {
            listener.onDataChanged();
        }
    }

    public interface OnDataChangedListener {
        void onDataChanged();
    }

    private static OnDataChangedListener listener;

    public static void setOnDataChangedListener(OnDataChangedListener listener) {
        ContactData.listener = listener;
    }
}
