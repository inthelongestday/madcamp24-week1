package com.example.madcamp24_week1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

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
        String imageUri = currentItem.getImageUri();
        boolean isUri = imageUri != null && !imageUri.isEmpty();
        if (isUri) {
            Glide.with(context)
                    .load(imageUri)
                    .thumbnail(0.1f)
                    .into(holder.imageView);
        }
        else {
            Glide.with(context)
                    .load(currentItem.getImageResId())
                    .thumbnail(0.1f)
                    .into(holder.imageView);
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = View.inflate(context, R.layout.dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                ImageView ivPic = dialogView.findViewById(R.id.ivPic);
                ivPic.setScaleType(ImageView.ScaleType.FIT_CENTER);

                TextView tvMemo = dialogView.findViewById(R.id.tvMemo);
                EditText etMemo = dialogView.findViewById(R.id.etMemo);
                Button btnSave = dialogView.findViewById(R.id.btnSave);

                if (isUri) {
                    Glide.with(context)
                            .load(imageUri)
                            .into(ivPic);
                }
                else {
                    Glide.with(context)
                            .load(currentItem.getImageResId())
                            .into(ivPic);
                }

                String memo = currentItem.getMemo();
                if (memo.isEmpty()) {
                    tvMemo.setVisibility(View.VISIBLE);
                    etMemo.setVisibility(View.GONE);
                    btnSave.setVisibility(View.GONE);
                    tvMemo.setText("Write your memo here...");
                } else {
                    tvMemo.setText(memo);
                }

                tvMemo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tvMemo.setVisibility(View.GONE);
                        etMemo.setVisibility(View.VISIBLE);
                        btnSave.setVisibility(View.VISIBLE);
                        etMemo.setText(tvMemo.getText().toString());

                        // Show the keyboard
                        etMemo.requestFocus();
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.showSoftInput(etMemo, InputMethodManager.SHOW_IMPLICIT);
                        }
                    }
                });

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newMemo = etMemo.getText().toString();
                        currentItem.setMemo(newMemo);
                        tvMemo.setText(newMemo);
                        tvMemo.setVisibility(View.VISIBLE);
                        etMemo.setVisibility(View.GONE);
                        btnSave.setVisibility(View.GONE);

                        // Hide the keyboard
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(etMemo.getWindowToken(), 0);
                        }
                    }
                });

                dlg.setTitle("View Photo");
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
