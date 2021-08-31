package id.luvie.luviedokter.fragmentMain;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import id.luvie.luviedokter.JadwalKonsultasiActivity;
import id.luvie.luviedokter.KonsultasiActivity;
import id.luvie.luviedokter.R;
import id.luvie.luviedokter.SharedPreference.SharedPreferencedConfig;
import id.luvie.luviedokter.adapter.KonsultasiBerlangsungAdapter;
import id.luvie.luviedokter.adapter.RequestAdapter;
import id.luvie.luviedokter.api.ConfigRetrofit;
import id.luvie.luviedokter.model.getPlayerId.ResponseGetPlayerId;
import id.luvie.luviedokter.model.konsultasiBerlangsung.ResponseKonsultasiBerlangsung;
import id.luvie.luviedokter.model.popupRequest.DataItem;
import id.luvie.luviedokter.model.popupRequest.ResponseRequestKonsultasi;
import id.luvie.luviedokter.model.status.ResponseItem;
import id.luvie.luviedokter.model.status.ResponseStatus;
import id.luvie.luviedokter.model.statusDokter.ResponseStatusDokter;
import com.onesignal.OSDeviceState;
import com.onesignal.OSPermissionStateChanges;
import com.onesignal.OneSignal;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;


public class HomeFragment extends Fragment {

    private SharedPreferencedConfig preferencedConfig;
    LinearLayout linearKonsultasi, linearJadwalKonsultasi;
    String status;
    private static final String ONESIGNAL_APP_ID = "e0a7b99b-6b25-4557-8e4a-b276c9ab8d3e";
    @SuppressLint("UseSwitchCompatOrMaterialCode") Switch switchButton;
    String token, user_id;
    TextView txtKonsultasiBerlangsung;
    RecyclerView rvKonsultasiBerlangsung;

    public Dialog dialog;
    int total_request;

    private Handler handler = new Handler();

    // Define the code block to be executed
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            loadRequest();

