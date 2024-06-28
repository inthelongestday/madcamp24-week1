package com.example.madcamp24_week1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactDetailAdapter extends RecyclerView.Adapter<ContactDetailAdapter.DetailViewHolder> {

    private List<ContactDetailDTO> detailList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public ContactDetailAdapter(List<ContactDetailDTO> detailList, OnItemClickListener listener) {
        this.detailList = detailList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item_detail, parent, false);
        return new DetailViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        ContactDetailDTO detailItem = detailList.get(position);
        holder.detailEditText.setText(detailItem.getDetail());
    }

    @Override
    public int getItemCount() {
        return detailList.size();
    }

    public static class DetailViewHolder extends RecyclerView.ViewHolder {
        EditText detailEditText;
        ImageButton deleteButton;

        public DetailViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            detailEditText = itemView.findViewById(R.id.detailEditText);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            deleteButton.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(position);
                    }
                }
            });
        }
    }

    public void addDetail(ContactDetailDTO detailItem) {
        detailList.add(detailItem);
        notifyItemInserted(detailList.size() - 1);
    }

    public void removeDetail(int position) {
        detailList.remove(position);
        notifyItemRemoved(position);
    }

    public void updateDetail(int position, String detail) {
        detailList.get(position).setDetail(detail);
        notifyItemChanged(position);
    }
}
