package com.example.madcamp24_week1;

import java.util.ArrayList;
import java.util.List;

public class ContactData {

    private static List<ContactDTO> contacts = new ArrayList<>();
    private static int nextId = 1;

    public static List<ContactDTO> getContacts() {
        return contacts;
    }

    static {
        //mock
        contacts.add(new ContactDTO(nextId++, "김철호","01012312321"));
        contacts.add(new ContactDTO(nextId++, "김철호2","01011233333"));
        contacts.add(new ContactDTO(nextId++, "김철호3","01213133321"));

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
        boolean removed = contacts.removeIf(contact -> contact.getId() == id);
        System.out.println("Deleted: " + removed);  // 로그로 삭제 성공 여부 확인
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
