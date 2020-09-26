package com.example.MusicPlayer.Controller.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;

import com.example.MusicPlayer.Controller.Fragment.ListOfArtistsFragment;
import com.example.MusicPlayer.Controller.Fragment.ListOfMusicFragment;
import com.example.MusicPlayer.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class ActivityMainPage extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private ViewPager2 mTaskViewPager;
/*    public static final int REQUEST_CODE_EASY_PERMISSION = 123 ;
    public static int REQUEST_CODE_ = 0 ;*/
/*    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }*/

    @RequiresApi(api = Build.VERSION_CODES.R)
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

    // in annotation automatically call setUI and we don't need to call it again
    //Idk why but this Annotation not Working i call this method inside onPermissionGranted method
//    @AfterPermissionGranted(123)
    private void setUI() {
        String[] permissions ={Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this,permissions)){
            FragmentStateAdapter adapter = new PagerAdapter(this);
            mTaskViewPager.setAdapter(adapter);
            defineTabs();
        }else {
            EasyPermissions.requestPermissions(this,
                    "PermissionDenied need access to Storage",132,permissions);
        }
    }

    private void defineTabs() {
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

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        setUI();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
/*        if (EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            new AppSettingsDialog.Builder(this).build().show();
        }*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
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
                    return ListOfMusicFragment.newInstance();
                }
                case 1: {
                    return ListOfArtistsFragment.newInstance("Album");
                }
                default: {
                    return ListOfArtistsFragment.newInstance("Artist");
                }
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

}


