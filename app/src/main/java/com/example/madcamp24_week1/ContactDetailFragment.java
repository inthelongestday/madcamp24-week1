package com.example.madcamp24_week1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;

public class ContactDetailFragment extends DialogFragment {

    private static final String ARG_ID = "id";
    private static final String ARG_NAME = "name";
    private static final String ARG_PHONE = "phone";
    private static final String ARG_POSITION = "position";

    private int id;
    private String name;
    private String phone;
    private int position;

    private OnContactActionListener listener;

    public static ContactDetailFragment newInstance(int id, String name, String phone, int position) {
        ContactDetailFragment fragment = new ContactDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        args.putString(ARG_NAME, name);
        args.putString(ARG_PHONE, phone);
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnContactActionListener) {
            listener = (OnContactActionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnContactActionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_ID);
            name = getArguments().getString(ARG_NAME);
            phone = getArguments().getString(ARG_PHONE);
            position = getArguments().getInt(ARG_POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_detail, container, false);
        TextView nameTextView = view.findViewById(R.id.contactName);
        TextView phoneTextView = view.findViewById(R.id.contactPhone);
        MaterialButton editButton = view.findViewById(R.id.editButton);
        MaterialButton deleteButton = view.findViewById(R.id.deleteButton);
        MaterialButton callButton = view.findViewById(R.id.callButton);
        MaterialButton messageButton = view.findViewById(R.id.messageButton);

        nameTextView.setText(name);
        phoneTextView.setText(phone);

        editButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditContact(id, name, phone, position);
                dismiss();
            }
        });

        deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Delete Contact")
                    .setMessage("정말 삭제하시겠습니까?")
                    .setPositiveButton("No", null)
                    .setNegativeButton("Yes", (dialog, which) -> {
                        if (listener != null) {
                            listener.onDeleteContact(id);
                            dismiss();
                        }
                    })
                    .show();
        });

        callButton.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + phone));
            startActivity(callIntent);
        });

        messageButton.setOnClickListener(v -> {
            Intent messageIntent = new Intent(Intent.ACTION_SENDTO);
            messageIntent.setData(Uri.parse("smsto:" + phone));
            startActivity(messageIntent);
        });

        return view;
    }

    public interface OnContactActionListener {
        void onEditContact(int id, String name, String phone, int position);
        void onDeleteContact(int id);
    }
}
