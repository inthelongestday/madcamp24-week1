package com.example.madcamp24_week1;

import java.util.ArrayList;
import java.util.List;

public class TravelRecordData {
    private static List<TravelRecordDTO> travelRecords = new ArrayList<>();
    private static int nextId = 1;

    public static List<TravelRecordDTO> getTravelRecords() {
        return travelRecords;
    }

    public static void addTravelRecord(TravelRecordDTO record) {
        record = new TravelRecordDTO(nextId++, record.getImageResId(), record.getMemo(), record.getDate(), record.getRegionId());
        travelRecords.add(record);
    }

    public static void updateTravelRecord(TravelRecordDTO updatedRecord) {
        for (int i = 0; i < travelRecords.size(); i++) {
            if (travelRecords.get(i).getId() == updatedRecord.getId()) {
                travelRecords.set(i, updatedRecord);
                return;
            }
        }
    }

    public static void deleteTravelRecord(int id) {
        travelRecords.removeIf(record -> record.getId() == id);
    }

    public static TravelRecordDTO getTravelRecordById(int id) {
        for (TravelRecordDTO record : travelRecords) {
            if (record.getId() == id) {
                return record;
            }
        }
        return null;
    }
}
