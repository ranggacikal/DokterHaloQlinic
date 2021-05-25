package com.haloqlinic.dokter.fragmentMain;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.haloqlinic.dokter.R;
import com.haloqlinic.dokter.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.dokter.fragmentHistoryChat.KonsultasiAccFragment;
import com.haloqlinic.dokter.fragmentHistoryChat.KonsultasiPendingFragment;
import com.haloqlinic.dokter.fragmentHistoryChat.KonsultasiSelesaiFragment;

public class HistoryChatFragment extends Fragment {

    public HistoryChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    TabLayout tabLayout;
    private KonsultasiPendingFragment konsultasiPendingFragment;
    private KonsultasiAccFragment konsultasiAccFragment;
    private KonsultasiSelesaiFragment konsultasiSelesaiFragment;
    private SharedPreferencedConfig preferencedConfig;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_history_chat, container, false);

        tabLayout = rootView.findViewById(R.id.tab_layout_history_chat);
        preferencedConfig = new SharedPreferencedConfig(getActivity());

        bindWidgetsWithAnEvent();
        setupTabLayout();

        return rootView;
    }

    private void setupTabLayout() {

        konsultasiPendingFragment = new KonsultasiPendingFragment();
        konsultasiAccFragment = new KonsultasiAccFragment();
        konsultasiSelesaiFragment = new KonsultasiSelesaiFragment();

        tabLayout.addTab(tabLayout.newTab().setText("Pending"));
        tabLayout.addTab(tabLayout.newTab().setText("Diterima"));
        tabLayout.addTab(tabLayout.newTab().setText("Selesai"));

    }

    private void bindWidgetsWithAnEvent() {

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentTabFragment(tab.getPosition());
                int position = tab.getPosition();
                preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_POSITION_FRAGMENT, String.valueOf(position));
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

        switch (position) {
            case 0:
                replaceFragment(konsultasiPendingFragment);
                break;
            case 1:
                replaceFragment(konsultasiAccFragment);
                break;
            case 2:
                replaceFragment(konsultasiSelesaiFragment);
                break;

        }

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_history_chat, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }


}