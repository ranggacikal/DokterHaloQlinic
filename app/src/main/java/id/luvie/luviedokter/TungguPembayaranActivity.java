package id.luvie.luviedokter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import id.luvie.luviedokter.api.ConfigRetrofit;
import id.luvie.luviedokter.model.checkPayment.ResponseCheckPayment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TungguPembayaranActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    // Define the code block to be executed
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            checkPayment();
            // Repeat every 2 seconds
            handler.postDelayed(runnable, 2000);
        }
    };

    String id_transaksi, token, id_customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tunggu_pembayaran);

        id_transaksi = getIntent().getStringExtra("id_transaksi");
        token = getIntent().getStringExtra("token");

        handler.post(runnable);

        checkPayment();
    }

    @Override
    public void onBackPressed() {
        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .setTitle("Anda Yakin ingin keluar dari halaman tunggu pembayaran?")
                .setCancelable(false)
                .setPositiveButton("Iya", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        TungguPembayaranActivity.super.onBackPressed();
                        //BATALKAN PESANAN METHO
                        finish();
                    }
                })
                .setNegativeButton("Tidak", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        // Show Dialog
        mDialog.show();
    }

    private void checkPayment() {

        ConfigRetrofit.service.checkPayment(id_transaksi).enqueue(new Callback<ResponseCheckPayment>() {
            @Override
            public void onResponse(Call<ResponseCheckPayment> call, Response<ResponseCheckPayment> response) {
                if (response.isSuccessful()){

                    String statusPayment = response.body().getStatus();

                    Log.d("checkPayment", "status: "+statusPayment);

                    if (statusPayment.equals("Paid") || statusPayment.equals("paid")){

                        Intent intent = new Intent(TungguPembayaranActivity.this, ChatActivity.class);
                        intent.putExtra("id_transaksi_online", id_transaksi);
                        intent.putExtra("token", token);
                        intent.putExtra("id_customer", id_customer);
                        intent.putExtra("activity", "terima_online");
                        startActivity(intent);
                        finish();

                    }else if (statusPayment.equals("fail") || statusPayment.equals("Fail")){
                        finish();
                    }

                }else{
                    Log.d("checkPayment", "onResponse: Response Fail");
                }
            }

            @Override
            public void onFailure(Call<ResponseCheckPayment> call, Throwable t) {
                Log.d("checkPayment", "onFailure: "+t.getMessage());
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}