package com.example.madcamp24_week1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity implements ContactDetailFragment.OnContactActionListener, ContactEditFragment.OnContactEditListener, TravelRecordEditFragment.OnTravelRecordEditListener, RegionFragment.OnRegionSelectedListener {

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
                            tab.setText("Regions");
                            break;
                    }
                }).attach();

        // 초기 화면으로 RegionFragment 설정
        if (savedInstanceState == null) {
            RegionFragment regionFragment = new RegionFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, regionFragment)
                    .commit();
        }

        // 탭 전환 리스너 추가
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() != 2) {
                    findViewById(R.id.fragment_container).setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Do nothing
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Do nothing
            }
        });
    }

    @Override
    public void onEditContact(int id, String name, String phone, int position) {
        ContactEditFragment contactEditFragment = ContactEditFragment.newInstance(id, name, phone, position);
        contactEditFragment.show(getSupportFragmentManager(), "contact_edit");
    }

    @Override
    public void onDeleteContact(int id) {
        ContactFragment contactFragment = (ContactFragment) getSupportFragmentManager().findFragmentByTag("f0");
        if (contactFragment != null) {
            contactFragment.onDeleteContact(id);
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
    public void onTravelRecordEdited(int id, int imageResId, String memo, LocalDate date, int regionId, String imageUri) {
        TravelRecordFragment travelRecordFragment = (TravelRecordFragment) getSupportFragmentManager().findFragmentByTag("travel_record_fragment");
        if (travelRecordFragment != null) {
            travelRecordFragment.onTravelRecordEdited(id, imageResId, memo, date, regionId, imageUri);
        }
    }

    @Override
    public void onRegionSelected(int regionId) {
        Log.d("MainActivity", "Region selected: " + regionId);
        TravelRecordFragment travelRecordFragment = TravelRecordFragment.newInstance(regionId);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, travelRecordFragment, "travel_record_fragment");
        transaction.addToBackStack(null);
        transaction.commit();
        findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
    }
}
