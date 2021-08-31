package id.luvie.luviedokter.fragmentHistoryChat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import id.luvie.luviedokter.R;
import id.luvie.luviedokter.SharedPreference.SharedPreferencedConfig;
import id.luvie.luviedokter.adapter.HistoryChatAdapter;
import id.luvie.luviedokter.api.ConfigRetrofit;
import id.luvie.luviedokter.model.listKonsultasi.DataItem;
import id.luvie.luviedokter.model.listKonsultasi.ResponseDataKonsultasi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KonsultasiPendingFragment extends Fragment {

    public KonsultasiPendingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    RecyclerView rvKonsultasiPending;

    private SharedPreferencedConfig preferencedConfig;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_konsultasi_pending, container, false);

        rvKonsultasiPending = rootView.findViewById(R.id.recycler_konsultasi_pending);

        preferencedConfig = new SharedPreferencedConfig(getActivity());

        rvKonsultasiPending.setHasFixedSize(true);
        rvKonsultasiPending.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadKonsultasiPending();

        return rootView;
    }

    private void loadKonsultasiPending() {

        String id_dokter = preferencedConfig.getPreferenceIdDokter();
        String status = "0";

        ConfigRetrofit.service.dataKonsultasi(id_dokter, status).enqueue(new Callback<ResponseDataKonsultasi>() {
            @Override
            public void onResponse(Call<ResponseDataKonsultasi> call, Response<ResponseDataKonsultasi> response) {
                if (response.isSuccessful()){

                    List<DataItem> dataKonsultasi = response.body().getData();
                    HistoryChatAdapter adapter = new HistoryChatAdapter(getActivity(), dataKonsultasi);
                    rvKonsultasiPending.setAdapter(adapter);

                }else{
                    Toast.makeText(getActivity(), "Data kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataKonsultasi> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        loadKonsultasiPending();
    }
}