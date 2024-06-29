package com.example.madcamp24_week1;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class RegionData {
    private static final List<RegionDTO> regions;

    static {
        List<RegionDTO> regionList = new ArrayList<>();
        regionList.add(new RegionDTO(1, "서울",R.drawable.pic1));
        // 해당 형식으로 이미지를 추가해주세요! 마지막은 각 이미지 경로

        regions = Collections.unmodifiableList(regionList);
    }

    public static List<RegionDTO> getRegions() {
        return regions;
    }
}
