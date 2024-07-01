package com.example.madcamp24_week1;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.Manifest;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TravelRecordEditFragment extends DialogFragment {

    private static final int REQUEST_CAMERA_PERMISSION = 101;
    private static final String ARG_ID = "id";
    private static final String ARG_IMAGE_RES_ID = "imageResId";
    private static final String ARG_MEMO = "memo";
    private static final String ARG_DATE = "date";
    private static final String ARG_REGION_ID = "regionId";
    private static final String ARG_IMAGE_URI = "imageUri";

    private int currentTravelRecordId; // Current Travel Record ID
    private int imageResId;
    private Uri photoURI;
    private String memo;
    private String date;
    private int regionId;
    private String imageUri;

    private ActivityResultLauncher<Intent> takePhotoLauncher;
    private OnTravelRecordEditListener listener;
    private ImageView imageView;
    private AutoCompleteTextView contactSearchAutoComplete;
    private RecyclerView taggedContactsRecyclerView;
    private List<ContactDTO> taggedContacts = new ArrayList<>();
    private ContactTagAdapter contactTagAdapter;

    public static TravelRecordEditFragment newInstance(int id, int imageResId, String imageUri, String memo, String date, int regionId) {
        TravelRecordEditFragment fragment = new TravelRecordEditFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        args.putInt(ARG_IMAGE_RES_ID, imageResId);
        args.putString(ARG_IMAGE_URI, imageUri);
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
            currentTravelRecordId = getArguments().getInt(ARG_ID);  // Get the current record ID
            imageResId = getArguments().getInt(ARG_IMAGE_RES_ID);
            imageUri = getArguments().getString(ARG_IMAGE_URI);
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
        contactSearchAutoComplete = view.findViewById(R.id.contactSearchAutoComplete);

        memoEditText.setText(memo);
        dateEditText.setText(date);

        captureButton.setOnClickListener(v -> cameraIntent());

        if (imageUri != null && !imageUri.isEmpty()) {
            imageView.setImageURI(Uri.parse(imageUri));
        } else if (imageResId != 0) {
            imageView.setImageResource(imageResId);
        }

        initializeContactSearch();  // 연락처 검색 초기화

        saveButton.setOnClickListener(v -> {
            String updatedMemo = memoEditText.getText().toString();
            String updatedDate = dateEditText.getText().toString();

            if (listener != null) {
                listener.onTravelRecordEdited(currentTravelRecordId, imageResId, updatedMemo, updatedDate, regionId, imageUri);
            }
            dismiss();
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contactSearchAutoComplete = view.findViewById(R.id.contactSearchAutoComplete);
        taggedContactsRecyclerView = view.findViewById(R.id.taggedContactsRecyclerView);

        loadTaggedContacts();  // 태그된 연락처 로드
        initializeContactSearch();
        setupTaggedContactsView();
    }

    private void initializeContactSearch() {
        List<ContactDTO> allContacts = ContactData.getContacts();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line,
                allContacts.stream().map(ContactDTO::getName).collect(Collectors.toList()));
        contactSearchAutoComplete.setAdapter(adapter);

        contactSearchAutoComplete.setOnItemClickListener((adapterView, view, position, id) -> {
            String selectedName = adapterView.getItemAtPosition(position).toString();
            ContactDTO selectedContact = allContacts.stream()
                    .filter(contact -> contact.getName().equals(selectedName))
                    .findFirst().orElse(null);

            if (selectedContact != null && !taggedContacts.contains(selectedContact)) {
                taggedContacts.add(selectedContact);
                TravelRecordContactDTO newContactTag = new TravelRecordContactDTO(currentTravelRecordId, selectedContact.getId());
                TravelRecordContactData.addTravelRecordContact(newContactTag);
                contactTagAdapter.notifyDataSetChanged();  // Update the RecyclerView
            }
            contactSearchAutoComplete.setText("");  // Reset the AutoCompleteTextView
        });
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

    private void setupTaggedContactsView() {
        contactTagAdapter = new ContactTagAdapter(taggedContacts, this::onContactTagClick);
        taggedContactsRecyclerView.setAdapter(contactTagAdapter);
        taggedContactsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void onContactTagClick(ContactDTO contact) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Contact")
                .setMessage("Do you want to remove this contact from the tags?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    taggedContacts.remove(contact);
                    TravelRecordContactData.removeTravelRecordContact(currentTravelRecordId, contact.getId());
                    contactTagAdapter.notifyDataSetChanged();
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    private void loadTaggedContacts() {
        taggedContacts.clear();
        List<ContactDTO> contacts = TravelRecordContactData.getContactsForTravelRecord(currentTravelRecordId);
        taggedContacts.addAll(contacts);

        if (contactTagAdapter != null) {
            contactTagAdapter.notifyDataSetChanged();
        }
    }

    public void setOnTravelRecordEditListener(OnTravelRecordEditListener listener) {
        this.listener = listener;
    }

    public interface OnTravelRecordEditListener {
        void onTravelRecordEdited(int id, int imageResId, String memo, String date, int regionId, String imageUri);
    }
}
