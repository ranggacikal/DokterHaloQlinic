package com.haloqlinic.dokter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.dokter.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.dokter.adapter.JadwalKonsultasiAdapter;
import com.haloqlinic.dokter.api.ConfigRetrofit;
import com.haloqlinic.dokter.databinding.ActivityJadwalKonsultasiBinding;
import com.haloqlinic.dokter.databinding.ActivitySignupBinding;
import com.haloqlinic.dokter.model.listKonsultasi.DataItem;
import com.haloqlinic.dokter.model.listKonsultasi.ResponseDataKonsultasi;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class JadwalKonsultasiActivity extends AppCompatActivity {

    private ActivityJadwalKonsultasiBinding binding;

    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJadwalKonsultasiBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        preferencedConfig = new SharedPreferencedConfig(this);

        PushDownAnim.setPushDownAnimTo(binding.imgBackJadwalKonsultasi)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        binding.recyclerJadwalKonsultasi.setHasFixedSize(true);
        binding.recyclerJadwalKonsultasi.setLayoutManager(new LinearLayoutManager(JadwalKonsultasiActivity.this));

        loadJadwalKonsultasi();
    }

    private void loadJadwalKonsultasi() {

        ProgressDialog progressDialog = new ProgressDialog(JadwalKonsultasiActivity.this);
        progressDialog.setMessage("Memuat Jadwal Konsultasi");
        progressDialog.show();

        ConfigRetrofit.service.dataKonsultasi(preferencedConfig.getPreferenceIdDokter(), "1")
                .enqueue(new Callback<ResponseDataKonsultasi>() {
                    @Override
                    public void onResponse(Call<ResponseDataKonsultasi> call, Response<ResponseDataKonsultasi> response) {
                        if (response.isSuccessful()){

                            progressDialog.dismiss();
                            List<DataItem> dataKonsultasi = response.body().getData();
                            JadwalKonsultasiAdapter adapter = new JadwalKonsultasiAdapter(JadwalKonsultasiActivity.this, dataKonsultasi);
                            binding.recyclerJadwalKonsultasi.setAdapter(adapter);

                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(JadwalKonsultasiActivity.this, "Gagal Memuat Data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseDataKonsultasi> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(JadwalKonsultasiActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadJadwalKonsultasi();
    }
}