package com.haloqlinic.dokter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.haloqlinic.dokter.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.dokter.api.ConfigRetrofit;
import com.haloqlinic.dokter.databinding.ActivityDetailKonsultasiBinding;
import com.haloqlinic.dokter.databinding.ActivityDetailProdukBinding;
import com.haloqlinic.dokter.model.addRecipe.ResponseTambahResep;
import com.haloqlinic.dokter.model.cariProduk.DataItem;
import com.haloqlinic.dokter.model.cariProduk.ResponseCariProduk;
import com.haloqlinic.dokter.model.cariProduk.VariasiItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class DetailProdukActivity extends AppCompatActivity {

    private ActivityDetailProdukBinding binding;

    String nama_produk, id_transaksi, id_dokter, id_produk, id_member, berat,
            jumlah, harga, id_variasi, variasi, aturan, keterangan, stock, id_customer, jenis_obat;
    List<VariasiItem> variasiItems = new ArrayList<>();

    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailProdukBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        preferencedConfig = new SharedPreferencedConfig(DetailProdukActivity.this);

        nama_produk = getIntent().getStringExtra("nama_produk");
        id_transaksi = getIntent().getStringExtra("id_transaksi");
        id_customer = getIntent().getStringExtra("id_customer");
        jenis_obat = getIntent().getStringExtra("jenis_obat");

        loadData();

        PushDownAnim.setPushDownAnimTo(binding.imgBackDetailProduk)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        binding.spinnerVariasiDetailProduk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_variasi = variasiItems.get(position).getId();
                stock = variasiItems.get(position).getStok();
                variasi = (String) variasiItems.get(position).getVariasi();

                if (!variasi.equals("")) {
                    binding.textLabelVariasi.setVisibility(View.VISIBLE);
                    binding.textVariasiDetailProduk.setVisibility(View.VISIBLE);
                    binding.textLabelStock.setVisibility(View.VISIBLE);
                    binding.textStockDetailProduk.setVisibility(View.VISIBLE);
                    binding.textVariasiDetailProduk.setText(variasi);
                    binding.textStockDetailProduk.setText(stock);
                }else if (variasi.equals("")){
                    binding.textPilihVariasi.setVisibility(View.GONE);
                    binding.spinnerVariasiDetailProduk.setVisibility(View.GONE);
                    binding.textLabelVariasi.setVisibility(View.GONE);
                    binding.textVariasiDetailProduk.setVisibility(View.GONE);
                    binding.textLabelStock.setVisibility(View.VISIBLE);
                    binding.textStockDetailProduk.setVisibility(View.VISIBLE);
                    binding.textStockDetailProduk.setText(stock);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        PushDownAnim.setPushDownAnimTo(binding.btnTambahObat)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tambahObat();
                    }
                });

    }

    private void tambahObat() {

        jumlah = binding.edtJumlahDetailProduk.getText().toString();
        aturan = binding.edtAturanDetailProduk.getText().toString();
        keterangan = binding.edtKeteranganDetailProduk.getText().toString();
        id_dokter = preferencedConfig.getPreferenceIdDokter();

        if (jumlah.isEmpty()){
            binding.edtJumlahDetailProduk.setError("Jumlah Tidak Boleh Kosong");
            binding.edtJumlahDetailProduk.requestFocus();
            return;
        }

        if (aturan.isEmpty()){

            binding.edtAturanDetailProduk.setError("Aturan Tidak Boleh Kosong");
            binding.edtAturanDetailProduk.requestFocus();
            return;

        }

        if (keterangan.isEmpty()){
            binding.edtKeteranganDetailProduk.setError("Keterangan tidak boleh kosong");
            binding.edtKeteranganDetailProduk.requestFocus();
            return;
        }

        int harga_total = Integer.parseInt(jumlah) * Integer.parseInt(harga);
        int berat_total = Integer.parseInt(jumlah) * Integer.parseInt(berat);

        String harga_post = String.valueOf(harga_total);
        String berat_post = String.valueOf(berat_total);

        Log.d("paramAddRecipe", "id_transaksi: "+id_transaksi);
        Log.d("paramAddRecipe", "id_customer: "+id_customer);
        Log.d("paramAddRecipe", "id_dokter: "+id_dokter);
        Log.d("paramAddRecipe", "id_produk: "+id_produk);
        Log.d("paramAddRecipe", "id_member: "+id_member);
        Log.d("paramAddRecipe", "berat: "+berat_total);
        Log.d("paramAddRecipe", "jumlah: "+jumlah);
        Log.d("paramAddRecipe", "harga: "+harga_total);
        Log.d("paramAddRecipe", "id_variasi: "+id_variasi);
        Log.d("paramAddRecipe", "variasi: "+variasi);
        Log.d("paramAddRecipe", "aturan: "+aturan);
        Log.d("paramAddRecipe", "keterangan: "+keterangan);

        ProgressDialog progressDialog = new ProgressDialog(DetailProdukActivity.this);
        progressDialog.setMessage("Menambahkan Resep Obat");
        progressDialog.show();

        ConfigRetrofit.service.tambahResep(id_transaksi, id_customer, id_dokter, id_produk, id_member, berat_post, jumlah, id_variasi,
                variasi, aturan, keterangan, harga).enqueue(new Callback<ResponseTambahResep>() {
            @Override
            public void onResponse(Call<ResponseTambahResep> call, Response<ResponseTambahResep> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();

                    Log.d("codeResponse", "berhasil: "+response.code());
                    Toast.makeText(DetailProdukActivity.this, "Berhasil Tambah Resep obat", Toast.LENGTH_SHORT).show();
                    finish();

                }else{
                    progressDialog.dismiss();
                    Log.d("codeResponse", "gagal: "+response.code());
                    Toast.makeText(DetailProdukActivity.this, "Gagal tambah resep obat", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTambahResep> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DetailProdukActivity.this, "Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadData() {

        ConfigRetrofit.service.cariProduk(jenis_obat, preferencedConfig.getPreferenceIdDokter(), nama_produk)
                .enqueue(new Callback<ResponseCariProduk>() {
            @Override
            public void onResponse(Call<ResponseCariProduk> call, Response<ResponseCariProduk> response) {
                if (response.isSuccessful()){

                    List<DataItem> dataItems = response.body().getData();

                    String url_image = "";
                    String nama_produk = "";
                    String harga_produk = "";

                    for (int a = 0; a<dataItems.size(); a++){

                        url_image = dataItems.get(a).getImg();
                        nama_produk = dataItems.get(a).getNamaProduk();
                        harga_produk = dataItems.get(a).getHarga();
                        id_produk = dataItems.get(a).getIdProduk();
                        berat = dataItems.get(a).getBerat();
                        harga = dataItems.get(a).getHargaJual();
                        id_member = dataItems.get(a).getIdMember();
                        variasiItems = dataItems.get(a).getVariasi();

                    }

                    binding.textNamaDetailProduk.setText(nama_produk);
                    binding.textHargaDetailProduk.setText("Rp" + NumberFormat.getInstance().format(Integer.parseInt(harga_produk)));

                    Glide.with(DetailProdukActivity.this)
                            .load(url_image)
                            .error(R.mipmap.ic_launcher)
                            .into(binding.imgDetailProduk);

                    Log.d("checkDataSpinner", "onResponse: "+variasiItems);

                    if (variasiItems.size()<1){
                        binding.spinnerVariasiDetailProduk.setVisibility(View.GONE);
                    }

                    List<String> listSpinnerVariasi = new ArrayList<>();
                    for (int i = 0; i<variasiItems.size(); i++){
                        listSpinnerVariasi.add((String) variasiItems.get(i).getVariasi());
                    }

                    ArrayAdapter<String> adapterVariasi = new ArrayAdapter<String>(DetailProdukActivity.this,
                            android.R.layout.simple_spinner_item, listSpinnerVariasi);
                    adapterVariasi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.spinnerVariasiDetailProduk.setAdapter(adapterVariasi);

                }
            }

            @Override
            public void onFailure(Call<ResponseCariProduk> call, Throwable t) {

            }
        });

    }
}