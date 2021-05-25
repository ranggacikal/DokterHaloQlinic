package com.haloqlinic.dokter.fragmentMain;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.haloqlinic.dokter.R;
import com.haloqlinic.dokter.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.dokter.adapter.JadwalKonsultasiAdapter;
import com.haloqlinic.dokter.api.ConfigRetrofit;
import com.haloqlinic.dokter.model.listKonsultasi.DataItem;
import com.haloqlinic.dokter.model.listKonsultasi.ResponseDataKonsultasi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private SharedPreferencedConfig preferencedConfig;
    RecyclerView rvJadwalKonsultasi;

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
        rvJadwalKonsultasi = rootView.findViewById(R.id.recycler_jadwal_konsultasi);

        txtNamaDokter.setText("Dr. "+preferencedConfig.getPreferenceNama());
        rvJadwalKonsultasi.setHasFixedSize(true);
        rvJadwalKonsultasi.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadJadwalKonsultasi();


        return rootView;
    }

    private void loadJadwalKonsultasi() {

        String id_dokter = preferencedConfig.getPreferenceIdDokter();
        String status = "1";

        ConfigRetrofit.service.dataKonsultasi(id_dokter, status).enqueue(new Callback<ResponseDataKonsultasi>() {
            @Override
            public void onResponse(Call<ResponseDataKonsultasi> call, Response<ResponseDataKonsultasi> response) {
                if (response.isSuccessful()){

                    List<DataItem> dataKonsultasi = response.body().getData();
                    JadwalKonsultasiAdapter adapter = new JadwalKonsultasiAdapter(getActivity(), dataKonsultasi);
                    rvJadwalKonsultasi.setAdapter(adapter);

                }else{
                    Toast.makeText(getActivity(), "Data Kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataKonsultasi> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}