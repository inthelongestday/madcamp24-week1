package com.example.madcamp24_week1;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
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
        return inflater.inflate(R.layout.fragment_travel_record, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getArguments() != null) {
            int regionId = getArguments().getInt(ARG_REGION_ID);
            Log.d("TravelRecordFragment", "Region ID: " + regionId);
            travelRecordList = TravelRecordData.getTravelRecordsForRegion(regionId);
            Log.d("TravelRecordFragment", "Records for region " + regionId + ": " + travelRecordList.size());
        } else {
            travelRecordList = TravelRecordData.getTravelRecords();
            Log.d("TravelRecordFragment", "All records: " + travelRecordList.size());
        }

        travelRecordAdapter = new TravelRecordAdapter(travelRecordList, (travelRecord, position) -> {
            // Show detail dialog for editing or deleting
            TravelRecordDetailFragment detailFragment = TravelRecordDetailFragment.newInstance(
                    travelRecord.getId(), travelRecord.getImageResId(), travelRecord.getMemo(), travelRecord.getDate(), travelRecord.getRegionId());
            detailFragment.setOnTravelRecordUpdatedListener(new TravelRecordDetailFragment.OnTravelRecordUpdatedListener() {
                @Override
                public void onTravelRecordUpdated(TravelRecordDTO updatedRecord) {
                    TravelRecordData.updateTravelRecord(updatedRecord);
                    travelRecordList.set(position, updatedRecord);
                    travelRecordAdapter.notifyItemChanged(position);
                }

                @Override
                public void onTravelRecordDeleted(int id) {
                    TravelRecordData.deleteTravelRecord(id);
                    travelRecordList.remove(position);
                    travelRecordAdapter.notifyItemRemoved(position);
                }
            });
            detailFragment.show(getParentFragmentManager(), "travel_record_detail");
        });

        recyclerView.setAdapter(travelRecordAdapter);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            // Open TravelRecordEditFragment for creating new record
            TravelRecordEditFragment editFragment = TravelRecordEditFragment.newInstance(-1, 0, "", "", getArguments().getInt(ARG_REGION_ID));
            editFragment.setOnTravelRecordEditListener((id, imageResId, memo, date, regionId) -> {
                TravelRecordDTO newRecord = new TravelRecordDTO(TravelRecordData.getNextId(), imageResId, memo, date, regionId);
                TravelRecordData.addTravelRecord(newRecord);
                travelRecordList.add(newRecord);
                travelRecordAdapter.notifyItemInserted(travelRecordList.size() - 1);
            });
            editFragment.show(getParentFragmentManager(), "travel_record_add");
        });
    }

    public void onTravelRecordEdited(int id, int imageResId, String memo, String date, int regionId) {
        for (int i = 0; i < travelRecordList.size(); i++) {
            TravelRecordDTO record = travelRecordList.get(i);
            if (record.getId() == id) {
                TravelRecordDTO updatedRecord = new TravelRecordDTO(id, imageResId, memo, date, regionId);
                travelRecordList.set(i, updatedRecord);
                travelRecordAdapter.notifyItemChanged(i);
                break;
            }
        }
    }
}
