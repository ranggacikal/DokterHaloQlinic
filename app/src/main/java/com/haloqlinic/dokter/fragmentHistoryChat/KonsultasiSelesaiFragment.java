package com.haloqlinic.dokter.fragmentHistoryChat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.haloqlinic.dokter.R;
import com.haloqlinic.dokter.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.dokter.adapter.HistoryChatAdapter;
import com.haloqlinic.dokter.api.ConfigRetrofit;
import com.haloqlinic.dokter.model.listKonsultasi.DataItem;
import com.haloqlinic.dokter.model.listKonsultasi.ResponseDataKonsultasi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KonsultasiSelesaiFragment extends Fragment {

    public KonsultasiSelesaiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    RecyclerView rvKonsultasiSelesai;
    private SharedPreferencedConfig preferencedConfig;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_konsultasi_selesai, container, false);

        rvKonsultasiSelesai = view.findViewById(R.id.recycler_konsultasi_selesai);
        preferencedConfig = new SharedPreferencedConfig(getActivity());
        
        rvKonsultasiSelesai.setHasFixedSize(true);
        rvKonsultasiSelesai.setLayoutManager(new LinearLayoutManager(getActivity()));
        
        loadKonsultasiSelesai();
        
        return view;
    }

    private void loadKonsultasiSelesai() {

        String id_dokter = preferencedConfig.getPreferenceIdDokter();

        ConfigRetrofit.service.dataKonsultasi(id_dokter, "2").enqueue(new Callback<ResponseDataKonsultasi>() {
            @Override
            public void onResponse(Call<ResponseDataKonsultasi> call, Response<ResponseDataKonsultasi> response) {
                if (response.isSuccessful()) {
                    List<DataItem> dataKonsultasi = response.body().getData();
                    HistoryChatAdapter adapter = new HistoryChatAdapter(getActivity(), dataKonsultasi);
                    rvKonsultasiSelesai.setAdapter(adapter);
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