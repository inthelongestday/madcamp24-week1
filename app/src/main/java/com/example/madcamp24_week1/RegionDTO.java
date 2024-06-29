package com.example.madcamp24_week1;

public class RegionDTO {
    private final int id;
    private final String name;

    public RegionDTO(int id, String regionName) {
        this.id = id;
        this.name = regionName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
