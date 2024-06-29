package com.example.madcamp24_week1;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.material.button.MaterialButton;

public class ContactEditFragment extends DialogFragment {

    private static final String ARG_NAME = "name";
    private static final String ARG_PHONE = "phone";
    private static final String ARG_POSITION = "position";

    private String name;
    private String phone;
    private int position;

    private OnContactEditListener listener;

    public static ContactEditFragment newInstance(String name, String phone, int position) {
        ContactEditFragment fragment = new ContactEditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_PHONE, phone);
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnContactEditListener) {
            listener = (OnContactEditListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnContactEditListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_NAME);
            phone = getArguments().getString(ARG_PHONE);
            position = getArguments().getInt(ARG_POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_edit, container, false);
        EditText nameEditText = view.findViewById(R.id.nameEditText);
        EditText phoneEditText = view.findViewById(R.id.phoneEditText);
        MaterialButton saveButton = view.findViewById(R.id.saveButton);

        nameEditText.setText(name);
        phoneEditText.setText(phone);

        saveButton.setOnClickListener(v -> {
            String newName = nameEditText.getText().toString();
            String newPhone = phoneEditText.getText().toString();
            if (listener != null) {
                listener.onContactEdited(newName, newPhone, position);
                dismiss();
            }
        });

        return view;
    }

    public interface OnContactEditListener {
        void onContactEdited(String name, String phone, int position);
    }
}
