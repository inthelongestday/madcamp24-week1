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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class TravelRecordFragment extends Fragment {

    private static final int REQUEST_CAMERA_PERMISSION = 101;
    private static final String ARG_REGION_ID = "region_id";
    private RecyclerView recyclerView;
    private TravelRecordAdapter travelRecordAdapter;
    private List<TravelRecordDTO> travelRecordList;
    private LocalDate currentDate;
    private TextView tvCurrentMonth;
    private Uri photoURI;
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
                            editFragment.setOnTravelRecordEditListener((id, imageResId, memo, date, regionId, imageUri1, taggedContacts) -> {
                                TravelRecordDTO newRecord = new TravelRecordDTO(TravelRecordData.getNextId(), imageResId, memo, date, regionId, imageUri1);
                                TravelRecordData.addTravelRecord(newRecord);

                                // 태깅된 연락처 정보 저장
                                for (ContactDTO contact : taggedContacts) {
                                    TravelRecordContactDTO recordContact = new TravelRecordContactDTO(newRecord.getId(), contact.getId());
                                    TravelRecordContactData.addTravelRecordContact(recordContact);
                                }

                                travelRecordList.add(newRecord);
                                travelRecordAdapter.notifyItemInserted(travelRecordList.size() - 1);
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
        fab.setOnClickListener(v -> cameraIntent());
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

    public void onTravelRecordEdited(int id, int imageResId, String memo, LocalDate date, int regionId, String imageUri, List<ContactDTO> taggedContacts) {
        for (int i = 0; i < travelRecordList.size(); i++) {
            TravelRecordDTO record = travelRecordList.get(i);
            if (record.getId() == id) {
                TravelRecordDTO updatedRecord = new TravelRecordDTO(id, imageResId, memo, date, regionId, imageUri);
                travelRecordList.set(i, updatedRecord);
                travelRecordAdapter.notifyItemChanged(i);

                // 태깅된 연락처 업데이트
                List<ContactDTO> currentContacts = TravelRecordContactData.getContactsForTravelRecord(id);
                for (ContactDTO contact : currentContacts) {
                    if (!taggedContacts.contains(contact)) {
                        TravelRecordContactData.removeTravelRecordContact(id, contact.getId());
                    }
                }
                for (ContactDTO contact : taggedContacts) {
                    if (!currentContacts.contains(contact)) {
                        TravelRecordContactDTO recordContact = new TravelRecordContactDTO(id, contact.getId());
                        TravelRecordContactData.addTravelRecordContact(recordContact);
                    }
                }
                break;
            }
        }
    }

    private void cameraIntent() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchCamera();
            } else {
                Toast.makeText(getContext(), "Camera permission is required to take photos", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
