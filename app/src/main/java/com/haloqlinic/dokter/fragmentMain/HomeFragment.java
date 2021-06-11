package com.haloqlinic.dokter.fragmentMain;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.haloqlinic.dokter.JadwalKonsultasiActivity;
import com.haloqlinic.dokter.KonsultasiActivity;
import com.haloqlinic.dokter.R;
import com.haloqlinic.dokter.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.dokter.adapter.JadwalKonsultasiAdapter;
import com.haloqlinic.dokter.api.ConfigRetrofit;
import com.haloqlinic.dokter.model.listKonsultasi.DataItem;
import com.haloqlinic.dokter.model.listKonsultasi.ResponseDataKonsultasi;
import com.haloqlinic.dokter.model.statusDokter.ResponseStatusDokter;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;


public class HomeFragment extends Fragment {

    private SharedPreferencedConfig preferencedConfig;
    LinearLayout linearKonsultasi, linearJadwalKonsultasi;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        preferencedConfig = new SharedPreferencedConfig(getActivity());
        TextView txtNamaDokter = rootView.findViewById(R.id.txt_nama_dokter_home);
        linearKonsultasi = rootView.findViewById(R.id.linear_konsultasi_home);
        linearJadwalKonsultasi = rootView.findViewById(R.id.linear_jadwal_konsultasi_home);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchButton = rootView.findViewById(R.id.simpleSwitch);

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    switchButton.setText("ONLINE");
                    online();


                }else{
                    switchButton.setText("OFFLINE");
                    offline();
                }
            }
        });

        txtNamaDokter.setText("Dr. "+preferencedConfig.getPreferenceNama());

        PushDownAnim.setPushDownAnimTo(linearKonsultasi)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), KonsultasiActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(linearJadwalKonsultasi)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), JadwalKonsultasiActivity.class));
                    }
                });

        return rootView;
    }

    private void offline() {

        String id_dokter = preferencedConfig.getPreferenceIdDokter();

        ConfigRetrofit.service.statusDokter(id_dokter, "2").enqueue(new Callback<ResponseStatusDokter>() {
            @Override
            public void onResponse(Call<ResponseStatusDokter> call, Response<ResponseStatusDokter> response) {
                if (response.isSuccessful()){

                    Toast.makeText(getActivity(), "Anda Saat ini OFFLINE", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getActivity(), "Status Offline GAGAL", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseStatusDokter> call, Throwable t) {
                Toast.makeText(getActivity(), "Error"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void online() {

        String id_dokter = preferencedConfig.getPreferenceIdDokter();

        ConfigRetrofit.service.statusDokter(id_dokter, "1").enqueue(new Callback<ResponseStatusDokter>() {
            @Override
            public void onResponse(Call<ResponseStatusDokter> call, Response<ResponseStatusDokter> response) {
                if (response.isSuccessful()){

                    Toast.makeText(getActivity(), "Anda Saat ini ONLINE", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getActivity(), "Status Online GAGAL", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseStatusDokter> call, Throwable t) {
                Toast.makeText(getActivity(), "Error"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}