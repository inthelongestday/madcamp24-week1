package com.example.madcamp24_week1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ImageViewHolder> {
    private final Context context;
    private final List<GalleryDTO> imageList;

    public GalleryAdapter(Context context, List<GalleryDTO> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        GalleryDTO currentItem = imageList.get(position);
        holder.imageView.setImageResource(currentItem.getImageResId());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = View.inflate(context, R.layout.dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                ImageView ivPic = dialogView.findViewById(R.id.ivPic);
                ivPic.setImageResource(currentItem.getImageResId());
                dlg.setTitle("View Photo");
                dlg.setIcon(R.drawable.ic_launcher_foreground);
                dlg.setView(dialogView);
                dlg.setNegativeButton("Close", null);
                dlg.show();
            }
        });

        holder.itemView.post(new Runnable() {
            @Override
            public void run() {
                // 뷰의 높이를 너비와 동일하게 설정하여 정사각형으로 만듦
                int width = holder.itemView.getWidth();
                holder.imageView.getLayoutParams().height = width;
                holder.imageView.requestLayout();
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivPic);
        }
    }
}
