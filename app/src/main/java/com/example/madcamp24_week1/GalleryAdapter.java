package com.example.madcamp24_week1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.List;

public class GalleryAdapter extends BaseAdapter {
    private Context context;
    private List<GalleryDTO> imageList;

    public GalleryAdapter(Context context, List<GalleryDTO> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(1, 1, 1, 1);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(imageList.get(position).getImageResId());

        // Image click to show in dialog
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = View.inflate(context, R.layout.dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                ImageView ivPic = dialogView.findViewById(R.id.ivPic);
                TextView textView = dialogView.findViewById(R.id.textView);
                textView.setText("Write your memo here..");
                ivPic.setImageResource(imageList.get(position).getImageResId());
                dlg.setTitle("View Photo");
                dlg.setIcon(R.drawable.ic_launcher_foreground);
                dlg.setView(dialogView);
                dlg.setNegativeButton("Close", null);
                dlg.show();
            }
        });

        return imageView;
    }
}
