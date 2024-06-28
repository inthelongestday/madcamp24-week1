package com.example.madcamp24_week1;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        GridView gridView = view.findViewById(R.id.gridView);

        // Mock 이미지 데이터를 제공
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.pic1);
        imageList.add(R.drawable.pic2);
        imageList.add(R.drawable.pic3);
        imageList.add(R.drawable.pic4);
        imageList.add(R.drawable.pic5);
        imageList.add(R.drawable.pic6);
        imageList.add(R.drawable.pic7);
        imageList.add(R.drawable.pic8);
        imageList.add(R.drawable.pic9);
        imageList.add(R.drawable.pic10);


        gridView.setAdapter(new ImageAdapter(getContext(), imageList));
        return view;
    }
}
