package com.example.madcamp24_week1;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class ContactFragment extends Fragment implements ContactAdapter.OnItemClickListener,
        ContactDetailFragment.OnContactActionListener, ContactEditFragment.OnContactEditListener {

    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;
    private List<ContactDTO> contactList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        contactList = ContactData.getContacts();
        contactAdapter = new ContactAdapter(contactList, this);
        recyclerView.setAdapter(contactAdapter);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            ContactEditFragment contactEditFragment = ContactEditFragment.newInstance(0, "", "", -1);
            contactEditFragment.show(getParentFragmentManager(), "contact_add");
        });

        return view;
    }

    @Override
    public void onItemClick(int id) {
        ContactDTO contact = ContactData.getContactById(id);
        if (contact != null) {
            ContactDetailFragment contactDetailFragment = ContactDetailFragment.newInstance(contact.getId(), contact.getName(), contact.getPhone());
            contactDetailFragment.show(getParentFragmentManager(), "contact_detail");
        } else {
            Log.d("ContactFragment", "No contact found with ID: " + id);
        }
    }

    @Override
    public void onEditContact(int id, String name, String phone, int position) {
        ContactEditFragment contactEditFragment = ContactEditFragment.newInstance(id, name, phone, position);
        contactEditFragment.show(getParentFragmentManager(), "contact_edit");
    }

    @Override
    public void onDeleteContact(int id) {
        int position = -1;
        for (int i = 0; i < contactList.size(); i++) {
            if (contactList.get(i).getId() == id) {
                position = i;
                break;
            }
        }

        if (position != -1) {
            ContactData.deleteContact(id);
            contactList.remove(position);
            contactAdapter.notifyItemRemoved(position);
        } else {
            Log.d("ContactFragment", "No contact found with ID: " + id);
        }
    }

    @Override
    public void onContactEdited(int id, String name, String phone, int position) {
        if (position == -1) {
            ContactDTO newContact = new ContactDTO(id, name, phone);
            ContactData.addContact(newContact);
            contactAdapter.notifyItemInserted(contactList.size() - 1);
        } else {
            ContactDTO contact = contactList.get(position);
            contact.setName(name);
            contact.setPhone(phone);
            ContactData.updateContact(contact);
            contactAdapter.notifyItemChanged(position);
        }
    }
}
