package com.example.madcamp24_week1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private RecyclerView recyclerView;
    private GalleryAdapter galleryAdapter;
    private List<GalleryDTO> imageList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        // Mock 이미지 데이터를 제공
        List<GalleryDTO> imageList = new ArrayList<>();
        imageList.add(new GalleryDTO(R.drawable.pic1));
        imageList.add(new GalleryDTO(R.drawable.pic2));
        imageList.add(new GalleryDTO(R.drawable.pic3));
        imageList.add(new GalleryDTO(R.drawable.pic4));
        imageList.add(new GalleryDTO(R.drawable.pic5));
        imageList.add(new GalleryDTO(R.drawable.pic6));
        imageList.add(new GalleryDTO(R.drawable.pic7));
        imageList.add(new GalleryDTO(R.drawable.pic8));
        imageList.add(new GalleryDTO(R.drawable.pic9));
        imageList.add(new GalleryDTO(R.drawable.pic10));
        imageList.add(new GalleryDTO(R.drawable.pic11));
        imageList.add(new GalleryDTO(R.drawable.pic12));
        imageList.add(new GalleryDTO(R.drawable.pic13));
        imageList.add(new GalleryDTO(R.drawable.pic14));
        imageList.add(new GalleryDTO(R.drawable.pic15));
        imageList.add(new GalleryDTO(R.drawable.pic16));
        imageList.add(new GalleryDTO(R.drawable.pic17));
        imageList.add(new GalleryDTO(R.drawable.pic18));
        imageList.add(new GalleryDTO(R.drawable.pic19));
        imageList.add(new GalleryDTO(R.drawable.pic20));
        imageList.add(new GalleryDTO(R.drawable.pic21));

        galleryAdapter = new GalleryAdapter(getContext(), imageList);
        recyclerView.setAdapter(galleryAdapter);
        return view;
    }
}
