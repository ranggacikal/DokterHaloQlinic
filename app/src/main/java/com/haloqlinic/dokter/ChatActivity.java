package com.haloqlinic.dokter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.haloqlinic.dokter.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.dokter.adapter.JadwalKonsultasiAdapter;
import com.haloqlinic.dokter.api.ConfigRetrofit;
import com.haloqlinic.dokter.model.listKonsultasi.DataItem;
import com.haloqlinic.dokter.model.listKonsultasi.ResponseDataKonsultasi;
import com.haloqlinic.dokter.model.notifChat.ResponseNotif;
import com.haloqlinic.dokter.model.updateStatus.ResponseUpdateStatus;
import com.haloqlinic.dokter.model.updateWaktuKonsul.ResponseUpdateWaktuKonsul;
import com.mesibo.api.Mesibo;
import com.mesibo.calls.api.MesiboCall;
import com.mesibo.messaging.MesiboMessagingFragment;
import com.mesibo.messaging.MesiboUI;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class ChatActivity extends AppCompatActivity implements Mesibo.ConnectionListener, Mesibo.MessageListener {


    class DemoUser {
        public String token;
        public String address;

        DemoUser(String token, String address) {
            this.token = token;
            this.address = address;
        }
    }

    DemoUser mUser1;
    DemoUser mUser2;

    DemoUser mRemoteUser;
    Mesibo.UserProfile mProfile;
    Mesibo.ReadDbSession mReadSession;

    View mLoginButton1, mLoginButton2, mSendButton, mUiButton, mAudioCallButton, mVideoCallButton;
    TextView mMessageStatus, mConnStatus, txtNamaPasien;
    CircleImageView imgPasien;
    ImageView imgBack, imgOnline, imgOffline;
    EditText mMessage;
    private SharedPreferencedConfig preferencedConfig;
    String player_id, id_transaksi, id_customer, batas_konsultasi;
    Button btnAkhiriKonsultasi, btnTambahResepObat;
    LinearLayout linearChat;
    FrameLayout frameChat;
    String message;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

//    boolean isLooping = true;
//
//    final Handler handler = new Handler();
//
//    final Runnable runnable = new Runnable() {
//        public void run() {
//
//            getTime();
//            handler.postDelayed(runnable, 1000);
//            Log.d("checkLooping", "run: " + date);
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        preferencedConfig = new SharedPreferencedConfig(ChatActivity.this);

        mUiButton = findViewById(R.id.launchUI);
        mVideoCallButton = findViewById(R.id.videoCall);
        mMessage = findViewById(R.id.message);
        txtNamaPasien = findViewById(R.id.text_nama_pasien_konsultasi);
        imgPasien = findViewById(R.id.img_pasien_konsultasi);
        imgBack = findViewById(R.id.img_back_konsultasi);
        imgOnline = findViewById(R.id.img_online);
        imgOffline = findViewById(R.id.img_offline);
        btnAkhiriKonsultasi = findViewById(R.id.btn_akhiri_konsultasi);
        linearChat = findViewById(R.id.linear_chat);
        frameChat = findViewById(R.id.frame_chat);
        btnTambahResepObat = findViewById(R.id.btn_resep_obat_chat);

        String token = getIntent().getStringExtra("token");
        String nama = getIntent().getStringExtra("nama");
        String url_image = getIntent().getStringExtra("url_image");
        player_id = getIntent().getStringExtra("player_id");
        id_transaksi = getIntent().getStringExtra("id_transaksi");
        id_customer = getIntent().getStringExtra("id_customer");
//        batas_konsultasi = getIntent().getStringExtra("batas_konsultasi");

        loadJadwalKonsultasi();

        Log.d("checkPlayerId", "onCreate: " + player_id);
        Log.d("checkIdTransaksi", "onCreate: " + id_transaksi);

//        if (isLooping = false){
//            handler.removeCallbacks(runnable);
//        }else{
//            handler.post(runnable);
//        }

        txtNamaPasien.setText(nama);

        Glide.with(ChatActivity.this)
                .load(url_image)
                .error(R.drawable.profile)
                .into(imgPasien);

        mVideoCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MesiboCall.getInstance().callUi(ChatActivity.this, mProfile.address, true);
            }
        });

        mUiButton.setEnabled(false);
        mVideoCallButton.setEnabled(false);

        mUser1 = new DemoUser(preferencedConfig.getPreferenceToken(), preferencedConfig.getPreferenceNama());
        mUser2 = new DemoUser(token, nama);

        Log.d("demoUser", "tokenDokter: " + preferencedConfig.getPreferenceToken());
        Log.d("demoUser", "tokenPasien: " + token);

        mesiboInit(mUser1, mUser2);

        mUiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (batas_konsultasi == null || batas_konsultasi.equals("")) {
                    updateWaktuKonsul();
                } else {

                    MesiboUI.launchMessageView(ChatActivity.this, mRemoteUser.address, 0);
                }

