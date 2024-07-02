package com.example.madcamp24_week1;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TravelRecordAdapter extends RecyclerView.Adapter<TravelRecordAdapter.TravelRecordViewHolder> {

    private Context context;
    private List<TravelRecordDTO> travelRecordList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(TravelRecordDTO travelRecord, int position);
    }

    public TravelRecordAdapter(Context context, List<TravelRecordDTO> travelRecordList, OnItemClickListener listener) {
        this.context = context;
        this.travelRecordList = travelRecordList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TravelRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.travel_record_item, parent, false);
        return new TravelRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TravelRecordViewHolder holder, int position) {
        TravelRecordDTO travelRecord = travelRecordList.get(position);
        holder.memoTextView.setText(travelRecord.getMemo());
        holder.dateTextView.setText(travelRecord.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        if (travelRecord.getImageUri() != null) {
            holder.imageView.setImageURI(Uri.parse(travelRecord.getImageUri()));
        } else {
            holder.imageView.setImageResource(travelRecord.getImageResId());
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(travelRecord, position));
    }

    @Override
    public int getItemCount() {
        return travelRecordList.size();
    }

    public void updateData(List<TravelRecordDTO> newTravelRecordList) {
        this.travelRecordList = newTravelRecordList;
        notifyDataSetChanged();
    }

    public static class TravelRecordViewHolder extends RecyclerView.ViewHolder {
        TextView memoTextView;
        TextView dateTextView;
        ImageView imageView;

        public TravelRecordViewHolder(@NonNull View itemView) {
            super(itemView);
            memoTextView = itemView.findViewById(R.id.memoTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
