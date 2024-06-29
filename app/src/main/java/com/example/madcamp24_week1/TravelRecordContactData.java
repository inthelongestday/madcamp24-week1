package com.example.madcamp24_week1;

import java.util.ArrayList;
import java.util.List;

public class TravelRecordContactData {
    private static List<TravelRecordContactDTO> travelRecordContacts = new ArrayList<>();

    public static List<TravelRecordContactDTO> getTravelRecordContacts() {
        return travelRecordContacts;
    }

    public static void addTravelRecordContact(TravelRecordContactDTO recordContact) {
        travelRecordContacts.add(recordContact);
    }

    public static List<ContactDTO> getContactsForTravelRecord(int travelRecordId) {
        List<ContactDTO> contacts = new ArrayList<>();
        for (TravelRecordContactDTO recordContact : travelRecordContacts) {
            if (recordContact.getTravelRecordId() == travelRecordId) {
                contacts.add(ContactData.getContactById(recordContact.getContactId()));
            }
        }
        return contacts;
    }
}
