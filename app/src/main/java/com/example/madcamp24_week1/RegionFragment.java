package com.example.madcamp24_week1;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class RegionFragment extends Fragment {

    private List<RegionDTO> regionList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_region, container, false);
        regionList = RegionData.getRegions();
        Log.d("RegionFragment", "Regions loaded: " + regionList.size());

        LinearLayout layout = view.findViewById(R.id.regionLayout);
        for (RegionDTO region : regionList) {
            Log.d("RegionFragment", "Adding button for region: " + region.getName());

            // Custom layout for region button
            View regionView = inflater.inflate(R.layout.region_item, layout, false);
            ImageView imageView = regionView.findViewById(R.id.regionImage);
            TextView textView = regionView.findViewById(R.id.regionName);

            imageView.setImageResource(region.getImageResId());
            textView.setText(region.getName());

            regionView.setOnClickListener(v -> {
                Log.d("RegionFragment", "Region selected: " + region.getId());
                if (getActivity() instanceof OnRegionSelectedListener) {
                    ((OnRegionSelectedListener) getActivity()).onRegionSelected(region.getId());
                }
            });

            layout.addView(regionView);
        }

        return view;
    }

    public interface OnRegionSelectedListener {
        void onRegionSelected(int regionId);
    }
}
