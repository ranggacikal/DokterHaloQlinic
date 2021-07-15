package com.haloqlinic.dokter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.haloqlinic.dokter.adapter.CariObatAdapter;
import com.haloqlinic.dokter.adapter.ListObatAdapter;
import com.haloqlinic.dokter.api.ConfigRetrofit;
import com.haloqlinic.dokter.databinding.ActivityDetailKonsultasiBinding;
import com.haloqlinic.dokter.databinding.ActivityTambahResepObatBinding;
import com.haloqlinic.dokter.model.cariProduk.ResponseCariProduk;
import com.haloqlinic.dokter.model.listRecipe.DataItem;
import com.haloqlinic.dokter.model.listRecipe.ResponseDataRecipe;
import com.haloqlinic.dokter.model.updateRecipe.ResponseUpdateResep;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class TambahResepObatActivity extends AppCompatActivity {

    private ActivityTambahResepObatBinding binding;

    public String id_transaksi, id_customer;

    List<DataItem> dataResep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTambahResepObatBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if (dataResep!=null) {

            dataResep.clear();

        }

        id_transaksi = getIntent().getStringExtra("id_transaksi");
        id_customer = getIntent().getStringExtra("id_customer");

        Log.d("cekIdTransaksi", "onCreate: "+ id_transaksi);

        binding.recyclerListResep.setHasFixedSize(true);
        binding.recyclerListResep.setLayoutManager(new LinearLayoutManager(TambahResepObatActivity.this));

        PushDownAnim.setPushDownAnimTo(binding.btnKirimResep)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateRecipe();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnResepKlinik)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(TambahResepObatActivity.this, CariResepActivity.class);
                        intent.putExtra("id_transaksi", id_transaksi);
                        intent.putExtra("id_customer", id_customer);
                        intent.putExtra("jenis_resep", "klinik");
                        startActivity(intent);
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnResepLuarKlinik)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(TambahResepObatActivity.this, CariResepActivity.class);
                        intent.putExtra("id_transaksi", id_transaksi);
                        intent.putExtra("id_customer", id_customer);
                        intent.putExtra("jenis_resep", "mitra");
                        startActivity(intent);
                    }
                });

        loadDataResep();
    }

    private void updateRecipe() {

        ArrayList<String> id_pesan = new ArrayList<>();
        ArrayList<String> jumlah = new ArrayList<>();
        ArrayList<String> harga = new ArrayList<>();
        ArrayList<String> berat_item = new ArrayList<>();

        for (int a = 0; a<dataResep.size(); a++){

            id_pesan.add(dataResep.get(a).getIdPesan());
            jumlah.add(dataResep.get(a).getJumlah());
            harga.add(dataResep.get(a).getHargaJual());
            berat_item.add(dataResep.get(a).getBeratItem());

        }

        ProgressDialog progressDialogUpdate = new ProgressDialog(TambahResepObatActivity.this);
        progressDialogUpdate.setMessage("Kirim List Obat");
        progressDialogUpdate.show();

        ConfigRetrofit.service.updateResep(id_transaksi, id_pesan, jumlah, harga, berat_item).enqueue(new Callback<ResponseUpdateResep>() {
            @Override
            public void onResponse(Call<ResponseUpdateResep> call, Response<ResponseUpdateResep> response) {
                if (response.isSuccessful()){

                    progressDialogUpdate.dismiss();
                    Log.d("cekLisParam", "idPesan: "+id_pesan);
                    Log.d("cekLisParam", "jumlah: "+jumlah);
                    Log.d("cekLisParam", "harga: "+harga);
                    Log.d("cekLisParam", "beratiItem: "+berat_item);
                    Toast.makeText(TambahResepObatActivity.this, "Berhasil kirim list obat", Toast.LENGTH_SHORT).show();
                    finish();

                }else{
                    progressDialogUpdate.dismiss();
                    Toast.makeText(TambahResepObatActivity.this, "Gagal kirim list obat", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUpdateResep> call, Throwable t) {
                progressDialogUpdate.dismiss();
                Toast.makeText(TambahResepObatActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void loadDataResep() {

        ProgressDialog progressDialog = new ProgressDialog(TambahResepObatActivity.this);
        progressDialog.setMessage("Memuat Data Resep");
        progressDialog.dismiss();

        ConfigRetrofit.service.dataResep(id_transaksi).enqueue(new Callback<ResponseDataRecipe>() {
            @Override
            public void onResponse(Call<ResponseDataRecipe> call, Response<ResponseDataRecipe> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();
                    Log.d("paramIdTransaksi", "onResponse: "+id_transaksi);
                    dataResep = response.body().getData();
                    ListObatAdapter adapter = new ListObatAdapter(TambahResepObatActivity.this,
                            dataResep, TambahResepObatActivity.this);
                    binding.recyclerListResep.setAdapter(adapter);

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(TambahResepObatActivity.this, "Gagal Memuat Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataRecipe> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TambahResepObatActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataResep();
    }
}