package com.example.madcamp24_week1;

public class RegionDTO {
    private final int id;
    private final String name;

    private final int imageResId;


    public RegionDTO(int id, String name, int imageResId) {
        this.id = id;
        this.name = name;
        this.imageResId = imageResId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }

}
