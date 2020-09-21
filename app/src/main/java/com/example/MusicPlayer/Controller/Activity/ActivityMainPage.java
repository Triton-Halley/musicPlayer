package com.example.MusicPlayer.Controller.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.MusicPlayer.Controller.Fragment.MainFragment;
import com.example.MusicPlayer.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ActivityMainPage extends AppCompatActivity {
    private ViewPager2 mTaskViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        findViews();
        setUI();
    }

    private void findViews() {
        mTaskViewPager = findViewById(R.id.viewPager2);
    }

    private void setUI() {
        FragmentStateAdapter adapter = new PagerAdapter(this);
        mTaskViewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, mTaskViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0: {
                        tab.setText("Music");
                        break;
                    }
                    case 1: {
                        tab.setText("Albums");
                        break;

                    }
                    default: {
                        tab.setText("Artist");
                        break;
                    }
                }
            }
        }
        );
        tabLayoutMediator.attach();

    }
    private class PagerAdapter extends FragmentStateAdapter {

        public PagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0: {
                    return MainFragment.newInstance("");
                }
                case 1: {
                    return MainFragment.newInstance(" ");
                }
                default: {
                    return MainFragment.newInstance("  ");
                }
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}


