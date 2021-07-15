package com.haloqlinic.dokter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.haloqlinic.dokter.adapter.ListObatAdapter;
import com.haloqlinic.dokter.api.ConfigRetrofit;
import com.haloqlinic.dokter.databinding.ActivityDetailProdukBinding;
import com.haloqlinic.dokter.databinding.ActivityListResepBinding;
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

public class ListResepActivity extends AppCompatActivity {

    private ActivityListResepBinding binding;
    String id_transaksi;

    List<DataItem> dataResep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListResepBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        id_transaksi = getIntent().getStringExtra("id_transaksi");

        PushDownAnim.setPushDownAnimTo(binding.imgBackListResepObat)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        binding.recyclerListResepObat.setHasFixedSize(true);
        binding.recyclerListResepObat.setLayoutManager(new LinearLayoutManager(ListResepActivity.this));

        loadDataResep();

        PushDownAnim.setPushDownAnimTo(binding.btnKirimListObat)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateRecipe();
                    }
                });

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

        ProgressDialog progressDialogUpdate = new ProgressDialog(ListResepActivity.this);
        progressDialogUpdate.setMessage("Kirim List Obat");
        progressDialogUpdate.show();

        ConfigRetrofit.service.updateResep(id_transaksi, id_pesan, jumlah, harga, berat_item).enqueue(new Callback<ResponseUpdateResep>() {
            @Override
            public void onResponse(Call<ResponseUpdateResep> call, Response<ResponseUpdateResep> response) {
                if (response.isSuccessful()){

                    progressDialogUpdate.dismiss();
                    Toast.makeText(ListResepActivity.this, "Berhasil kirim list obat", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ListResepActivity.this, MainActivity.class));
                    finish();

                }else{
                    progressDialogUpdate.dismiss();
                    Toast.makeText(ListResepActivity.this, "Gagal kirim list obat", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUpdateResep> call, Throwable t) {
                progressDialogUpdate.dismiss();
                Toast.makeText(ListResepActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void loadDataResep() {

//        ProgressDialog progressDialog = new ProgressDialog(ListResepActivity.this);
//        progressDialog.setMessage("Memuat Data Resep");
//        progressDialog.dismiss();
//
//        ConfigRetrofit.service.dataResep(id_transaksi).enqueue(new Callback<ResponseDataRecipe>() {
//            @Override
//            public void onResponse(Call<ResponseDataRecipe> call, Response<ResponseDataRecipe> response) {
//                if (response.isSuccessful()){
//
//                    progressDialog.dismiss();
//                    dataResep = response.body().getData();
//                    ListObatAdapter adapter = new ListObatAdapter(ListResepActivity.this, dataResep);
//                    binding.recyclerListResepObat.setAdapter(adapter);
//
//                }else{
//                    progressDialog.dismiss();
//                    Toast.makeText(ListResepActivity.this, "Gagal Memuat Data", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseDataRecipe> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(ListResepActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}