package com.example.madcamp24_week1;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TravelRecordData {

    private static List<TravelRecordDTO> travelRecords = new ArrayList<>();
    private static int nextId = 1;

    static {
        // Mock 데이터
        travelRecords.add(new TravelRecordDTO(nextId++, R.drawable.pic1, "서울 여행 기록 1", LocalDate.parse("2024-06-29"), 0,
                "android.resource://com.example.madcamp24_week1/" + R.drawable.pic1));
        travelRecords.add(new TravelRecordDTO(nextId++, R.drawable.pic2, "수원 여행 기록 1", LocalDate.parse("2024-06-29"), 1,
                "android.resource://com.example.madcamp24_week1/" + R.drawable.pic2));
        travelRecords.add(new TravelRecordDTO(nextId++, R.drawable.pic3, "강릉 여행 기록 1", LocalDate.parse("2024-06-29"), 2,
                "android.resource://com.example.madcamp24_week1/" + R.drawable.pic3));
        travelRecords.add(new TravelRecordDTO(nextId++, R.drawable.pic4, "대전 여행 기록 1", LocalDate.parse("2024-06-29"), 3,
                "android.resource://com.example.madcamp24_week1/" + R.drawable.pic4));
        travelRecords.add(new TravelRecordDTO(nextId++, R.drawable.pic5, "대구 여행 기록 1", LocalDate.parse("2024-06-29"), 4,
                "android.resource://com.example.madcamp24_week1/" + R.drawable.pic5));
        travelRecords.add(new TravelRecordDTO(nextId++, R.drawable.pic7, "제주 여행 기록 1", LocalDate.parse("2024-06-29"), 6,
                "android.resource://com.example.madcamp24_week1/" + R.drawable.pic7));
        ///
        travelRecords.add(new TravelRecordDTO(nextId++, R.drawable.pic8, "서울 여행 기록 2", LocalDate.parse("2024-07-03"), 0,
                "android.resource://com.example.madcamp24_week1/" + R.drawable.pic8));
        travelRecords.add(new TravelRecordDTO(nextId++, R.drawable.pic9, "수원 여행 기록 2", LocalDate.parse("2024-07-03"), 1,
                "android.resource://com.example.madcamp24_week1/" + R.drawable.pic9));
        travelRecords.add(new TravelRecordDTO(nextId++, R.drawable.pic15, "강릉 여행 기록 2", LocalDate.parse("2024-07-03"), 2,
                "android.resource://com.example.madcamp24_week1/" + R.drawable.pic15));
        travelRecords.add(new TravelRecordDTO(nextId++, R.drawable.pic11, "대전 여행 기록 2", LocalDate.parse("2024-07-03"), 3,
                "android.resource://com.example.madcamp24_week1/" + R.drawable.pic11));
        travelRecords.add(new TravelRecordDTO(nextId++, R.drawable.pic12, "대구 여행 기록 2", LocalDate.parse("2024-07-03"), 4,
                "android.resource://com.example.madcamp24_week1/" + R.drawable.pic12));
        travelRecords.add(new TravelRecordDTO(nextId++, R.drawable.pic17, "광주 여행 기록 2", LocalDate.parse("2024-07-03"), 5,
                "android.resource://com.example.madcamp24_week1/" + R.drawable.pic17));
        travelRecords.add(new TravelRecordDTO(nextId++, R.drawable.pic14, "제주 여행 기록 2", LocalDate.parse("2024-07-03"), 6,
                "android.resource://com.example.madcamp24_week1/" + R.drawable.pic14));

    }

    public static List<TravelRecordDTO> getTravelRecords() {
        return travelRecords;
    }

    public static List<TravelRecordDTO> getTravelRecordsForRegion(int regionId) {
        List<TravelRecordDTO> filteredRecords = new ArrayList<>();
        for (TravelRecordDTO record : travelRecords) {
            if (record.getRegionId() == regionId) {
                filteredRecords.add(record);
            }
        }
        return filteredRecords;
    }

    public static void addTravelRecord(TravelRecordDTO record) {
        record = new TravelRecordDTO(nextId++, record.getImageResId(), record.getMemo(), record.getDate(), record.getRegionId(), record.getImageUri());
        travelRecords.add(record);
        notifyDataChanged();
    }

    public static void updateTravelRecord(TravelRecordDTO updatedRecord) {
        for (int i = 0; i < travelRecords.size(); i++) {
            if (travelRecords.get(i).getId() == updatedRecord.getId()) {
                travelRecords.set(i, updatedRecord);
                notifyDataChanged();
                return;
            }
        }
    }

    public static void deleteTravelRecord(int id) {
        travelRecords.removeIf(record -> record.getId() == id);
        notifyDataChanged();
    }

    public static TravelRecordDTO getTravelRecordById(int id) {
        for (TravelRecordDTO record : travelRecords) {
            if (record.getId() == id) {
                return record;
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


    public static int getNextId() {
        return nextId++;
    }

    public static List<TravelRecordDTO> getTravelRecordsForRegionAndMonth(int regionId, Month month) {
        return travelRecords.stream()
                .filter(record -> record.getRegionId() == regionId && record.getDate().getMonth() == month)
                .collect(Collectors.toList());
    }

}
