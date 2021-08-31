package id.luvie.luviedokter.fragmentSaldo;

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
import id.luvie.luviedokter.adapter.PendapatanAdapter;
import id.luvie.luviedokter.api.ConfigRetrofit;
import id.luvie.luviedokter.model.saldo.DataItem;
import id.luvie.luviedokter.model.saldo.ResponseDataSaldo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaldoKonsultasiFragment extends Fragment {

    public SaldoKonsultasiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    RecyclerView rvSaldoKonsultasi;
    private SharedPreferencedConfig preferencedConfig;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_saldo_konsultasi, container, false);

        rvSaldoKonsultasi = rootView.findViewById(R.id.recycler_saldo_konsultasi);
        preferencedConfig = new SharedPreferencedConfig(getActivity());

        rvSaldoKonsultasi.setHasFixedSize(true);
        rvSaldoKonsultasi.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadSaldoKonsultasi();

        return rootView;
    }

    private void loadSaldoKonsultasi() {

        String id_dokter = preferencedConfig.getPreferenceIdDokter();

        ConfigRetrofit.service.dataSaldo(id_dokter, "0").enqueue(new Callback<ResponseDataSaldo>() {
            @Override
            public void onResponse(Call<ResponseDataSaldo> call, Response<ResponseDataSaldo> response) {
                if (response.isSuccessful()){

                    List<DataItem> dataKonsultasi = response.body().getData();
                    PendapatanAdapter adapter = new PendapatanAdapter(getActivity(), dataKonsultasi);
                    rvSaldoKonsultasi.setAdapter(adapter);

                }else{
                    Toast.makeText(getActivity(), "Data kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataSaldo> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}