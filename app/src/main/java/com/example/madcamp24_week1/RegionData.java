package com.example.madcamp24_week1;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class RegionData {
    private static final List<RegionDTO> regions;

    static {
        List<RegionDTO> regionList = new ArrayList<>();
        regionList.add(new RegionDTO(1, "서울"));
        regionList.add(new RegionDTO(2, "대전"));
        regionList.add(new RegionDTO(3, "광주"));
        // UI에 따른 추가 지역을 설정할 예정

        regions = Collections.unmodifiableList(regionList);
    }

    public static List<RegionDTO> getRegions() {
        return regions;
    }
}
