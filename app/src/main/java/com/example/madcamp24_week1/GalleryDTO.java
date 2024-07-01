package com.example.madcamp24_week1;

public class GalleryDTO {
    private int imageResId;
    private String imageUri;
    private String memo;

    public GalleryDTO(int imageResId) {
        this.imageResId = imageResId;
        this.imageUri  = null;
        this.memo = "";
    }

    public GalleryDTO(String imageUri) {
        this.imageResId = 0;
        this.imageUri = imageUri;
        this.memo = "";
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
