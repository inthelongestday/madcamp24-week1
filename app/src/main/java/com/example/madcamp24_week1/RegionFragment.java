package com.example.madcamp24_week1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class RegionFragment extends Fragment {

    private List<RegionDTO> regionList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_region, container, false);
        regionList = RegionData.getRegions();
        Log.d("RegionFragment", "Regions loaded: " + regionList.size());

        ImageView ivSeoul = view.findViewById(R.id.regionSeoul);
        ImageView ivGyeonggi = view.findViewById(R.id.regionGyeonggi);
        ImageView ivGangwon = view.findViewById(R.id.regionGangwon);
        ImageView ivChungcheong = view.findViewById(R.id.regionChungcheong);
        ImageView ivGyeongsang = view.findViewById(R.id.regionGyeongsang);
        ImageView ivJeolla = view.findViewById(R.id.regionJeolla);
        ImageView ivJeju = view.findViewById(R.id.regionJeju);

        ivSeoul.setOnClickListener(v -> regionClickHandler(regionList.get(0)));
        ivGyeonggi.setOnClickListener(v -> regionClickHandler(regionList.get(1)));
        ivGangwon.setOnClickListener(v -> regionClickHandler(regionList.get(2)));
        ivChungcheong.setOnClickListener(v -> regionClickHandler(regionList.get(3)));
        ivGyeongsang.setOnClickListener(v -> regionClickHandler(regionList.get(4)));
        ivJeolla.setOnClickListener(v -> regionClickHandler(regionList.get(5)));
        ivJeju.setOnClickListener(v -> regionClickHandler(regionList.get(6)));

        return view;
    }

    private void regionClickHandler(RegionDTO region) {
        if (getActivity() instanceof OnRegionSelectedListener) {
            ((OnRegionSelectedListener) getActivity()).onRegionSelected(region.getId());
        }
    }

    public interface OnRegionSelectedListener {
        void onRegionSelected(int regionId);
    }
}
