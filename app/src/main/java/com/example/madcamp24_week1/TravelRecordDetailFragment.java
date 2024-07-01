package com.example.madcamp24_week1;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TravelRecordDetailFragment extends DialogFragment {

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

    private OnTravelRecordUpdatedListener listener;

    public static TravelRecordDetailFragment newInstance(int id, int imageResId, String memo, String date, int regionId) {
        TravelRecordDetailFragment fragment = new TravelRecordDetailFragment();
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
        Button editButton = view.findViewById(R.id.editButton);
        Button deleteButton = view.findViewById(R.id.deleteButton);

        imageView.setImageResource(imageResId);
        memoTextView.setText(memo);
        dateTextView.setText(date);
        regionTextView.setText(String.valueOf(regionId));

        editButton.setOnClickListener(v -> {
            TravelRecordEditFragment editFragment = TravelRecordEditFragment.newInstance(id, imageResId, memo, date, regionId);
            editFragment.setOnTravelRecordEditListener((editedId, editedImageResId, editedMemo, editedDate, editedRegionId) -> {
                TravelRecordDTO updatedRecord = new TravelRecordDTO(editedId, editedImageResId, editedMemo, editedDate, editedRegionId);
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
}
