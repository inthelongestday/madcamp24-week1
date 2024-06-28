package com.example.madcamp24_week1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<ContactDTO> contactDTOList;

    public ContactAdapter(List<ContactDTO> contactDTOList) {
        this.contactDTOList = contactDTOList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        ContactDTO contactDTO = contactDTOList.get(position);
        holder.contactName.setText(contactDTO.getName());
        holder.contactPhone.setText(contactDTO.getPhone());
    }

    @Override
    public int getItemCount() {
        return contactDTOList.size();
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView contactName;
        TextView contactPhone;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            contactName = itemView.findViewById(R.id.contactName);
            contactPhone = itemView.findViewById(R.id.contactPhone);
        }
    }
}
