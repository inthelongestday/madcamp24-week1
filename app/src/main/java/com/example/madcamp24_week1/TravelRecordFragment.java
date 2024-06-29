package com.example.madcamp24_week1;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TravelRecordFragment extends Fragment {

    private static final String ARG_REGION_ID = "region_id";
    private RecyclerView recyclerView;
    private TravelRecordAdapter travelRecordAdapter;
    private List<TravelRecordDTO> travelRecordList;

    public static TravelRecordFragment newInstance(int regionId) {
        TravelRecordFragment fragment = new TravelRecordFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_REGION_ID, regionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel_record, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getArguments() != null) {
            int regionId = getArguments().getInt(ARG_REGION_ID);
            travelRecordList = TravelRecordData.getTravelRecordsForRegion(regionId);
        } else {
            travelRecordList = TravelRecordData.getTravelRecords();
        }

        travelRecordAdapter = new TravelRecordAdapter(travelRecordList, (travelRecord, position) -> {
            // Handle item click here
            // For example, open TravelRecordDetailFragment
        });

        recyclerView.setAdapter(travelRecordAdapter);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            TravelRecordEditFragment travelRecordEditFragment = TravelRecordEditFragment.newInstance(-1, 0, "", "", 0);
            travelRecordEditFragment.show(getParentFragmentManager(), "travel_record_add");
        });

        return view;
    }

    public void onTravelRecordEdited(int id, int imageResId, String memo, String date, int regionId) {
        if (id == -1) {
            TravelRecordDTO newRecord = new TravelRecordDTO(travelRecordList.size() + 1, imageResId, memo, date, regionId);
            TravelRecordData.addTravelRecord(newRecord);
            travelRecordList.add(newRecord);
            travelRecordAdapter.notifyItemInserted(travelRecordList.size() - 1);
        } else {
            for (int i = 0; i < travelRecordList.size(); i++) {
                TravelRecordDTO record = travelRecordList.get(i);
                if (record.getId() == id) {
                    TravelRecordDTO updatedRecord = new TravelRecordDTO(id, imageResId, memo, date, regionId);
                    TravelRecordData.updateTravelRecord(updatedRecord);
                    travelRecordList.set(i, updatedRecord);
                    travelRecordAdapter.notifyItemChanged(i);
                    break;
                }
            }
        }
    }
}
