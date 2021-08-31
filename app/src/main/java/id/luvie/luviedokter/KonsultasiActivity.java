package id.luvie.luviedokter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import id.luvie.luviedokter.SharedPreference.SharedPreferencedConfig;
import id.luvie.luviedokter.adapter.KonsultasiAdapter;
import id.luvie.luviedokter.api.ConfigRetrofit;

import id.luvie.luviedokter.databinding.ActivityKonsultasiBinding;
import id.luvie.luviedokter.model.listKonsultasi.DataItem;
import id.luvie.luviedokter.model.listKonsultasi.ResponseDataKonsultasi;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class KonsultasiActivity extends AppCompatActivity {

    private ActivityKonsultasiBinding binding;

    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKonsultasiBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        preferencedConfig = new SharedPreferencedConfig(KonsultasiActivity.this);

        PushDownAnim.setPushDownAnimTo(binding.imgBackKonsultasi)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        binding.recyclerKonsultasi.setHasFixedSize(true);
        binding.recyclerKonsultasi.setLayoutManager(new LinearLayoutManager(KonsultasiActivity.this));

        loadKonsultasi();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadKonsultasi();
    }

    private void loadKonsultasi() {

        ProgressDialog progressDialog = new ProgressDialog(KonsultasiActivity.this);
        progressDialog.setMessage("Memuat Konsultasi");
        progressDialog.show();

        ConfigRetrofit.service.dataKonsultasi(preferencedConfig.getPreferenceIdDokter(), "0").enqueue(new Callback<ResponseDataKonsultasi>() {
            @Override
            public void onResponse(Call<ResponseDataKonsultasi> call, Response<ResponseDataKonsultasi> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    List<DataItem> dataKonsultasi = response.body().getData();
                    KonsultasiAdapter adapter = new KonsultasiAdapter(KonsultasiActivity.this, dataKonsultasi);
                    binding.recyclerKonsultasi.setAdapter(adapter);
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(KonsultasiActivity.this, "Gagal Memuat Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataKonsultasi> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(KonsultasiActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}