package com.haloqlinic.dokter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.haloqlinic.dokter.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.dokter.databinding.ActivityMainBinding;
import com.haloqlinic.dokter.fragmentMain.HistoryChatFragment;
import com.haloqlinic.dokter.fragmentMain.HistoryPembayaranFragment;
import com.haloqlinic.dokter.fragmentMain.HomeFragment;
import com.haloqlinic.dokter.fragmentMain.ProfileFragment;
import com.haloqlinic.dokter.fragmentMain.ResepObatFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SharedPreferencedConfig preferencedConfig;

    private HomeFragment homeFragment;
    private ResepObatFragment resepObatFragment;
    private HistoryChatFragment historyChatFragment;
    private HistoryPembayaranFragment historyPembayaranFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        preferencedConfig = new SharedPreferencedConfig(this);

        bindWidgetsWithAnEvent();
        setupTabLayout();

    }

    private void setupTabLayout() {

        homeFragment = new HomeFragment();
        resepObatFragment = new ResepObatFragment();
        historyChatFragment = new HistoryChatFragment();
        profileFragment = new ProfileFragment();

        binding.tabLayoutHome.addTab(binding.tabLayoutHome.newTab().setText("Home").setIcon(R.drawable.ic_home_dokter));
        binding.tabLayoutHome.addTab(binding.tabLayoutHome.newTab().setText("Pendapatan").setIcon(R.drawable.icon_pendapatan));
        binding.tabLayoutHome.addTab(binding.tabLayoutHome.newTab().setText("History chat").setIcon(R.drawable.ic_history_chat_dokter));
        binding.tabLayoutHome.addTab(binding.tabLayoutHome.newTab().setText("Profile").setIcon(R.drawable.ic_profile_dokter));

    }

    private void bindWidgetsWithAnEvent() {


        binding.tabLayoutHome.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentTabFragment(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


    }

    private void setCurrentTabFragment(int position) {

        switch (position)
        {
            case 0 :
                replaceFragment(homeFragment);
                break;
            case 1 :
                replaceFragment(resepObatFragment);
                break;
            case 2 :
                replaceFragment(historyChatFragment);
                break;
            case 3 :
                replaceFragment(profileFragment);
                break;
        }

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_home, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }
}