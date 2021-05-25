package com.haloqlinic.dokter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.dokter.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.dokter.adapter.RiwayatPenarikanAdapter;
import com.haloqlinic.dokter.api.ConfigRetrofit;
import com.haloqlinic.dokter.databinding.ActivityTarikDanaBinding;
import com.haloqlinic.dokter.databinding.ActivityUpdatePasswordBinding;
import com.haloqlinic.dokter.model.listWithDrawal.DataItem;
import com.haloqlinic.dokter.model.listWithDrawal.ResponseListWithDrawal;
import com.haloqlinic.dokter.model.withdrawal.ResponseWithdrawal;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class TarikDanaActivity extends AppCompatActivity {

    private ActivityTarikDanaBinding binding;
    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTarikDanaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        preferencedConfig = new SharedPreferencedConfig(this);

        PushDownAnim.setPushDownAnimTo(binding.btnRequestTarikDana)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestPenarikan();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.imgBackPenarikanDana)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        binding.recyclerRiwayatPenarikan.setHasFixedSize(true);
        binding.recyclerRiwayatPenarikan.setLayoutManager(new LinearLayoutManager(TarikDanaActivity.this));

        loadRiwayatPenarikan();
    }

    private void loadRiwayatPenarikan() {

        ConfigRetrofit.service.listDrawal(preferencedConfig.getPreferenceIdDokter()).enqueue(new Callback<ResponseListWithDrawal>() {
            @Override
            public void onResponse(Call<ResponseListWithDrawal> call, Response<ResponseListWithDrawal> response) {
                if (response.isSuccessful()){

                    List<DataItem> dataListDrawal = response.body().getData();
                    RiwayatPenarikanAdapter adapter = new RiwayatPenarikanAdapter(TarikDanaActivity.this, dataListDrawal);
                    binding.recyclerRiwayatPenarikan.setAdapter(adapter);

                }else{
                    Toast.makeText(TarikDanaActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseListWithDrawal> call, Throwable t) {
                Toast.makeText(TarikDanaActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void requestPenarikan() {

        String nominal = binding.edtNominalPenarikan.getText().toString();

        if (nominal.isEmpty()){
            binding.edtNominalPenarikan.setError("Field tidak boleh kosong");
            binding.edtNominalPenarikan.requestFocus();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(TarikDanaActivity.this);
        progressDialog.setMessage("Request Penarikan");
        progressDialog.show();

        ConfigRetrofit.service.postWithdrawal(preferencedConfig.getPreferenceIdDokter(), nominal)
                .enqueue(new Callback<ResponseWithdrawal>() {
                    @Override
                    public void onResponse(Call<ResponseWithdrawal> call, Response<ResponseWithdrawal> response) {
                        if (response.isSuccessful()){

                            progressDialog.dismiss();
                            binding.edtNominalPenarikan.setText("");
                            loadRiwayatPenarikan();
                            Toast.makeText(TarikDanaActivity.this, "Berhasil Melakukan Request Penarikan Dana", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(TarikDanaActivity.this, "Gagal Request Penarikan Dana", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseWithdrawal> call, Throwable t) {
                        Toast.makeText(TarikDanaActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}