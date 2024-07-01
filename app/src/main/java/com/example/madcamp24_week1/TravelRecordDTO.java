package com.example.madcamp24_week1;

public class TravelRecordDTO {

    private final int id;
    private final int imageResId;
    private final String memo;
    private final String date;
    private final int regionId;
    private final String imageUri;

    public TravelRecordDTO(int id, int imageResId, String memo, String date, int regionId, String imageUri) {
        this.id = id;
        this.imageResId = imageResId;
        this.memo = memo;
        this.date = date;
        this.regionId = regionId;
        this.imageUri = imageUri;
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

    public String getImageUri() {
        return imageUri;
    }

    public TravelRecordDTO withImageResId(int newImageResId) {
        return new TravelRecordDTO(id, newImageResId, memo, date, regionId, imageUri);
    }

    public TravelRecordDTO withMemo(String newMemo) {
        return new TravelRecordDTO(id, imageResId, newMemo, date, regionId, imageUri);
    }

    public TravelRecordDTO withDate(String newDate) {
        return new TravelRecordDTO(id, imageResId, memo, newDate, regionId, imageUri);
    }

    public TravelRecordDTO withRegionId(int newRegionId) {
        return new TravelRecordDTO(id, imageResId, memo, date, newRegionId, imageUri);
    }

    public TravelRecordDTO withImageUri(String newImageUri) {
        return new TravelRecordDTO(id, imageResId, memo, date, regionId, newImageUri);
    }
}
