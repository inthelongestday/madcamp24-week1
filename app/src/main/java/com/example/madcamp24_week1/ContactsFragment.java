package com.example.madcamp24_week1;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class ContactsFragment extends Fragment implements ContactAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<ContactDTO> contactList = ContactData.getContacts();
        contactAdapter = new ContactAdapter(contactList, this);
        recyclerView.setAdapter(contactAdapter);
        return view;
    }

    @Override
    public void onItemClick(ContactDTO contact) {
        ContactDetailFragment dialogFragment = ContactDetailFragment.newInstance(contact.getName(), contact.getPhone());
        dialogFragment.show(getParentFragmentManager(), "contact_detail");
    }
}
