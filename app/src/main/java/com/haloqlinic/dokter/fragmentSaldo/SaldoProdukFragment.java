package com.haloqlinic.dokter.fragmentSaldo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.haloqlinic.dokter.R;
import com.haloqlinic.dokter.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.dokter.adapter.PendapatanAdapter;
import com.haloqlinic.dokter.api.ConfigRetrofit;
import com.haloqlinic.dokter.model.saldo.DataItem;
import com.haloqlinic.dokter.model.saldo.ResponseDataSaldo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaldoProdukFragment extends Fragment {

    public SaldoProdukFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    RecyclerView rvSaldoProduk;
    private SharedPreferencedConfig preferencedConfig;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_saldo_produk, container, false);

        rvSaldoProduk = rootView.findViewById(R.id.recycler_saldo_produk);
        preferencedConfig = new SharedPreferencedConfig(getActivity());

        loadSaldoProduk();

        return rootView;
    }

    private void loadSaldoProduk() {

        String id_dokter = preferencedConfig.getPreferenceIdDokter();

        ConfigRetrofit.service.dataSaldo(id_dokter, "1").enqueue(new Callback<ResponseDataSaldo>() {
            @Override
            public void onResponse(Call<ResponseDataSaldo> call, Response<ResponseDataSaldo> response) {
                if (response.isSuccessful()){

                    List<DataItem> dataSaldo = response.body().getData();
                    PendapatanAdapter adapter = new PendapatanAdapter(getActivity(), dataSaldo);
                    rvSaldoProduk.setAdapter(adapter);

                }else{
                    Toast.makeText(getActivity(), "Data kosomg", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataSaldo> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}