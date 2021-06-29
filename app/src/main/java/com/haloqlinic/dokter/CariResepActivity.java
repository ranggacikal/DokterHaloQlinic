package com.haloqlinic.dokter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.haloqlinic.dokter.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.dokter.adapter.CariObatAdapter;
import com.haloqlinic.dokter.api.ConfigRetrofit;
import com.haloqlinic.dokter.databinding.ActivityCariResepBinding;
import com.haloqlinic.dokter.databinding.ActivityKonsultasiBinding;
import com.haloqlinic.dokter.model.cariProduk.DataItem;
import com.haloqlinic.dokter.model.cariProduk.ResponseCariProduk;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CariResepActivity extends AppCompatActivity {

    private ActivityCariResepBinding binding;

    public String jenisObat;
    private SharedPreferencedConfig preferencedConfig;

    public String id_transaksi, id_customer;

    public CariResepActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCariResepBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        preferencedConfig = new SharedPreferencedConfig(this);

        jenisObat = getIntent().getStringExtra("jenis_resep");
        id_customer = getIntent().getStringExtra("id_customer");
        id_transaksi = getIntent().getStringExtra("id_transaksi");

        if (jenisObat.equals("klinik")){
            binding.linearResepKlinik.setVisibility(View.VISIBLE);
            binding.linearResepMitra.setVisibility(View.GONE);
        }else if (jenisObat.equals("mitra")){
            binding.linearResepKlinik.setVisibility(View.GONE);
            binding.linearResepMitra.setVisibility(View.VISIBLE);
        }

        binding.recyclerCariResepKlinik.setHasFixedSize(true);
        binding.recyclerCariResepMitra.setHasFixedSize(true);

        binding.recyclerCariResepKlinik.setLayoutManager(new LinearLayoutManager(CariResepActivity.this));
        binding.recyclerCariResepMitra.setLayoutManager(new LinearLayoutManager(CariResepActivity.this));

        binding.searchResepObatKlinik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchResepObatKlinik.setQueryHint("Cari Produk Klinik");
                binding.searchResepObatKlinik.setIconified(false);
            }
        });

        binding.searchResepObatMitra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchResepObatMitra.setQueryHint("Cari Produk Mitra");
                binding.searchResepObatMitra.setIconified(false);
            }
        });

        binding.searchResepObatKlinik.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String textCari) {

                cariProdukKlinik(textCari);
                return true;
            }
        });

        binding.searchResepObatMitra.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String textCariMitra) {
                cariProdukMitra(textCariMitra);
                return true;
            }
        });

    }

    private void cariProdukMitra(String textCariMitra) {

        if (textCariMitra.equals("")){
            binding.recyclerCariResepMitra.setVisibility(View.GONE);
        }else{

            binding.recyclerCariResepMitra.setVisibility(View.VISIBLE);

            ConfigRetrofit.service.cariProduk("mitra", preferencedConfig.getPreferenceIdDokter(),
                    textCariMitra).enqueue(new Callback<ResponseCariProduk>() {
                @Override
                public void onResponse(Call<ResponseCariProduk> call, Response<ResponseCariProduk> response) {
                    if (response.isSuccessful()){
                        List<DataItem> dataItems = response.body().getData();
                        CariObatAdapter adapter = new CariObatAdapter(CariResepActivity.this,
                                dataItems, CariResepActivity.this);
                        binding.recyclerCariResepMitra.setAdapter(adapter);
                    }else{
                        Toast.makeText(CariResepActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseCariProduk> call, Throwable t) {
                    Toast.makeText(CariResepActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    private void cariProdukKlinik(String textCari) {

        if (textCari.equals("")){
            binding.recyclerCariResepKlinik.setVisibility(View.GONE);
        }else{
            binding.recyclerCariResepKlinik.setVisibility(View.VISIBLE);

            ConfigRetrofit.service.cariProduk("klinik", preferencedConfig.getPreferenceIdDokter(), textCari)
                    .enqueue(new Callback<ResponseCariProduk>() {
                        @Override
                        public void onResponse(Call<ResponseCariProduk> call, Response<ResponseCariProduk> response) {
                            if (response.isSuccessful()){

                                List<DataItem> dataCari = response.body().getData();

                                CariObatAdapter adapter = new CariObatAdapter(CariResepActivity.this,
                                        dataCari, CariResepActivity.this);

                                binding.recyclerCariResepKlinik.setAdapter(adapter);

                            }else{
                                Toast.makeText(CariResepActivity.this, "Data kosong", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseCariProduk> call, Throwable t) {
                            Toast.makeText(CariResepActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }
}