package com.example.madcamp24_week1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TravelRecordFragment extends Fragment {

    private static final String ARG_REGION_ID = "region_id";
    private RecyclerView recyclerView;
    private TravelRecordAdapter travelRecordAdapter;
    private List<TravelRecordDTO> travelRecordList;
    private LocalDate currentDate;
    private TextView tvCurrentMonth;

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
        tvCurrentMonth = view.findViewById(R.id.tvCurrentMonth);
        ImageView btnPreviousMonth = view.findViewById(R.id.btnPreviousMonth);
        ImageView btnNextMonth = view.findViewById(R.id.btnNextMonth);

        currentDate = LocalDate.now();
        updateMonthDisplay();

        btnPreviousMonth.setOnClickListener(v -> {
            currentDate = currentDate.minusMonths(1);
            updateMonthDisplay();
            updateTravelRecordList();
        });

        btnNextMonth.setOnClickListener(v -> {
            currentDate = currentDate.plusMonths(1);
            updateMonthDisplay();
            updateTravelRecordList();
        });

        updateTravelRecordList();

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            // Open TravelRecordEditFragment for creating new record
            TravelRecordEditFragment editFragment = TravelRecordEditFragment.newInstance(-1, 0, "", "", "", getArguments().getInt(ARG_REGION_ID));
            editFragment.setOnTravelRecordEditListener(new TravelRecordEditFragment.OnTravelRecordEditListener() {
                @Override
                public void onTravelRecordEdited(int id, int imageResId, String memo, LocalDate date, int regionId, String imageUri) {
                    TravelRecordDTO newRecord = new TravelRecordDTO(TravelRecordData.getNextId(), imageResId, memo, date, regionId, imageUri);
                    TravelRecordData.addTravelRecord(newRecord);
                    travelRecordList.add(newRecord);
                    travelRecordAdapter.notifyItemInserted(travelRecordList.size() - 1);
                }
            });
            editFragment.show(getParentFragmentManager(), "travel_record_add");
        });
    }

    private void updateMonthDisplay() {
        tvCurrentMonth.setText(DateTimeFormatter.ofPattern("yyyy.MM").format(currentDate));
    }

    private void updateTravelRecordList() {
        int regionId = getArguments() != null ? getArguments().getInt(ARG_REGION_ID, -1) : -1;
        travelRecordList = TravelRecordData.getTravelRecordsForRegionAndMonth(regionId, currentDate.getMonth());
        if (travelRecordAdapter == null) {
            travelRecordAdapter = new TravelRecordAdapter(getContext(), travelRecordList, this::onTravelRecordSelected);
            recyclerView.setAdapter(travelRecordAdapter);
        } else {
            travelRecordAdapter.updateData(travelRecordList);
            travelRecordAdapter.notifyDataSetChanged();
        }
    }

    private void onTravelRecordSelected(TravelRecordDTO travelRecord, int position) {
        TravelRecordDetailFragment detailFragment = TravelRecordDetailFragment.newInstance(
                travelRecord.getId(), travelRecord.getImageResId(), travelRecord.getImageUri(),
                travelRecord.getMemo(), travelRecord.getDate(), travelRecord.getRegionId());
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
    }

    public void onTravelRecordEdited(int id, int imageResId, String memo, LocalDate date, int regionId, String imageUri) {
        for (int i = 0; i < travelRecordList.size(); i++) {
            TravelRecordDTO record = travelRecordList.get(i);
            if (record.getId() == id) {
                TravelRecordDTO updatedRecord = new TravelRecordDTO(id, imageResId, memo, date, regionId, imageUri);
                travelRecordList.set(i, updatedRecord);
                travelRecordAdapter.notifyItemChanged(i);
                break;
            }
        }
    }
}
