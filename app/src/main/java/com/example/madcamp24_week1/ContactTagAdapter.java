package com.example.madcamp24_week1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ContactTagAdapter extends RecyclerView.Adapter<ContactTagAdapter.ContactTagViewHolder> {

    private List<ContactDTO> contactList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onContactClick(ContactDTO contact);
    }

    public ContactTagAdapter(List<ContactDTO> contactList, OnItemClickListener listener) {
        this.contactList = contactList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactTagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tagged_contact_item, parent, false);
        return new ContactTagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactTagViewHolder holder, int position) {
        ContactDTO contact = contactList.get(position);
        holder.taggedContactName.setText(contact.getName() + " " + contact.getPhone());
        holder.itemView.setOnClickListener(v -> listener.onContactClick(contact));
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    static class ContactTagViewHolder extends RecyclerView.ViewHolder {
        TextView taggedContactName;

        public ContactTagViewHolder(@NonNull View itemView) {
            super(itemView);
            taggedContactName = itemView.findViewById(R.id.taggedContactName);
        }
    }
}
