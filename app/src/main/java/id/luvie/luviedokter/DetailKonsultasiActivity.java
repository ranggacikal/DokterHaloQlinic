package id.luvie.luviedokter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import id.luvie.luviedokter.api.ConfigRetrofit;

import id.luvie.luviedokter.databinding.ActivityDetailKonsultasiBinding;
import id.luvie.luviedokter.model.updateKonsultasi.ResponseUpdateKonsultasi;
import com.thekhaeng.pushdownanim.PushDownAnim;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class DetailKonsultasiActivity extends AppCompatActivity {

    private ActivityDetailKonsultasiBinding binding;

    String token;

    String id_customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailKonsultasiBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        String nama_pasien = getIntent().getStringExtra("nama_pasien");
        String jadwal = getIntent().getStringExtra("jadwal");
        token = getIntent().getStringExtra("token");
        id_customer = getIntent().getStringExtra("id_customer");

        binding.textNamaPasienDetailKonsultasi.setText(nama_pasien);
        binding.textJadwalDetailKonsultasi.setText(jadwal);

        PushDownAnim.setPushDownAnimTo(binding.imgBackDetailKonsultasi)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnTolakPermintaan)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tampilDialogTolak();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnTerimaPermintaan)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tampilDialogTerima();
                    }
                });

    }

    private void tampilDialogTerima() {

        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(this)
                .setTitle("Terima Permintaan?")
                .setMessage("Apakah anda yakin ingin menerima permintaan konsultasi dengan anda?")
                .setCancelable(false)
                .setPositiveButton("Terima",  new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        terimaPermintaan();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Batal",  new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        // Show Dialog
        mBottomSheetDialog.show();

    }

    private void terimaPermintaan() {

        String id_transaksi = getIntent().getStringExtra("id_transaksi");

        ProgressDialog progressDialogTerima = new ProgressDialog(DetailKonsultasiActivity.this);
        progressDialogTerima.setMessage("Menerima Permintaan");
        progressDialogTerima.show();

        ConfigRetrofit.service.updateKonsultasu(id_transaksi, "1").enqueue(new Callback<ResponseUpdateKonsultasi>() {
            @Override
            public void onResponse(Call<ResponseUpdateKonsultasi> call, Response<ResponseUpdateKonsultasi> response) {
                if (response.isSuccessful()){

                    progressDialogTerima.dismiss();

                    String responseHasil = response.body().getResponse();
//                    String id_transaksi = response.body().getIdTransaksi();
                    Log.d("cekIdTransaksi", "onResponse: "+id_transaksi);

                    if (responseHasil.equals("Data Berhasil Dikirim")) {
                        Toast.makeText(DetailKonsultasiActivity.this, "Menerima permintaan berhasil", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(DetailKonsultasiActivity.this, ChatActivity.class);
//                        intent.putExtra("id_transaksi_online", id_transaksi);
//                        intent.putExtra("token", token);
//                        intent.putExtra("activity", "terima_online");
                        Intent intent = new Intent(DetailKonsultasiActivity.this, TungguPembayaranActivity.class);
                        intent.putExtra("id_transaksi", id_transaksi);
                        intent.putExtra("token", token);
                        intent.putExtra("id_customer", id_customer);
                        startActivity(intent);
                        finish();

                    }
                }else{
                    progressDialogTerima.dismiss();
                    Toast.makeText(DetailKonsultasiActivity.this, "Gagal menerima permintaan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUpdateKonsultasi> call, Throwable t) {
                progressDialogTerima.dismiss();
                Toast.makeText(DetailKonsultasiActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void tampilDialogTolak() {

        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(this)
                .setTitle("Tolak Permintaan?")
                .setMessage("Apakah anda yakin ingin menolak permintaan konsultasi dengan anda?")
                .setCancelable(false)
                .setPositiveButton("Tolak",  new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        tolakPermintaan();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Batal",  new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        // Show Dialog
        mBottomSheetDialog.show();

    }

    private void tolakPermintaan() {

        String id_transaksi = getIntent().getStringExtra("id_transaksi");

        ProgressDialog progressDialog = new ProgressDialog(DetailKonsultasiActivity.this);
        progressDialog.setMessage("Menolak permintaan");
        progressDialog.show();

        ConfigRetrofit.service.updateKonsultasu(id_transaksi, "2").enqueue(new Callback<ResponseUpdateKonsultasi>() {
            @Override
            public void onResponse(Call<ResponseUpdateKonsultasi> call, Response<ResponseUpdateKonsultasi> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(DetailKonsultasiActivity.this, "Berhasil Menolak permintaan", Toast.LENGTH_SHORT).show();
                    finish();

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(DetailKonsultasiActivity.this, "Gagal menolak permintaan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUpdateKonsultasi> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DetailKonsultasiActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}