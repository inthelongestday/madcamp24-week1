package com.example.madcamp24_week1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RegionData {
    private static final List<RegionDTO> regions;

    static {
        List<RegionDTO> regionList = new ArrayList<>();
        regionList.add(new RegionDTO(0, "서울",R.drawable.pic1));
        regionList.add(new RegionDTO(1, "경기",R.drawable.pic2));
        regionList.add(new RegionDTO(2, "강원",R.drawable.pic3));
        regionList.add(new RegionDTO(3, "충청",R.drawable.pic4));
        regionList.add(new RegionDTO(4, "경상",R.drawable.pic5));
        regionList.add(new RegionDTO(5, "전라",R.drawable.pic6));
        regionList.add(new RegionDTO(6, "제주",R.drawable.pic7));
        // 해당 형식으로 이미지를 추가해주세요! 마지막은 각 이미지 경로

        regions = Collections.unmodifiableList(regionList);
    }

    public static List<RegionDTO> getRegions() {
        return regions;
    }
}
