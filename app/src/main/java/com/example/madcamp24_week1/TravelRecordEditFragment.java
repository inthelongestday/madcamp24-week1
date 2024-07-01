package com.example.madcamp24_week1;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.Manifest;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.button.MaterialButton;

import java.util.Date;

public class TravelRecordEditFragment extends DialogFragment {

    private static final int REQUEST_CAMERA_PERMISSION = 101;
    private static final String ARG_ID = "id";
    private static final String ARG_IMAGE_RES_ID = "imageResId";
    private static final String ARG_MEMO = "memo";
    private static final String ARG_DATE = "date";
    private static final String ARG_REGION_ID = "regionId";

    private int id;
    private int imageResId;
    private Uri photoURI;
    private String memo;
    private String date;
    private int regionId;
    private String imageUri;

    private ActivityResultLauncher<Intent> takePhotoLauncher;
    private OnTravelRecordEditListener listener;
    private ImageView imageView;

    public static TravelRecordEditFragment newInstance(int id, int imageResId, String memo, String date, int regionId) {
        TravelRecordEditFragment fragment = new TravelRecordEditFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        args.putInt(ARG_IMAGE_RES_ID, imageResId);
        args.putString(ARG_MEMO, memo);
        args.putString(ARG_DATE, date);
        args.putInt(ARG_REGION_ID, regionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_ID);
            imageResId = getArguments().getInt(ARG_IMAGE_RES_ID);
            memo = getArguments().getString(ARG_MEMO);
            date = getArguments().getString(ARG_DATE);
            regionId = getArguments().getInt(ARG_REGION_ID);
        }

        takePhotoLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == FragmentActivity.RESULT_OK) {
                        if (photoURI != null) {
                            imageView.setImageURI(photoURI);
                            imageUri = photoURI.toString();
                        }
                    }
                }
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel_record_edit, container, false);

        EditText memoEditText = view.findViewById(R.id.memoEditText);
        EditText dateEditText = view.findViewById(R.id.dateEditText);
        imageView = view.findViewById(R.id.imageView);
        MaterialButton saveButton = view.findViewById(R.id.saveButton);
        MaterialButton captureButton = view.findViewById(R.id.captureButton);

        memoEditText.setText(memo);
        dateEditText.setText(date);

        captureButton.setOnClickListener(v -> cameraIntent());

        if (imageResId != 0) {
            imageView.setImageResource(imageResId);
        }
        if (photoURI != null) {
            imageView.setImageURI(photoURI);
        }

        saveButton.setOnClickListener(v -> {
            String updatedMemo = memoEditText.getText().toString();
            String updatedDate = dateEditText.getText().toString();

            if (listener != null) {
                listener.onTravelRecordEdited(id, imageResId, updatedMemo, updatedDate, regionId, imageUri);
            }
            dismiss();
        });

        return view;
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

    public void setOnTravelRecordEditListener(OnTravelRecordEditListener listener) {
        this.listener = listener;
    }

    public interface OnTravelRecordEditListener {
        void onTravelRecordEdited(int id, int imageResId, String memo, String date, int regionId, String imageUri);
    }
}
