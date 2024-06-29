package com.example.madcamp24_week1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity implements ContactDetailFragment.OnContactActionListener, ContactEditFragment.OnContactEditListener, TravelRecordEditFragment.OnTravelRecordEditListener {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        viewPager.setAdapter(new ViewPagerAdapter(this));

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Contacts");
                            break;
                        case 1:
                            tab.setText("Gallery");
                            break;
                        case 2:
                            tab.setText("Travel Records");
                            break;
                    }
                }).attach();
    }

    @Override
    public void onEditContact(int id, String name, String phone, int position) {
        ContactEditFragment contactEditFragment = ContactEditFragment.newInstance(id, name, phone, position);
        contactEditFragment.show(getSupportFragmentManager(), "contact_edit");
    }

    @Override
    public void onDeleteContact(int position) {
        ContactFragment contactFragment = (ContactFragment) getSupportFragmentManager().findFragmentByTag("f0");
        if (contactFragment != null) {
            contactFragment.onDeleteContact(position);
        }
    }

    @Override
    public void onContactEdited(int id, String name, String phone, int position) {
        ContactFragment contactFragment = (ContactFragment) getSupportFragmentManager().findFragmentByTag("f0");
        if (contactFragment != null) {
            contactFragment.onContactEdited(id, name, phone, position);
        }
    }

    @Override
    public void onTravelRecordEdited(int id, int imageResId, String memo, String date, int regionId) {
        TravelRecordFragment travelRecordFragment = (TravelRecordFragment) getSupportFragmentManager().findFragmentByTag("f2");
        if (travelRecordFragment != null) {
            travelRecordFragment.onTravelRecordEdited(id, imageResId, memo, date, regionId);
        }
    }
}
