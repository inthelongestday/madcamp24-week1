package com.example.madcamp24_week1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RegionAdapter extends RecyclerView.Adapter<RegionAdapter.RegionViewHolder> {

    private List<RegionDTO> regionList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(RegionDTO region);
    }

    public RegionAdapter(List<RegionDTO> regionList, OnItemClickListener listener) {
        this.regionList = regionList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RegionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_region, parent, false);
        return new RegionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegionViewHolder holder, int position) {
        RegionDTO region = regionList.get(position);
        holder.regionNameTextView.setText(region.getRegionName());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(region));
    }

    @Override
    public int getItemCount() {
        return regionList.size();
    }

    public static class RegionViewHolder extends RecyclerView.ViewHolder {
        TextView regionNameTextView;

        public RegionViewHolder(@NonNull View itemView) {
            super(itemView);
            regionNameTextView = itemView.findViewById(R.id.regionNameTextView);
        }
    }
}
