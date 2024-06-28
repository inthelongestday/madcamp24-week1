package com.example.madcamp24_week1;

import java.util.ArrayList;
import java.util.List;

public class ContactData {
    public static List<ContactDTO> getContacts() {
        List<ContactDTO> contactDTOS = new ArrayList<>();
        contactDTOS.add(new ContactDTO("김철호", "0104156780"));
        contactDTOS.add(new ContactDTO("김철호1", "01065143210"));
        contactDTOS.add(new ContactDTO("김철호2", "0105555555"));
        return contactDTOS;
    }
}