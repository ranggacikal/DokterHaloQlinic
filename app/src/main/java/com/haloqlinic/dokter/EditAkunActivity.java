package com.haloqlinic.dokter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.haloqlinic.dokter.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.dokter.api.ConfigRetrofit;
import com.haloqlinic.dokter.databinding.ActivityEditAkunBinding;
import com.haloqlinic.dokter.databinding.ActivityMainBinding;
import com.haloqlinic.dokter.model.editAkun.ResponseEditAkun;
import com.haloqlinic.dokter.model.kecamatan.ResponseDataKecamatan;
import com.haloqlinic.dokter.model.kota.ResponseDatakota;
import com.haloqlinic.dokter.model.kota.ResponseItem;
import com.haloqlinic.dokter.model.provinsi.DataItem;
import com.haloqlinic.dokter.model.provinsi.ResponseDataProvinsi;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class EditAkunActivity extends AppCompatActivity {

    private ActivityEditAkunBinding binding;

    private SharedPreferencedConfig preferencedConfig;

    List<DataItem> dataProvinsi;
    List<ResponseItem> dataKota;
    List<com.haloqlinic.dokter.model.kecamatan.ResponseItem> dataKecamatan;

    String id_provinsi = "";
    String id_kota = "";
    String id_kecamatan = "";

    String provinsi, kota, kecamatan, jenis_kelamin;

    String id_provinsi_post = "";
    String id_kota_post = "";
    String id_kecamatan_post = "";
    String jenis_kelamin_post = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditAkunBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        preferencedConfig = new SharedPreferencedConfig(this);

        ArrayList<String> jenisKelaminList = new ArrayList<>();
        jenisKelaminList.add("Laki - Laki");
        jenisKelaminList.add("Perempuan");

        binding.imgBackEditAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.relativeProvinsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.relativeProvinsi.setVisibility(View.GONE);
                binding.spinnerProvinsiEditAkun.setVisibility(View.VISIBLE);
                initSpinnerProvinsi();
            }
        });

        binding.relativeKota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.relativeKota.setVisibility(View.GONE);
                binding.spinnerKotaEditAkun.setVisibility(View.VISIBLE);
            }
        });

        binding.relativeKecamatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.relativeKecamatan.setVisibility(View.GONE);
                binding.spinnerKecamatanEditAkun.setVisibility(View.VISIBLE);
            }
        });

        binding.relativeJenisKelamin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.relativeJenisKelamin.setVisibility(View.GONE);
                binding.spinnerJkEditAkun.setVisibility(View.VISIBLE);
            }
        });

        ArrayAdapter<String> adapterJenisKelamin = new ArrayAdapter<String>(EditAkunActivity.this, R.layout.spinner_item, jenisKelaminList);

        binding.spinnerJkEditAkun.setAdapter(adapterJenisKelamin);

        binding.spinnerJkEditAkun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jenis_kelamin = jenisKelaminList.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        initData();



        binding.spinnerProvinsiEditAkun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_provinsi = dataProvinsi.get(position).getProvinceId();
                provinsi = dataProvinsi.get(position).getProvince();
                initSpinnerKota(id_provinsi);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerKotaEditAkun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_kota = dataKota.get(position).getCityId();
                kota = dataKota.get(position).getCityName();
                initSpinnerKecamatan(id_kota);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerKecamatanEditAkun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_kecamatan = dataKecamatan.get(position).getSubdistrictId();
                kecamatan = dataKecamatan.get(position).getSubdistrictName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        PushDownAnim.setPushDownAnimTo(binding.btnEditAkun)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tampilDialog();
                    }
                });

    }

    private void tampilDialog() {

        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(this)
                .setTitle("Edit akun?")
                .setMessage("Apakah anda yakin ingin mengubah data ini?")
                .setCancelable(false)
                .setPositiveButton("Ubah", R.drawable.ic_edit, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        editAkun();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Batal", R.drawable.ic_close, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        // Show Dialog
        mBottomSheetDialog.show();

    }

    private void editAkun() {

        String nama = binding.edtNamaEditAkun.getText().toString();
        String alamat = binding.edtAlamatEditAkun.getText().toString();
        String no_hp = binding.edtNoHpEditAkun.getText().toString();

        if (nama.isEmpty()){
            binding.edtNamaEditAkun.setError("Nama tidak boleh kosong");
            binding.edtNamaEditAkun.requestFocus();
            return;
        }

        if (alamat.isEmpty()){
            binding.edtAlamatEditAkun.setError("Alamat tidak boleh kosong");
            binding.edtAlamatEditAkun.requestFocus();
            return;
        }

        if (no_hp.isEmpty()){
            binding.edtNoHpEditAkun.setError("No. HP tidak boleh kosong");
            binding.edtNoHpEditAkun.requestFocus();
            return;
        }

        if (!id_provinsi.equals("")){
            id_provinsi_post = id_provinsi;
        }else{
            id_provinsi_post = preferencedConfig.getPreferenceIdProvinsi();
            provinsi = preferencedConfig.getPreferenceProvinsi();
        }

        if (!id_kota.equals("")){
            id_kota_post = id_kota;
        }else{
            id_kota_post = preferencedConfig.getPreferenceIdKota();
            kota = preferencedConfig.getPreferenceKota();
        }

        if (!id_kecamatan.equals("")){
            id_kecamatan_post = id_kecamatan;
        }else{
            id_kecamatan_post = preferencedConfig.getPreferenceIdKecamatan();
            kecamatan = preferencedConfig.getPreferenceKecamatan();
        }

        if (jenis_kelamin!=null){
            jenis_kelamin_post = jenis_kelamin;
        }else{
            jenis_kelamin_post = preferencedConfig.getPreferenceJk();
        }

        ProgressDialog progressDialog = new ProgressDialog(EditAkunActivity.this);
        progressDialog.setMessage("Mengubah data");
        progressDialog.show();

        ConfigRetrofit.service.editAkun(preferencedConfig.getPreferenceIdDokter(), nama, alamat, id_provinsi_post,
                id_kota_post, id_kecamatan_post, no_hp, jenis_kelamin_post).enqueue(new Callback<ResponseEditAkun>() {
            @Override
            public void onResponse(Call<ResponseEditAkun> call, Response<ResponseEditAkun> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_NAMA, nama);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_ALAMAT, alamat);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_ID_PROVINSI, id_provinsi_post);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_ID_KOTA, id_kota_post);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_ID_KECAMATAN, id_kecamatan_post);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_PROVINSI, provinsi);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_KOTA, kota);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_KECAMATAN, kecamatan);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_NO_HP, no_hp);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_JK, jenis_kelamin_post);
                    Toast.makeText(EditAkunActivity.this, "Edit Data berhasil", Toast.LENGTH_SHORT).show();
                    finish();

                }else{
                    progressDialog.dismiss();

                    Log.d("testEdit", "nama: "+nama);
                    Log.d("testEdit", "alamat: "+alamat);
                    Log.d("testEdit", "idProvinsi: "+id_provinsi_post);
                    Log.d("testEdit", "idKota: "+id_kota_post);
                    Log.d("testEdit", "idKecamatan: "+id_kecamatan_post);
                    Log.d("testEdit", "provinsi: "+provinsi);
                    Log.d("testEdit", "kota: "+kota);
                    Log.d("testEdit", "kecamatan: "+kecamatan);
                    Log.d("testEdit", "noHp: "+no_hp);
                    Log.d("testEdit", "jk: "+jenis_kelamin_post);
                    Log.d("testEdit", "id_provinsi: "+id_provinsi);
                    Log.d("testEdit", "id_kota: "+id_kota);
                    Log.d("testEdit", "id_kecamatan: "+id_kecamatan);

                    Toast.makeText(EditAkunActivity.this, "Gagal Edit Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEditAkun> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditAkunActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initSpinnerKecamatan(String id_kota) {

        ProgressDialog progressDialog = new ProgressDialog(EditAkunActivity.this);
        progressDialog.setMessage("Memuat kecamatan");
        progressDialog.show();

        ConfigRetrofit.service.dataKecamatan(id_kota).enqueue(new Callback<ResponseDataKecamatan>() {
            @Override
            public void onResponse(Call<ResponseDataKecamatan> call, Response<ResponseDataKecamatan> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();

                    dataKecamatan = response.body().getResponse();
                    List<String> listSpinnerKecamatan = new ArrayList<String>();

                    for (int i = 0; i<dataKecamatan.size(); i++){
                        listSpinnerKecamatan.add(dataKecamatan.get(i).getSubdistrictName());
                    }

                    ArrayAdapter<String> adapterKecamatan = new ArrayAdapter<String>(EditAkunActivity.this,
                            R.layout.spinner_item, listSpinnerKecamatan);

                    adapterKecamatan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    binding.spinnerKecamatanEditAkun.setAdapter(adapterKecamatan);

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(EditAkunActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataKecamatan> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditAkunActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initSpinnerKota(String id_provinsi) {

        ProgressDialog progressDialog = new ProgressDialog(EditAkunActivity.this);
        progressDialog.setMessage("Memuat Kota");
        progressDialog.show();

        ConfigRetrofit.service.dataKota(id_provinsi).enqueue(new Callback<ResponseDatakota>() {
            @Override
            public void onResponse(Call<ResponseDatakota> call, Response<ResponseDatakota> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    dataKota = response.body().getResponse();
                    List<String> listSpinnerKota = new ArrayList<String>();

                    for (int i = 0; i<dataKota.size(); i++){
                        listSpinnerKota.add(dataKota.get(i).getCityName());
                    }

                    ArrayAdapter<String> adapterKota = new ArrayAdapter<String>(EditAkunActivity.this,
                            R.layout.spinner_item, listSpinnerKota);

                    adapterKota.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    binding.spinnerKotaEditAkun.setAdapter(adapterKota);

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(EditAkunActivity.this, "Data kota kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDatakota> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditAkunActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initSpinnerProvinsi() {

        ProgressDialog progressDialog = new ProgressDialog(EditAkunActivity.this);
        progressDialog.setMessage("Memuat provinsi");
        progressDialog.show();

        ConfigRetrofit.service.dataProvinsi().enqueue(new Callback<ResponseDataProvinsi>() {
            @Override
            public void onResponse(Call<ResponseDataProvinsi> call, Response<ResponseDataProvinsi> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    dataProvinsi = response.body().getData();
                    List<String> listSpinnerProvinsi = new ArrayList<String>();

                    for (int i = 0; i<dataProvinsi.size(); i++){
                        listSpinnerProvinsi.add(dataProvinsi.get(i).getProvince());
                    }

                    ArrayAdapter<String> adapterProvinsi = new ArrayAdapter<String>(EditAkunActivity.this,
                            R.layout.spinner_item, listSpinnerProvinsi);
                    adapterProvinsi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.spinnerProvinsiEditAkun.setAdapter(adapterProvinsi);

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(EditAkunActivity.this, "Data provinsi kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataProvinsi> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditAkunActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initData() {

        String nama = preferencedConfig.getPreferenceNama();
        String alamat = preferencedConfig.getPreferenceAlamat();
        String nama_provinsi = preferencedConfig.getPreferenceProvinsi();
        String nama_kota = preferencedConfig.getPreferenceKota();
        String nama_kecamatan = preferencedConfig.getPreferenceKecamatan();
        String no_hp = preferencedConfig.getPreferenceNoHp();
        String jenis_kelamin = preferencedConfig.getPreferenceJk();

        if (nama.equals("")){

            binding.edtNamaEditAkun.setText("");

        }else{
            binding.edtNamaEditAkun.setText(nama);
        }

        if (alamat.equals("")){
            binding.edtAlamatEditAkun.setText("");
        }else{
            binding.edtAlamatEditAkun.setText(alamat);
        }

        if (nama_provinsi.equals("")){
            binding.spinnerProvinsiEditAkun.setVisibility(View.VISIBLE);
            binding.relativeProvinsi.setVisibility(View.GONE);
        }else{
            binding.textProvinsiEditAkun.setText(nama_provinsi);
            binding.relativeProvinsi.setVisibility(View.VISIBLE);
            binding.spinnerProvinsiEditAkun.setVisibility(View.GONE);
        }

        if (nama_kota.equals("")){
            binding.spinnerKotaEditAkun.setVisibility(View.VISIBLE);
            binding.relativeKota.setVisibility(View.GONE);
        }else{
            binding.textKotaEditAkun.setText(nama_kota);
            binding.relativeKota.setVisibility(View.VISIBLE);
            binding.spinnerKotaEditAkun.setVisibility(View.GONE);
        }

        if (nama_kecamatan.equals("")){
            binding.spinnerKecamatanEditAkun.setVisibility(View.VISIBLE);
            binding.relativeKecamatan.setVisibility(View.GONE);
        }else{
            binding.textKecamatanEditAkun.setText(nama_kecamatan);
            binding.relativeKecamatan.setVisibility(View.VISIBLE);
            binding.spinnerKecamatanEditAkun.setVisibility(View.GONE);
        }

        if (no_hp.equals("")){
            binding.edtNoHpEditAkun.setText("");
        }else{
            binding.edtNoHpEditAkun.setText(no_hp);
        }

        if (jenis_kelamin.equals("")){
            binding.spinnerJkEditAkun.setVisibility(View.VISIBLE);
            binding.relativeJenisKelamin.setVisibility(View.GONE);
        }else{
            binding.textJenisKelaminEditAkun.setText(jenis_kelamin);
            binding.relativeJenisKelamin.setVisibility(View.VISIBLE);
            binding.spinnerJkEditAkun.setVisibility(View.GONE);
        }

    }
}