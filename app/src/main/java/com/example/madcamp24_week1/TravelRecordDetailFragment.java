package com.example.madcamp24_week1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class TravelRecordDetailFragment extends DialogFragment {

    private static final String ARG_ID = "id";
    private static final String ARG_IMAGE_RES_ID = "imageResId";
    private static final String ARG_IMAGE_URI = "imageUri";
    private static final String ARG_MEMO = "memo";
    private static final String ARG_DATE = "date";
    private static final String ARG_REGION_ID = "regionId";

    private int id;
    private int imageResId;
    private String imageUri;
    private String memo;
    private String date;
    private int regionId;

    private OnTravelRecordUpdatedListener listener;
    private LinearLayout contactButtonsLayout;
    private RecyclerView contactDetailsRecyclerView;
    private ContactAdapter contactAdapter;
    private List<ContactDTO> contacts;

    public static TravelRecordDetailFragment newInstance(int id, int imageResId, String imageUri, String memo, LocalDate date, int regionId) {
        TravelRecordDetailFragment fragment = new TravelRecordDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        args.putInt(ARG_IMAGE_RES_ID, imageResId);
        args.putString(ARG_IMAGE_URI, imageUri);
        args.putString(ARG_MEMO, memo);
        args.putString(ARG_DATE, date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
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
            imageUri = getArguments().getString(ARG_IMAGE_URI);
            memo = getArguments().getString(ARG_MEMO);
            date = getArguments().getString(ARG_DATE);
            regionId = getArguments().getInt(ARG_REGION_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel_record_detail, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView memoTextView = view.findViewById(R.id.memoTextView);
        TextView dateTextView = view.findViewById(R.id.dateTextView);
        TextView regionTextView = view.findViewById(R.id.regionTextView);
        contactButtonsLayout = view.findViewById(R.id.contactButtonsLayout);
        contactDetailsRecyclerView = view.findViewById(R.id.contactDetailsRecyclerView);
        Button editButton = view.findViewById(R.id.editButton);
        Button deleteButton = view.findViewById(R.id.deleteButton);

        if (imageUri != null && !imageUri.isEmpty()) {
            Glide.with(this).load(imageUri).into(imageView);
        } else {
            Glide.with(this).load(imageResId).into(imageView);
        }

        memoTextView.setText(memo);
        dateTextView.setText(date);
        regionTextView.setText(String.valueOf(regionId));

        loadAndDisplayContacts();

        editButton.setOnClickListener(v -> {
            TravelRecordEditFragment editFragment = TravelRecordEditFragment.newInstance(id, imageResId, imageUri, memo, date, regionId);
            editFragment.setOnTravelRecordEditListener((editedId, editedImageResId, editedMemo, editedDate, editedRegionId, editedImageUri, editedTaggedContacts) -> {
                TravelRecordDTO updatedRecord = new TravelRecordDTO(editedId, editedImageResId, editedMemo, editedDate, editedRegionId, editedImageUri);
                TravelRecordData.updateTravelRecord(updatedRecord);

                // 현재 태깅된 연락처 목록을 가져옴
                List<ContactDTO> currentContacts = TravelRecordContactData.getContactsForTravelRecord(editedId);

                // 새로운 태깅된 연락처와 비교하여 추가 및 제거
                for (ContactDTO contact : currentContacts) {
                    if (!editedTaggedContacts.contains(contact)) {
                        TravelRecordContactData.removeTravelRecordContact(editedId, contact.getId());
                    }
                }
                for (ContactDTO contact : editedTaggedContacts) {
                    if (!currentContacts.contains(contact)) {
                        TravelRecordContactDTO recordContact = new TravelRecordContactDTO(editedId, contact.getId());
                        TravelRecordContactData.addTravelRecordContact(recordContact);
                    }
                }

                if (listener != null) {
                    listener.onTravelRecordUpdated(updatedRecord);
                }
                dismiss();
            });
            editFragment.show(getParentFragmentManager(), "travel_record_edit");
        });

        deleteButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTravelRecordDeleted(id);
            }
            dismiss();
        });

        return view;
    }

    public void setOnTravelRecordUpdatedListener(OnTravelRecordUpdatedListener listener) {
        this.listener = listener;
    }

    public interface OnTravelRecordUpdatedListener {
        void onTravelRecordUpdated(TravelRecordDTO updatedRecord);
        void onTravelRecordDeleted(int id);
    }

    private void loadAndDisplayContacts() {
        contacts = TravelRecordContactData.getContactsForTravelRecord(id);
        for (ContactDTO contact : contacts) {
            Button contactButton = new Button(getContext());
            contactButton.setText(contact.getName());
            contactButton.setOnClickListener(v -> displayContactDetails(contact));
            contactButtonsLayout.addView(contactButton);
        }
    }

    private void displayContactDetails(ContactDTO contact) {
        List<ContactDTO> singleContactList = List.of(contact);
        contactAdapter = new ContactAdapter(singleContactList, this::onContactClick);
        contactDetailsRecyclerView.setAdapter(contactAdapter);
        contactDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        contactDetailsRecyclerView.setVisibility(View.VISIBLE);
    }

    private void onContactClick(int contactId) {
        // 연락처 클릭시 이벤트를 처리
    }
}
