package com.haloqlinic.dokter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.haloqlinic.dokter.adapter.CariObatAdapter;
import com.haloqlinic.dokter.api.ConfigRetrofit;
import com.haloqlinic.dokter.databinding.ActivityDetailKonsultasiBinding;
import com.haloqlinic.dokter.databinding.ActivityTambahResepObatBinding;
import com.haloqlinic.dokter.model.cariProduk.DataItem;
import com.haloqlinic.dokter.model.cariProduk.ResponseCariProduk;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class TambahResepObatActivity extends AppCompatActivity {

    private ActivityTambahResepObatBinding binding;

    public String id_transaksi, id_customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTambahResepObatBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        id_transaksi = getIntent().getStringExtra("id_transaksi");
        id_customer = getIntent().getStringExtra("id_customer");

        binding.searchTambahResepObat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchTambahResepObat.setQueryHint("Cari Produk / Obat");
                binding.searchTambahResepObat.setIconified(false);
            }
        });

        binding.searchTambahResepObat.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                cariProduk(newText);
                return true;
            }
        });

        PushDownAnim.setPushDownAnimTo(binding.btnLihatListResep)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TambahResepObatActivity.this, ListResepActivity.class);
                        intent.putExtra("id_transaksi", id_transaksi);
                        startActivity(intent);
                    }
                });
    }

    private void cariProduk(String newText) {

        if (newText.equals("")){

            binding.recyclerTambahResepObat.setVisibility(View.GONE);

        }else{
            binding.recyclerTambahResepObat.setVisibility(View.VISIBLE);

            ConfigRetrofit.service.cariProduk(newText).enqueue(new Callback<ResponseCariProduk>() {
                @Override
                public void onResponse(Call<ResponseCariProduk> call, Response<ResponseCariProduk> response) {
                    if (response.isSuccessful()){

                        binding.recyclerTambahResepObat.setHasFixedSize(true);
                        binding.recyclerTambahResepObat.setLayoutManager(new LinearLayoutManager(TambahResepObatActivity.this));

                        List<DataItem> dataProduk = response.body().getData();

                        CariObatAdapter adapter = new CariObatAdapter(TambahResepObatActivity.this, dataProduk,
                                TambahResepObatActivity.this);

                        binding.recyclerTambahResepObat.setAdapter(adapter);

                    }else {

                        Toast.makeText(TambahResepObatActivity.this, "Gagal Memuat Data", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<ResponseCariProduk> call, Throwable t) {
                    Toast.makeText(TambahResepObatActivity.this, "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}