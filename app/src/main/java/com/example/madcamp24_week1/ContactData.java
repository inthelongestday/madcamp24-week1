package com.example.madcamp24_week1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactData {

    private static List<ContactDTO> contacts = new ArrayList<>();
    private static int nextId = 1;

    public static List<ContactDTO> getContacts() {
        return contacts;
    }

    static {
        //mock
        contacts.add(new ContactDTO(nextId++, "곽빈","01027864251"));
        contacts.add(new ContactDTO(nextId++, "김이겸","01056891245"));
        contacts.add(new ContactDTO(nextId++, "김재환","01012385121"));
        contacts.add(new ContactDTO(nextId++, "김철호","01012312321"));
        contacts.add(new ContactDTO(nextId++, "김택연","01057822851"));
        contacts.add(new ContactDTO(nextId++, "양의지","01013133321"));
        contacts.add(new ContactDTO(nextId++, "정수빈","01074835628"));
        contacts.add(new ContactDTO(nextId++, "허경민","01027834551"));
    }

    public static void addContact(ContactDTO contact) {
        contact.setId(nextId++);
        contacts.add(contact);
        sortContacts();
        notifyDataChanged();
    }

    public static void updateContact(ContactDTO updatedContact) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId() == updatedContact.getId()) {
                contacts.set(i, updatedContact);
                sortContacts();
                notifyDataChanged();
                return;
            }
        }
    }

    public static boolean deleteContact(int id) {
        boolean removed = contacts.removeIf(contact -> contact.getId() == id);
        if (removed) {
            sortContacts();
            notifyDataChanged();
        }
        System.out.println("Deleted: " + removed);
        return removed; // 삭제 성공 여부를 반환
    }

    public static ContactDTO getContactById(int id) {
        for (ContactDTO contact : contacts) {
            if (contact.getId() == id) {
                return contact;
            }
        }
        return null;
    }

    private static void sortContacts() {
        Collections.sort(contacts);
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