            // Repeat every 2 seconds
            handler.postDelayed(runnable, 2000);
        }
    };

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
        switchButton = rootView.findViewById(R.id.simpleSwitch);
        txtKonsultasiBerlangsung = rootView.findViewById(R.id.text_konsultasi_berlangsung);
        rvKonsultasiBerlangsung = rootView.findViewById(R.id.rv_konsultasi_berlangsung);

        rvKonsultasiBerlangsung.setHasFixedSize(true);
        rvKonsultasiBerlangsung.setLayoutManager(new LinearLayoutManager(getActivity()));

        OneSignal.initWithContext(getActivity());
        OneSignal.setAppId(ONESIGNAL_APP_ID);
        OSDeviceState device = OneSignal.getDeviceState();

        dialog = new Dialog(getActivity());

        //Memasang Title / Judul pada Custom Dialog
        dialog.setTitle("Request Konsultasi");

        //Memasang Desain Layout untuk Custom Dialog
        dialog.setContentView(R.layout.dialog_request);



        token = device.getPushToken();
        user_id = device.getUserId();

        loadStatus();
        loadRequest();
        getPlayerId();

        if (total_request == 0){
            handler.post(runnable);
        }else{
            handler.removeCallbacks(runnable);
        }

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

        loadKonsultasiBerlangsung();

        return rootView;
    }

    private void loadKonsultasiBerlangsung() {

        ConfigRetrofit.service.konsultasiBerlangsung(preferencedConfig.getPreferenceIdDokter())
                .enqueue(new Callback<ResponseKonsultasiBerlangsung>() {
                    @Override
                    public void onResponse(Call<ResponseKonsultasiBerlangsung> call, Response<ResponseKonsultasiBerlangsung> response) {
                        if (response.isSuccessful()){

                            List<id.luvie.luviedokter.model.konsultasiBerlangsung.DataItem> dataKonsultasi =
                                    response.body().getData();
                            KonsultasiBerlangsungAdapter adapter = new KonsultasiBerlangsungAdapter(getActivity(), dataKonsultasi);
                            if (dataKonsultasi.size()<1){
                                txtKonsultasiBerlangsung.setVisibility(View.GONE);
                            }else{
                                txtKonsultasiBerlangsung.setVisibility(View.VISIBLE);
                                rvKonsultasiBerlangsung.setAdapter(adapter);
                            }

                        }else{
                            Toast.makeText(getActivity(), "Gagal Memuat Data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseKonsultasiBerlangsung> call, Throwable t) {
                        Toast.makeText(getActivity(), "Koneksi Error", Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void getPlayerId() {

        ConfigRetrofit.service.getPlayerId(preferencedConfig.getPreferenceIdDokter(), user_id)
                .enqueue(new Callback<ResponseGetPlayerId>() {
                    @Override
                    public void onResponse(Call<ResponseGetPlayerId> call, Response<ResponseGetPlayerId> response) {
                        if (response.isSuccessful()){

                            Log.d("getPlayerIdHome", "onResponse: "+response.body().getIdDokter());
                            Log.d("getPlayerIdHome", "onResponse: "+response.body().getPlayerId());

                        }else{
                            Log.d("c", "onResponseElse: Gagal Load Data");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseGetPlayerId> call, Throwable t) {
                        Log.d("getPlayerIdHome", "onFailure: "+t.getMessage());
                    }
                });

    }

    private void loadRequest() {

        ConfigRetrofit.service.requestKonsultasi(preferencedConfig.getPreferenceIdDokter())
                .enqueue(new Callback<ResponseRequestKonsultasi>() {
                    @Override
                    public void onResponse(Call<ResponseRequestKonsultasi> call, Response<ResponseRequestKonsultasi> response) {
                        if (response.isSuccessful()){

                            total_request = response.body().getTotalRequest();

                            //Memasang Listener / Aksi saat tombol OK di Klik
                            RecyclerView rvRequest = dialog.findViewById(R.id.rv_dialog_request);

                            if (total_request!=0){

                                Log.d("getRequest", "onResponse: success");

                                rvRequest.setHasFixedSize(true);
                                rvRequest.setLayoutManager(new LinearLayoutManager(getActivity()));

                                List<DataItem> dataRequest = response.body().getData();
                                RequestAdapter adapter = new RequestAdapter(getActivity(), dataRequest);
                                rvRequest.setAdapter(adapter);
                                dialog.show();

                            }else{
                                Log.d("getRequest", "onResponse: Data Kosong");
                                dialog.dismiss();
                            }

                        }else{
                            Log.d("getRequest", "onResponse: Response Failed");
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseRequestKonsultasi> call, Throwable t) {
                        Log.d("getRequest", "failure: "+t.getMessage());
                    }
                });

    }

    @Override
    public void onResume() {
        super.onResume();
        handler.post(runnable);
        loadKonsultasiBerlangsung();
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    private void loadStatus() {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Memuat Status Dokter");
        progressDialog.show();

        ConfigRetrofit.service.status(preferencedConfig.getPreferenceIdDokter(), user_id).enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();
                    List<ResponseItem> dataStatus = response.body().getResponse();

                    for (int i = 0; i<dataStatus.size(); i++){

                        status = dataStatus.get(i).getStatus();

                    }

                    Log.d("CheckStatusDokter", "onResponse: "+status);
                    Log.d("CheckStatusDokter", "Player ID: "+user_id);
                    if (status.equals("1")){

                        switchButton.setChecked(true);
                        switchButton.setText("ONLINE");

                    }else if (status.equals("2")){
                        switchButton.setChecked(false);
                        switchButton.setText("OFFLINE");
                    }else{
                        switchButton.setClickable(false);
                    }
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Gagal Memuat Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseStatus> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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
                Toast.makeText(getContext(), "Error"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }



}