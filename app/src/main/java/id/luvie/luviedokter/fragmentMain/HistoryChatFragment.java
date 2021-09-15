package id.luvie.luviedokter.fragmentMain;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import id.luvie.luviedokter.R;
import id.luvie.luviedokter.SharedPreference.SharedPreferencedConfig;
import id.luvie.luviedokter.adapter.HistoryChatAdapter;
import id.luvie.luviedokter.adapter.HistoryKonsultasiAdapter;
import id.luvie.luviedokter.api.ConfigRetrofit;
import id.luvie.luviedokter.model.HistoryKonsultasi;
import id.luvie.luviedokter.model.listKonsultasi.DataItem;
import id.luvie.luviedokter.model.listKonsultasi.ResponseDataKonsultasi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryChatFragment extends Fragment {

    public HistoryChatFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerHistoryChat;
    private SharedPreferencedConfig preferencedConfig;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_history_chat, container, false);

        recyclerHistoryChat = rootView.findViewById(R.id.recycler_history_chat);
        preferencedConfig = new SharedPreferencedConfig(getActivity());

        recyclerHistoryChat.setHasFixedSize(true);
        recyclerHistoryChat.setLayoutManager(new LinearLayoutManager(getActivity()));

       // loadKonsultasiSelesai();

        return rootView;
    }

    private void loadKonsultasiSelesai() {

        String id_dokter = preferencedConfig.getPreferenceIdDokter();

        ConfigRetrofit.service.dataKonsultasi(id_dokter, "2").enqueue(new Callback<ResponseDataKonsultasi>() {
            @Override
            public void onResponse(Call<ResponseDataKonsultasi> call, Response<ResponseDataKonsultasi> response) {
                if (response.isSuccessful()) {
                    List<DataItem> dataKonsultasi = response.body().getData();
                    HistoryChatAdapter adapter = new HistoryChatAdapter(getActivity(), dataKonsultasi);
                    recyclerHistoryChat.setAdapter(adapter);
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

    private void historyKonsultasi(){
        String host = "https://luvie.co.id/android/dokter/history_konsultasi.php";
        AndroidNetworking.post(host).addBodyParameter("id_dokter",preferencedConfig.getPreferenceIdDokter())
                .setPriority(Priority.HIGH).build()
                .getAsObject(HistoryKonsultasi.class, new ParsedRequestListener<HistoryKonsultasi>() {
                    @Override
                    public void onResponse(HistoryKonsultasi response) {
                        HistoryKonsultasiAdapter adapter  = new HistoryKonsultasiAdapter(getActivity(),response.getItems());
                        recyclerHistoryChat.setAdapter(adapter);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getActivity(), "Error: "+anError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        historyKonsultasi();
    }

}