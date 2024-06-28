package com.example.madcamp24_week1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ContactDetailFragment extends DialogFragment {

    private static final String ARG_NAME = "name";
    private static final String ARG_PHONE = "phone";

    private String name;
    private String phone;

    public static ContactDetailFragment newInstance(String name, String phone) {
        ContactDetailFragment fragment = new ContactDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_PHONE, phone);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_NAME);
            phone = getArguments().getString(ARG_PHONE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_detail, container, false);
        TextView nameTextView = view.findViewById(R.id.contactName);
        TextView phoneTextView = view.findViewById(R.id.contactPhone);
        Button callButton = view.findViewById(R.id.callButton);
        Button messageButton = view.findViewById(R.id.messageButton);

        nameTextView.setText(name);
        phoneTextView.setText(phone);

        callButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phone));
            startActivity(intent);
        });

        messageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("sms:" + phone));
            startActivity(intent);
        });

        return view;
    }
}
