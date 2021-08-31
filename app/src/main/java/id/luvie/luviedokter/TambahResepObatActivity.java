package id.luvie.luviedokter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import id.luvie.luviedokter.SharedPreference.SharedPreferencedConfig;
import id.luvie.luviedokter.adapter.ListObatAdapter;
import id.luvie.luviedokter.api.ConfigRetrofit;


import id.luvie.luviedokter.databinding.ActivityTambahResepObatBinding;
import id.luvie.luviedokter.model.listRecipe.DataItem;
import id.luvie.luviedokter.model.listRecipe.ResponseDataRecipe;
import id.luvie.luviedokter.model.updateRecipe.ResponseUpdateResep;
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

    String id_member = "";

    private SharedPreferencedConfig preferencedConfig;

    List<DataItem> dataResep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTambahResepObatBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        preferencedConfig = new SharedPreferencedConfig(this);

        if (dataResep!=null) {

            dataResep.clear();

        }

        id_transaksi = getIntent().getStringExtra("id_transaksi");
        id_customer = getIntent().getStringExtra("id_customer");
        String id_kategori_dokter = preferencedConfig.getPreferenceIdKategori();

        if (id_kategori_dokter.equals("2")){
            binding.relativeKlinik.setVisibility(View.VISIBLE);
        }else if (id_kategori_dokter.equals("3")){
            binding.relativeKlinik.setVisibility(View.GONE);
        }

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

        PushDownAnim.setPushDownAnimTo(binding.btnPilihTindakan)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TambahResepObatActivity.this, TindakanActivity.class);
                        intent.putExtra("id_transaksi", id_transaksi);
                        intent.putExtra("id_customer", id_customer);
                        startActivity(intent);
                    }
                });

        loadDataResep();
    }

    private void updateRecipe() {

        String catatan = binding.edtCatatanResepObat.getText().toString();
        String diagnosis = binding.edtDiagnosaResep.getText().toString();

        if (catatan.isEmpty()){
            binding.edtCatatanResepObat.setError("Catatan Tidak Boleh Kosong");
            binding.edtCatatanResepObat.requestFocus();
            return;
        }

        if (diagnosis.isEmpty()){
            binding.edtDiagnosaResep.setError("Diagnosis Tidak Boleh Kosong");
            binding.edtDiagnosaResep.requestFocus();
            return;
        }

        ArrayList<String> id_pesan = new ArrayList<>();
        ArrayList<String> jumlah = new ArrayList<>();
        ArrayList<String> harga = new ArrayList<>();
        ArrayList<String> berat_item = new ArrayList<>();

        for (int a = 0; a<dataResep.size(); a++){

            id_pesan.add(dataResep.get(a).getIdPesan());
            jumlah.add(dataResep.get(a).getJumlah());
            harga.add(dataResep.get(a).getHargaJual());
            berat_item.add(dataResep.get(a).getBeratItem());
            id_member = dataResep.get(a).getIdMember();

        }

        ProgressDialog progressDialogUpdate = new ProgressDialog(TambahResepObatActivity.this);
        progressDialogUpdate.setMessage("Kirim List Obat");
        progressDialogUpdate.show();

        ConfigRetrofit.service.updateResep(id_transaksi, id_pesan, jumlah, harga, berat_item, catatan,
                diagnosis, id_member)
                .enqueue(new Callback<ResponseUpdateResep>() {
            @Override
            public void onResponse(Call<ResponseUpdateResep> call, Response<ResponseUpdateResep> response) {
                if (response.isSuccessful()){

                    progressDialogUpdate.dismiss();
                    Log.d("cekLisParam", "idPesan: "+id_pesan);
                    Log.d("cekLisParam", "jumlah: "+jumlah);
                    Log.d("cekLisParam", "harga: "+harga);
                    Log.d("cekLisParam", "beratiItem: "+berat_item);
                    Log.d("cekLisParam", "id_member: "+id_member);
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