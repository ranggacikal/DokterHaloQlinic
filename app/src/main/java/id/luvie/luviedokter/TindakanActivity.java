package id.luvie.luviedokter;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;

import id.luvie.luviedokter.SharedPreference.SharedPreferencedConfig;
import id.luvie.luviedokter.api.ConfigRetrofit;
import id.luvie.luviedokter.databinding.ActivityCariResepBinding;
import id.luvie.luviedokter.databinding.ActivityTindakanBinding;
import id.luvie.luviedokter.model.addRecipe.ResponseTambahResep;
import id.luvie.luviedokter.model.list_mitra.DataItem;
import id.luvie.luviedokter.model.list_mitra.ResponseListMitra;
import id.luvie.luviedokter.model.list_treatment.ResponseListTreatment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TindakanActivity extends AppCompatActivity {

    private ActivityTindakanBinding binding;
    List<DataItem> dataMitra;
    List<id.luvie.luviedokter.model.list_treatment.DataItem> dataTreatment;

    String id_transaksi, id_customer, id_member, id_kategori, id_produk, berat, harga;
    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTindakanBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        preferencedConfig = new SharedPreferencedConfig(TindakanActivity.this);

        id_transaksi = getIntent().getStringExtra("id_transaksi");
        id_customer = getIntent().getStringExtra("id_customer");

        loadMitraKlinik();

        binding.spinnerJenisTindakan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String jenis = dataTreatment.get(position).getNamaProduk();
                id_kategori = dataTreatment.get(position).getIdKategori();
                id_produk = dataTreatment.get(position).getIdProduk();
                berat = dataTreatment.get(position).getBerat();
                harga = dataTreatment.get(position).getHarga();
                Toast.makeText(TindakanActivity.this, "Anda Memilih : "+jenis,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerMitraKlinik.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_member = dataMitra.get(position).getIdMember();
                loadDataTreatment(id_member);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        PushDownAnim.setPushDownAnimTo(binding.imgBackTindakan)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnTambahTindakan)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tambahResep();
                    }
                });

    }

    private void tambahResep() {

        ProgressDialog progressTambahResep = new ProgressDialog(TindakanActivity.this);
        progressTambahResep.setMessage("Menambahkan Tindakan");
        progressTambahResep.show();

        ConfigRetrofit.service.tambahResep(id_transaksi, id_customer, preferencedConfig.getPreferenceIdDokter(),
                id_produk, id_member, berat, "", "", "", "", "", harga,
                id_kategori).enqueue(new Callback<ResponseTambahResep>() {
            @Override
            public void onResponse(Call<ResponseTambahResep> call, Response<ResponseTambahResep> response) {
                if (response.isSuccessful()){

                    progressTambahResep.dismiss();
                    Toast.makeText(TindakanActivity.this, "Berhasil Tambah Tindakan",
                            Toast.LENGTH_SHORT).show();
                    finish();

                }else{
                    progressTambahResep.dismiss();
                    Toast.makeText(TindakanActivity.this, "Gagal Menambahkan Tindakan",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTambahResep> call, Throwable t) {
                progressTambahResep.dismiss();
                Toast.makeText(TindakanActivity.this, "Error: "+t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadDataTreatment(String id_member) {

        ProgressDialog progressTreatment = new ProgressDialog(TindakanActivity.this);
        progressTreatment.setMessage("Memuat Data Treatment");
        progressTreatment.show();

        ConfigRetrofit.service.dataTreatment(id_member).enqueue(new Callback<ResponseListTreatment>() {
            @Override
            public void onResponse(Call<ResponseListTreatment> call, Response<ResponseListTreatment> response) {
                if (response.isSuccessful()){

                    progressTreatment.dismiss();
                    dataTreatment = response.body().getData();
                    List<String> listTreatment = new ArrayList<String>();

                    if(dataTreatment.size()<1){
                        binding.spinnerJenisTindakan.setVisibility(View.GONE);
                    }else{
                        binding.spinnerJenisTindakan.setVisibility(View.VISIBLE);
                    }

                    for (int a=0; a<dataTreatment.size(); a++){

                        listTreatment.add(dataTreatment.get(a).getNamaProduk());

                    }

                    ArrayAdapter<String> adapterTreatment = new ArrayAdapter<String>(TindakanActivity.this,
                            R.layout.spinner_item, listTreatment);
                    adapterTreatment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.spinnerJenisTindakan.setAdapter(adapterTreatment);

                }else{
                    progressTreatment.dismiss();
                    Toast.makeText(TindakanActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseListTreatment> call, Throwable t) {
                progressTreatment.dismiss();
                Toast.makeText(TindakanActivity.this, "Error: "+t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadMitraKlinik() {

        ProgressDialog progressDialog = new ProgressDialog(TindakanActivity.this);
        progressDialog.setMessage("Memuat Data Mitra");
        progressDialog.show();

        ConfigRetrofit.service.dataMitra().enqueue(new Callback<ResponseListMitra>() {
            @Override
            public void onResponse(Call<ResponseListMitra> call, Response<ResponseListMitra> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();
                    dataMitra = response.body().getData();
                    List<String> listMitra = new ArrayList<String>();

                    for (int a=0; a<dataMitra.size(); a++){

                        listMitra.add(dataMitra.get(a).getNama());

                    }

                    ArrayAdapter<String> adapterMitra = new ArrayAdapter<String>(TindakanActivity.this,
                            R.layout.spinner_item, listMitra);
                    adapterMitra.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.spinnerMitraKlinik.setAdapter(adapterMitra);

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(TindakanActivity.this, "Data Mitra Kosong",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseListMitra> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TindakanActivity.this, "Error: "+t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}