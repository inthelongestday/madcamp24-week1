package com.example.madcamp24_week1;

public class TravelRecordDTO {

    private final int id;
    private final int imageResId;
    private final String memo;
    private final String date;
    private final int regionId;

    public TravelRecordDTO(int id, int imageResId, String memo, String date, int regionId) {
        this.id = id;
        this.imageResId = imageResId;
        this.memo = memo;
        this.date = date;
        this.regionId = regionId;
    }

    public int getId() {
        return id;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getMemo() {
        return memo;
    }

    public String getDate() {
        return date;
    }

    public int getRegionId() {
        return regionId;
    }

    public TravelRecordDTO withImageResId(int newImageResId) {
        return new TravelRecordDTO(id, newImageResId, memo, date, regionId);
    }

    public TravelRecordDTO withMemo(String newMemo) {
        return new TravelRecordDTO(id, imageResId, newMemo, date, regionId);
    }

    public TravelRecordDTO withDate(String newDate) {
        return new TravelRecordDTO(id, imageResId, memo, newDate, regionId);
    }

    public TravelRecordDTO withRegionId(int newRegionId) {
        return new TravelRecordDTO(id, imageResId, memo, date, newRegionId);
    }
}
