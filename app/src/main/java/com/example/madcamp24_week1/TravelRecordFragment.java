package com.example.madcamp24_week1;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;

public class TravelRecordFragment extends Fragment {

    private static final int REQUEST_CAMERA_PERMISSION = 101;
    private static final String ARG_REGION_ID = "region_id";
    private Uri photoURI;
    private RecyclerView recyclerView;
    private TravelRecordAdapter travelRecordAdapter;
    private List<TravelRecordDTO> travelRecordList;
    private ActivityResultLauncher<Intent> takePhotoLauncher;

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

        takePhotoLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == FragmentActivity.RESULT_OK) {
                    if (photoURI != null) {
                        String imageUri = photoURI.toString();
                        // Open TravelRecordEditFragment for creating new record
                        TravelRecordEditFragment editFragment = TravelRecordEditFragment.newInstance(-1, 0, imageUri, "", "", getArguments().getInt(ARG_REGION_ID));
                        editFragment.setOnTravelRecordEditListener(new TravelRecordEditFragment.OnTravelRecordEditListener() {
                            @Override
                            public void onTravelRecordEdited(int id, int imageResId, String memo, String date, int regionId, String imageUri) {
                                TravelRecordDTO newRecord = new TravelRecordDTO(TravelRecordData.getNextId(), imageResId, memo, date, regionId, imageUri);
                                TravelRecordData.addTravelRecord(newRecord);
                                travelRecordList.add(newRecord);
                                travelRecordAdapter.notifyItemInserted(travelRecordList.size() - 1);
                            }
                        });
                        editFragment.show(getParentFragmentManager(), "travel_record_add");
                    }
                }
            }
        );

        return view;
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

        travelRecordAdapter = new TravelRecordAdapter(getContext(), travelRecordList, new TravelRecordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TravelRecordDTO travelRecord, int position) {
                // Show detail dialog for editing or deleting
                TravelRecordDetailFragment detailFragment = TravelRecordDetailFragment.newInstance(
                        travelRecord.getId(), travelRecord.getImageResId(), travelRecord.getImageUri(), travelRecord.getMemo(), travelRecord.getDate(), travelRecord.getRegionId());
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
        });

        recyclerView.setAdapter(travelRecordAdapter);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> cameraIntent());
    }

    private void cameraIntent() {
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CAMERA_PERMISSION);
        } else {
            launchCamera();
        }
    }

    private void launchCamera() {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhotoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            String timeStamp = new DateFormat().format("yyyyMMdd_HHmmss", new Date()).toString();
            String imageFileName = "JPEG_" + timeStamp + "_";

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, imageFileName);
            photoURI = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            takePhotoLauncher.launch(takePhotoIntent);
        }
    }

    public void onTravelRecordEdited(int id, int imageResId, String memo, String date, int regionId, String imageUri) {
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