//                Intent intent = new Intent(ChatActivity.this, MessagingActivity.class);
//                intent.putExtra("address", mRemoteUser.address);
//                startActivity(intent);

            }
        });

        btnAkhiriKonsultasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tampilDialog();
            }
        });

        PushDownAnim.setPushDownAnimTo(btnTambahResepObat)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ChatActivity.this, TambahResepObatActivity.class);
                        intent.putExtra("id_transaksi", id_transaksi);
                        intent.putExtra("id_customer", id_customer);
                        startActivity(intent);
                    }
                });

        PushDownAnim.setPushDownAnimTo(imgBack)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });


    }

//    private void getTime() {
//
//        calendar = Calendar.getInstance();
//        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        date = dateFormat.format(calendar.getTime());
//
//        if (date.equals("2021-07-07 15:51:00")) {
//
//            isLooping = false;
//            handler.removeCallbacks(runnable);
//            tampilDialogKonsultasiHabis();
//
//        }
//
//    }

//    private void tampilDialogKonsultasiHabis() {
//
//        MaterialDialog mDialog = new MaterialDialog.Builder(this)
//                .setTitle("WAKTU KONSULTASI SUDAH HABIS")
//                .setMessage("Silahkan keluar dari room chat dan akhiri konsultasi")
//                .setCancelable(false)
//                .setPositiveButton("OKE", R.drawable.ic_delete, new MaterialDialog.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int which) {
//                        finish();
//                    }
//                })
//                .setNegativeButton("Cancel", R.drawable.ic_close, new MaterialDialog.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int which) {
//                        dialogInterface.dismiss();
//                    }
//                })
//                .build();
//
//        // Show Dialog
//        mDialog.show();
//
//    }

    private void loadJadwalKonsultasi() {

        ConfigRetrofit.service.dataKonsultasi(preferencedConfig.getPreferenceIdDokter(), "1")
                .enqueue(new Callback<ResponseDataKonsultasi>() {
                    @Override
                    public void onResponse(Call<ResponseDataKonsultasi> call, Response<ResponseDataKonsultasi> response) {
                        if (response.isSuccessful()) {
                            List<DataItem> dataKonsultasi = response.body().getData();

                            for (int a = 0; a < dataKonsultasi.size(); a++) {

                                batas_konsultasi = (String) dataKonsultasi.get(a).getBatasKonsultasi();

                            }

                            Log.d("loadBatasKonsultasi", "onResponse: " + batas_konsultasi);

                        } else {

                            Log.d("loadBatasKonsultasi", "onResponse: gagal memuat batas konsultasi");

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseDataKonsultasi> call, Throwable t) {
                        Log.d("loadBatasKonsultasi", "Error: " + t.getMessage());
                    }
                });

    }

    private void updateWaktuKonsul() {

        ProgressDialog progressDialog = new ProgressDialog(ChatActivity.this);
        progressDialog.setMessage("Membuka Jadwal");
        progressDialog.show();

        ConfigRetrofit.service.updateWaktuKonsul(id_transaksi).enqueue(new Callback<ResponseUpdateWaktuKonsul>() {
            @Override
            public void onResponse(Call<ResponseUpdateWaktuKonsul> call, Response<ResponseUpdateWaktuKonsul> response) {
                if (response.isSuccessful()) {

                    progressDialog.dismiss();
                    Toast.makeText(ChatActivity.this, "Berhasil membuka waktu konsultasi", Toast.LENGTH_SHORT).show();
                    MesiboUI.launchMessageView(ChatActivity.this, mRemoteUser.address, 0);

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ChatActivity.this, "Gagal membuka konsultasi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUpdateWaktuKonsul> call, Throwable t) {
                progressDialog.dismiss();
//                Toast.makeText(ChatActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("failureUpdateWaktu", "onFailure: " + t.getMessage());
            }
        });

    }

    private void tampilDialog() {

        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(this)
                .setTitle("Akhiri Konsultasi?")
                .setMessage("Apakah anda yakin ingin mengakhiri konsultasi?")
                .setCancelable(false)
                .setPositiveButton("Akhiri", new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        updateStatus();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Batal", new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        // Show Dialog
        mBottomSheetDialog.show();

    }

    private void updateStatus() {

        ProgressDialog progressDialog = new ProgressDialog(ChatActivity.this);
        progressDialog.setMessage("Menyelesaikan Konsultasi");
        progressDialog.show();

        ConfigRetrofit.service.updateStatus(id_transaksi).enqueue(new Callback<ResponseUpdateStatus>() {
            @Override
            public void onResponse(Call<ResponseUpdateStatus> call, Response<ResponseUpdateStatus> response) {
                if (response.isSuccessful()) {

                    progressDialog.dismiss();
                    Log.d("checkCode", "onResponse: " + response.code());
                    Toast.makeText(ChatActivity.this, "Berhasil menyelesaikan konsultasi", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ChatActivity.this, "Gagal Menyelesaikan konsultasi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseUpdateStatus> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ChatActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void mesiboInit(DemoUser user, DemoUser remoteUser) {
        Mesibo api = Mesibo.getInstance();
        api.init(getApplicationContext());

        Mesibo.addListener(this);
        Mesibo.setSecureConnection(true);
        Mesibo.setAccessToken(user.token);
        Mesibo.setDatabase("mydb", 0);
        Mesibo.start();

        mRemoteUser = remoteUser;
        mProfile = new Mesibo.UserProfile();
        mProfile.address = remoteUser.address;
        mProfile.name = remoteUser.address;
        Mesibo.setUserProfile(mProfile, false);

        // enable buttons
        mUiButton.setEnabled(true);
        mVideoCallButton.setEnabled(true);

        MesiboCall mesiboCall = MesiboCall.getInstance();
        mesiboCall.init(this);


        // Read receipts are enabled only when App is set to be in foreground
        Mesibo.setAppInForeground(this, 0, true);
        mReadSession = new Mesibo.ReadDbSession(mRemoteUser.address, this);
        mReadSession.enableReadReceipt(true);
        mReadSession.read(100);

    }

    @Override
    public void Mesibo_onConnectionStatus(int i) {
        if (i == 1) {
            imgOnline.setVisibility(View.VISIBLE);
            imgOffline.setVisibility(View.GONE);
        } else {
            imgOffline.setVisibility(View.VISIBLE);
            imgOnline.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean Mesibo_onMessage(Mesibo.MessageParams messageParams, byte[] bytes) {
        try {
            message = new String(bytes, "UTF-8");


        } catch (Exception e) {
        }

        return true;
    }

    private void pushNotification() {

        ConfigRetrofit.service.notifChat(player_id).enqueue(new Callback<ResponseNotif>() {
            @Override
            public void onResponse(Call<ResponseNotif> call, Response<ResponseNotif> response) {
                if (response.isSuccessful()) {

                    Log.d("statusNotifChat", "onResponse: " + "Berhasil Push Notification");

                } else {
                    Log.d("statusNotifChat", "onResponse: " + "Gagal Push Notification");
                }
            }

            @Override
            public void onFailure(Call<ResponseNotif> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void Mesibo_onMessageStatus(Mesibo.MessageParams messageParams) {

        int status = messageParams.getStatus();
        int expiry = messageParams.duration;
        long id = messageParams.mid;
        boolean incoming = messageParams.isIncoming();
        Log.d("cekStatus", "id: " + id);
        Log.d("cekStatus", "expiry: " + expiry);
        Log.d("cekStatus", "incoming: " + incoming);
        Log.d("cekStatus", "message: " + message);
        Log.d("cekStatus", "Mesibo_onMessageStatus: " + status);

        if (status == 1 && id != 0) {
            pushNotification();
        }
    }

    @Override
    public void Mesibo_onActivity(Mesibo.MessageParams messageParams, int i) {

        Log.d("cekMesiboActivity", "Mesibo_onActivity: " + messageParams.peer);

    }

    @Override
    public void Mesibo_onLocation(Mesibo.MessageParams messageParams, Mesibo.Location location) {

    }

    @Override
    public void Mesibo_onFile(Mesibo.MessageParams messageParams, Mesibo.FileInfo fileInfo) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadJadwalKonsultasi();
    }
}