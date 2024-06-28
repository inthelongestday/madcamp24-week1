package com.example.madcamp24_week1;

public class GalleryDTO {
    private int imageResId;
    private String memo;

    public GalleryDTO(int imageResId) {
        this.imageResId = imageResId;
        this.memo = "";
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
