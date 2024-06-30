package com.example.madcamp24_week1;

public class TravelRecordContactDTO {
    private int travelRecordId;
    private int contactId;

    public TravelRecordContactDTO(int travelRecordId, int contactId) {
        this.travelRecordId = travelRecordId;
        this.contactId = contactId;
    }

    public int getContactId() {
        return contactId;
    }

    public int getTravelRecordId() {
        return travelRecordId;
    }
    //for TravelTags..
}
