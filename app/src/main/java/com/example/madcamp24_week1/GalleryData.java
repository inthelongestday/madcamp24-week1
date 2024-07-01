package com.example.madcamp24_week1;

import java.util.ArrayList;
import java.util.List;

public class GalleryData {
    private static List<GalleryDTO> imageList = new ArrayList<>();

    static {
        //mock
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
    }

    public static List<GalleryDTO> getImageList() {
        return imageList;
    }

    public static void addImage(GalleryDTO image) {
        imageList.add(image);
    }
}
