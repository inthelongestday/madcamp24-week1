package com.example.madcamp24_week1;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.android.material.button.MaterialButton;

public class TravelRecordEditFragment extends DialogFragment {

    private static final String ARG_ID = "id";
    private static final String ARG_IMAGE_RES_ID = "imageResId";
    private static final String ARG_MEMO = "memo";
    private static final String ARG_DATE = "date";
    private static final String ARG_REGION_ID = "regionId";

    private int id;
    private int imageResId;
    private String memo;
    private String date;
    private int regionId;

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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnTravelRecordEditListener) {
            listener = (OnTravelRecordEditListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnTravelRecordEditListener");
        }
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel_record_edit, container, false);
        EditText memoEditText = view.findViewById(R.id.memoEditText);
        EditText dateEditText = view.findViewById(R.id.dateEditText);
        // Region selection spinner setup here
        imageView = view.findViewById(R.id.imageView);
        MaterialButton saveButton = view.findViewById(R.id.saveButton);
        MaterialButton captureButton = view.findViewById(R.id.captureButton);

        memoEditText.setText(memo);
        dateEditText.setText(date);
        if (imageResId != 0) {
            imageView.setImageResource(imageResId);
        }

        captureButton.setOnClickListener(v -> {
            // Implement image capture here
        });

        saveButton.setOnClickListener(v -> {
            String newMemo = memoEditText.getText().toString();
            String newDate = dateEditText.getText().toString();
            // Get selected region ID from spinner
            int newRegionId = 0; // Replace with actual selected region ID
            if (listener != null) {
                listener.onTravelRecordEdited(id, imageResId, newMemo, newDate, newRegionId);
                dismiss();
            }
        });

        return view;
    }

    public interface OnTravelRecordEditListener {
        void onTravelRecordEdited(int id, int imageResId, String memo, String date, int regionId);
    }
}
