package com.example.madcamp24_week1;

import java.util.ArrayList;
import java.util.List;

public class ContactData {
    private static List<ContactDTO> contacts = new ArrayList<>();
    private static int nextId = 1;

    static {
        addMockContacts();
    }

    public static List<ContactDTO> getContacts() {
        return contacts;
    }

    public static ContactDTO getContactById(int id) {
        for (ContactDTO contact : contacts) {
            if (contact.getId() == id) {
                return contact;
            }
        }
        return null;
    }

    public static void addContact(ContactDTO contact) {
        contact.setId(nextId++);
        contacts.add(contact);
    }

    public static void updateContact(ContactDTO updatedContact) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId() == updatedContact.getId()) {
                contacts.set(i, updatedContact);
                return;
            }
        }
    }

    public static void deleteContact(int id) {
        contacts.removeIf(contact -> contact.getId() == id);
    }

    private static void addMockContacts() {
        contacts.add(new ContactDTO(nextId++, "김철호", "0104156780"));
        contacts.add(new ContactDTO(nextId++, "김철호1", "01065143210"));
        contacts.add(new ContactDTO(nextId++, "김철호2", "0105555555"));
    }
}
