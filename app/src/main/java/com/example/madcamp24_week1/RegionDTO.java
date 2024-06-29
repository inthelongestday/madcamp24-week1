package com.example.madcamp24_week1;

public class RegionDTO {
    private final int id;
    private final String regionName;

    public RegionDTO(int id, String regionName) {
        this.id = id;
        this.regionName = regionName;
    }

    public int getId() {
        return id;
    }

    public String getRegionName() {
        return regionName;
    }
}
