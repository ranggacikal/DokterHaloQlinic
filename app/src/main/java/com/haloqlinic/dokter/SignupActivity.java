package com.haloqlinic.dokter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.haloqlinic.dokter.api.ConfigRetrofit;
import com.haloqlinic.dokter.databinding.ActivityMainBinding;
import com.haloqlinic.dokter.databinding.ActivitySignupBinding;
import com.haloqlinic.dokter.model.kategoriDokter.DataItem;
import com.haloqlinic.dokter.model.kategoriDokter.ResponseKategoriDokter;
import com.haloqlinic.dokter.model.kecamatan.ResponseDataKecamatan;
import com.haloqlinic.dokter.model.kota.ResponseDatakota;
import com.haloqlinic.dokter.model.kota.ResponseItem;
import com.haloqlinic.dokter.model.provinsi.ResponseDataProvinsi;
import com.haloqlinic.dokter.model.signUp.ResponseSignup;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;

    List<DataItem> dataKategori;
    List<com.haloqlinic.dokter.model.provinsi.DataItem> dataProvinsi;
    List<ResponseItem> dataKota;
    List<com.haloqlinic.dokter.model.kecamatan.ResponseItem> dataKecamatan;

    String id_kategori, id_provinsi, jenis_kelamin, id_kota, id_kecamatan, tanggal;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initKategoriDokter();

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        PushDownAnim.setPushDownAnimTo(binding.btnSignupPilihTanggal)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDateDialog();
                    }
                });

        binding.spinnerSignupKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_kategori = dataKategori.get(position).getIdKategori();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerSignupProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_provinsi = dataProvinsi.get(position).getProvinceId();

                initSpinnerKota(id_provinsi);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerSignupKota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_kota = dataKota.get(position).getCityId();

                initSpinnerKecamatan(id_kota);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinnerSignupKecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_kecamatan = dataKecamatan.get(position).getSubdistrictId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<String> jenisKelaminList = new ArrayList<>();
        jenisKelaminList.add("Laki - Laki");
        jenisKelaminList.add("Perempuan");

        ArrayAdapter<String> adapterJenisKelamin = new ArrayAdapter<String>(SignupActivity.this, R.layout.spinner_item, jenisKelaminList);
        binding.spinnerSignupJenisKelamin.setAdapter(adapterJenisKelamin);

        binding.spinnerSignupJenisKelamin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jenis_kelamin = jenisKelaminList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        showHidePass(binding.imgHidePasswordSignup);

        PushDownAnim.setPushDownAnimTo(binding.btnSignup)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        signUp();
                    }
                });

    }

    private void showDateDialog() {

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                binding.textSignupPengalaman.setText(dateFormatter.format(newDate.getTime()));
                tanggal = dateFormatter.format(newDate.getTime());
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();

    }

    private void signUp() {

        String nama = binding.edtSignupNama.getText().toString();
        String email = binding.edtSignupEmail.getText().toString();
        String no_hp = binding.edtSignupNoHp.getText().toString();
        String password = binding.edtSignupPassword.getText().toString();
        String alamat = binding.edtSignupAlamat.getText().toString();
        String no_sip = binding.edtSignupNoSip.getText().toString();
        String spesialis = binding.edtSignupSpesialis.getText().toString();
        String tentang = binding.edtSignupTentang.getText().toString();
        String str = binding.edtSignupNoStr.getText().toString();
        String alumni = binding.edtSignupAlumni.getText().toString();
        String tempat_praktik = binding.edtSignupTempatPraktik.getText().toString();

        if (nama.isEmpty()){
            binding.edtSignupNama.setError("Nama tidak boleh kosng");
            binding.edtSignupNama.requestFocus();
            return;
        }

        if (email.isEmpty()){
            binding.edtSignupEmail.setError("Email tidak boleh kosng");
            binding.edtSignupEmail.requestFocus();
            return;
        }

        if (no_hp.isEmpty()){
            binding.edtSignupNoHp.setError("No Hp tidak boleh kosng");
            binding.edtSignupNoHp.requestFocus();
            return;
        }

        if (password.isEmpty()){
            binding.edtSignupPassword.setError("Password tidak boleh kosng");
            binding.edtSignupPassword.requestFocus();
            return;
        }

        if (alamat.isEmpty()){
            binding.edtSignupAlamat.setError("alamat tidak boleh kosng");
            binding.edtSignupAlamat.requestFocus();
            return;
        }

        if (no_sip.isEmpty()){
            binding.edtSignupNoSip.setError("No SIP tidak boleh kosong");
            binding.edtSignupNoSip.requestFocus();
            return;
        }

        if (spesialis.isEmpty()){
            binding.edtSignupSpesialis.setError("Spesialis tidak boleh kosng");
            binding.edtSignupSpesialis.requestFocus();
            return;
        }

        if (tentang.isEmpty()){
            binding.edtSignupTentang.setError("Tentang tidak boleh kosng");
            binding.edtSignupTentang.requestFocus();
            return;
        }

        if (str.isEmpty()){
            binding.edtSignupNoStr.setError("No. STR tidak boleh kosong");
            binding.edtSignupNoStr.requestFocus();
            return;
        }

        if (alumni.isEmpty()){
            binding.edtSignupAlumni.setError("Alumni tidak boleh kosong");
            binding.edtSignupAlumni.requestFocus();
            return;
        }

        if (tempat_praktik.isEmpty()){
            binding.edtSignupTempatPraktik.setError("Tempat Praktik tidak boleh kosong");
            binding.edtSignupTempatPraktik.requestFocus();
            return;
        }

        if (tanggal.isEmpty() || tanggal == null){
            Toast.makeText(this, "Anda belum memilih tanggal", Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setMessage("Signup");
        progressDialog.show();

        ConfigRetrofit.service.signup(id_kategori, email, password, nama, alamat, no_hp, id_kecamatan,
                id_kota, id_provinsi, no_sip, spesialis, jenis_kelamin, tentang, str, alumni, tempat_praktik, tanggal).enqueue(new Callback<ResponseSignup>() {
            @Override
            public void onResponse(Call<ResponseSignup> call, Response<ResponseSignup> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();
                    Toast.makeText(SignupActivity.this, "Signup Success", Toast.LENGTH_SHORT).show();
                    finish();

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(SignupActivity.this, "Signup failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSignup> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showHidePass(View view){

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(view.getId()==R.id.img_hide_password_signup){

                    if(binding.edtSignupPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                        ((ImageView)(view)).setImageResource(R.drawable.icon_awesome_eye);
                        //Show Password
                        binding.edtSignupPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                    else{
                        ((ImageView)(view)).setImageResource(R.drawable.icon_awesome_eye_slash);

                        //Hide Password
                        binding.edtSignupPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    }
                }

            }
        });

    }

    private void initSpinnerKecamatan(String id_city) {

        ProgressDialog progressDialogKecamatan = new ProgressDialog(SignupActivity.this);
        progressDialogKecamatan.setMessage("Memuat Kecamatan");
        progressDialogKecamatan.show();

        ConfigRetrofit.service.dataKecamatan(id_city).enqueue(new Callback<ResponseDataKecamatan>() {
            @Override
            public void onResponse(Call<ResponseDataKecamatan> call, Response<ResponseDataKecamatan> response) {
                if (response.isSuccessful()){

                    progressDialogKecamatan.dismiss();

                    dataKecamatan = response.body().getResponse();
                    List<String> listSpinnerKecamatan = new ArrayList<String>();

                    for (int i = 0; i<dataKecamatan.size(); i++){
                        listSpinnerKecamatan.add(dataKecamatan.get(i).getSubdistrictName());
                    }

                    ArrayAdapter<String> adapterKecamatan = new ArrayAdapter<String>(SignupActivity.this,
                            R.layout.spinner_item, listSpinnerKecamatan);

                    adapterKecamatan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    binding.spinnerSignupKecamatan.setAdapter(adapterKecamatan);

                }else{
                    progressDialogKecamatan.dismiss();
                    Toast.makeText(SignupActivity.this, "Data Kecamatan kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataKecamatan> call, Throwable t) {
                progressDialogKecamatan.dismiss();
                Toast.makeText(SignupActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initSpinnerKota(String id_province) {

        ProgressDialog progressDialogKota = new ProgressDialog(SignupActivity.this);
        progressDialogKota.setMessage("Memuat kota");
        progressDialogKota.show();

        ConfigRetrofit.service.dataKota(id_province).enqueue(new Callback<ResponseDatakota>() {
            @Override
            public void onResponse(Call<ResponseDatakota> call, Response<ResponseDatakota> response) {
                if (response.isSuccessful()){

                    progressDialogKota.dismiss();

                    dataKota = response.body().getResponse();
                    List<String> listSpinnerkota = new ArrayList<String>();

                    for (int i = 0; i<dataKota.size(); i++){
                        listSpinnerkota.add(dataKota.get(i).getCityName());
                    }

                    ArrayAdapter<String> adapterKota = new ArrayAdapter<String>(SignupActivity.this,
                            R.layout.spinner_item, listSpinnerkota);

                    adapterKota.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    binding.spinnerSignupKota.setAdapter(adapterKota);

                }else{
                    progressDialogKota.dismiss();
                    Toast.makeText(SignupActivity.this, "Data Kota Kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDatakota> call, Throwable t) {
                progressDialogKota.dismiss();
                Toast.makeText(SignupActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initKategoriDokter() {

        ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setMessage("Memuat Kategori Dokter");
        progressDialog.show();

        ConfigRetrofit.service.kategoriDokter().enqueue(new Callback<ResponseKategoriDokter>() {
            @Override
            public void onResponse(Call<ResponseKategoriDokter> call, Response<ResponseKategoriDokter> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();

                    dataKategori = response.body().getData();
                    List<String> listSpinnerKategori = new ArrayList<String>();

                    for (int i = 0; i<dataKategori.size(); i++){
                        listSpinnerKategori.add(dataKategori.get(i).getKategori());
                    }

                    ArrayAdapter<String> adapterKecamatan = new ArrayAdapter<String>(SignupActivity.this,
                            R.layout.spinner_item, listSpinnerKategori);

                    adapterKecamatan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    binding.spinnerSignupKategori.setAdapter(adapterKecamatan);

                    initProvinsi();

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(SignupActivity.this, "Data Kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseKategoriDokter> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SignupActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initProvinsi() {

        ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setMessage("Memuat Provinsi");
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

                    ArrayAdapter<String> adapterProvinsi = new ArrayAdapter<String>(SignupActivity.this,
                            R.layout.spinner_item, listSpinnerProvinsi);

                    adapterProvinsi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    binding.spinnerSignupProvinsi.setAdapter(adapterProvinsi);

                }else {
                    progressDialog.dismiss();
                    Toast.makeText(SignupActivity.this, "Data Provinsi Kosong", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseDataProvinsi> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SignupActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}