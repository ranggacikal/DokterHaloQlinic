package id.luvie.luviedokter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import id.luvie.luviedokter.R;

import id.luvie.luviedokter.SharedPreference.SharedPreferencedConfig;
import id.luvie.luviedokter.api.ConfigRetrofit;
import id.luvie.luviedokter.callCustom.CallActivity;
import id.luvie.luviedokter.model.listKonsultasi.DataItem;
import id.luvie.luviedokter.model.listKonsultasi.ResponseDataKonsultasi;
import id.luvie.luviedokter.model.listKonsultasiOnline.ResponseDataKonsultasiOnline;
import id.luvie.luviedokter.model.notifChat.ResponseNotif;
import id.luvie.luviedokter.model.pushNotifResep.ResponsePushNotifResep;
import id.luvie.luviedokter.model.updateStatus.ResponseUpdateStatus;
import id.luvie.luviedokter.model.updateWaktuKonsul.ResponseUpdateWaktuKonsul;

import com.google.android.material.snackbar.Snackbar;
import com.mesibo.api.Mesibo;
import com.mesibo.api.MesiboProfile;
import com.mesibo.calls.api.MesiboCall;
import com.mesibo.calls.api.MesiboCallActivity;
import com.mesibo.calls.api.MesiboVideoView;
import com.mesibo.messaging.MesiboUI;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class ChatActivity extends AppCompatActivity implements Mesibo.ConnectionListener,
        Mesibo.MessageListener, Mesibo.CallListener {


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
        return false;
    }

    MesiboCall.Call mCall = new MesiboCall.Call() {
        @Override
        public void setListener(MesiboCall.InProgressListener inProgressListener) {

        }

        @Override
        public MesiboCall.CallProperties getCallProperties() {
            return null;
        }

        @Override
        public void start(MesiboCallActivity mesiboCallActivity, MesiboCall.InProgressListener inProgressListener) {

        }

        @Override
        public void answer(boolean b) {

        }

        @Override
        public void answer() {

        }

        @Override
        public void sendDTMF(int i) {

        }

        @Override
        public void hangup() {
        }

        @Override
        public void switchCamera() {

        }

        @Override
        public void switchSource() {

        }

        @Override
        public boolean isFrontCamera() {
            return false;
        }

        @Override
        public void changeVideoFormat(int i, int i1, int i2) {

        }

        @Override
        public void setVideoSource(int i, int i1) {

        }

        @Override
        public int getVideoSource() {
            return 0;
        }

        @Override
        public void mute(boolean b, boolean b1, boolean b2) {

        }

        @Override
        public boolean toggleAudioMute() {
            return false;
        }

        @Override
        public boolean toggleVideoMute() {
            return false;
        }

        @Override
        public void setAudioDevice(MesiboCall.AudioDevice audioDevice, boolean b) {

        }

        @Override
        public MesiboCall.AudioDevice getActiveAudioDevice() {
            return null;
        }

        @Override
        public boolean toggleAudioDevice(MesiboCall.AudioDevice audioDevice) {
            return false;
        }

        @Override
        public void setVideoView(MesiboVideoView mesiboVideoView, boolean b) {

        }

        @Override
        public void setVideoViewsSwapped(boolean b) {

        }

        @Override
        public boolean isVideoViewsSwapped() {
            return false;
        }

        @Override
        public MesiboVideoView getVideoView(boolean b) {
            return null;
        }

        @Override
        public void setVideoScaling(MesiboCall.VideoScalingType videoScalingType) {

        }

        @Override
        public MesiboCall.VideoScalingType getVideoScalingType() {
            return null;
        }

        @Override
        public long getAnswerTime() {
            return 0;
        }

        @Override
        public boolean isVideoCall() {
            return false;
        }

        @Override
        public boolean isIncoming() {
            return false;
        }

        @Override
        public boolean isCallInProgress() {
            return false;
        }

        @Override
        public boolean isLinkUp() {
            return false;
        }

        @Override
        public boolean isCallConnected() {
            return false;
        }

        @Override
        public boolean isAnswered() {
            return false;
        }

        @Override
        public boolean getMuteStatus(boolean b, boolean b1, boolean b2) {
            return false;
        }

        @Override
        public void playInCallSound(Context context, int i, boolean b) {

        }

        @Override
        public void stopInCallSound() {

        }
    };

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

    }

    @Override
    public void Mesibo_onLocation(Mesibo.MessageParams messageParams, Mesibo.Location location) {

    }

    @Override
    public void Mesibo_onFile(Mesibo.MessageParams messageParams, Mesibo.FileInfo fileInfo) {

    }

    @Override
    public boolean Mesibo_onCall(long l, long l1, MesiboProfile mesiboProfile, int i) {
        return false;
    }

    @Override
    public boolean Mesibo_onCallStatus(long l, long l1, int i, long l2, long l3, long l4, String s) {
        return false;
    }

    @Override
    public void Mesibo_onCallServer(int i, String s, String s1, String s2) {

    }


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
    String id_transaksi_online, activity, nama, url_image, token;
    int counter = 960;

//    MesiboCall.Call mCall;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    String tokenIntent;

    Toast toast1;
    View layout1;

    int detik = 60;

    boolean buttonChat;
    CountDownTimer cdTimer;


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

//        mCall = MesiboCall.getInstance().getActiveCall();
//        batas_konsultasi = getIntent().getStringExtra("batas_konsultasi");
        activity = getIntent().getStringExtra("activity");

        token = getIntent().getStringExtra("token");

        if (activity.equals("terima_online")) {
            id_transaksi = getIntent().getStringExtra("id_transaksi_online");
            id_customer = getIntent().getStringExtra("id_customer");
            Log.d("id_transaksi_online", "onCreate: " + id_transaksi_online);
            loadDataKonsultasi(id_transaksi);

        } else if (activity.equals("jadwal_konsultasi")) {
            nama = getIntent().getStringExtra("nama");
            url_image = getIntent().getStringExtra("url_image");
            player_id = getIntent().getStringExtra("player_id");
            id_transaksi = getIntent().getStringExtra("id_transaksi");
            id_customer = getIntent().getStringExtra("id_customer");

            txtNamaPasien.setText(nama);

            Glide.with(ChatActivity.this)
                    .load(url_image)
                    .error(R.drawable.icon_user)
                    .into(imgPasien);

            mUser1 = new DemoUser(preferencedConfig.getPreferenceToken(), preferencedConfig.getPreferenceNama());
            mUser2 = new DemoUser(token, nama);

//        mUser1 = new DemoUser("21a938a28e33400f98dcf7f3f58f8309ea8dab7d09e7ebb365b7c",
//                "test1");
//        mUser2 = new DemoUser("7b836d5f84af0dc4d4c1b3aa9f806ca59dce548a663033b052eee365b7f",
//                "test2");

            Log.d("demoUser", "tokenDokter: " + preferencedConfig.getPreferenceToken());
            Log.d("demoUser", "tokenPasien: " + token);
            Log.d("demoUser", "namDokter: " + preferencedConfig.getPreferenceNama());
            Log.d("demoUser", "namaPasien: " + nama);

            mesiboInit(mUser1, mUser2);

        }

        loadJadwalKonsultasi();
        Log.d("checkPlayerId", "onCreate: " + player_id);
        Log.d("checkIdTransaksi", "onCreate: " + id_transaksi);

//        if (isLooping = false){
//            handler.removeCallbacks(runnable);
//        }else{
//            handler.post(runnable);
//        }

        mVideoCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                buttonChat = false;

                if (batas_konsultasi == null || batas_konsultasi.equals("")) {
                    updateWaktuKonsul();

                } else {

                    MesiboCall.getInstance().callUi(ChatActivity.this, mProfile.address, true);
//                    startActivity(new Intent(ChatActivity.this, CallActivity.class));

//                    new CountDownTimer(960000, 1000) {
//                        public void onTick(long millisUntilFinished) {
//                            counter--;
//                            Log.d("checkCounter", "onTick: " + String.valueOf(counter));
//
//                            if(counter<30) {
//
//                                Toast.makeText(ChatActivity.this, "Konsultasi Akan berakhir dalam"+
//                                                String.valueOf(counter), Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//
//                        public void onFinish() {
//
//                            Toast.makeText(ChatActivity.this, "Konsultasi berakhir", Toast.LENGTH_LONG).show();
//
//
//                        }
//                    }.start();

                }
            }
        });

//        mUiButton.setEnabled(false);
//        mVideoCallButton.setEnabled(false);

        mUiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("checkBatasKonsultasi", "onClick: " + batas_konsultasi);

                buttonChat = true;

                if (batas_konsultasi == null || batas_konsultasi.equals("")) {
                    updateWaktuKonsul();

                } else {

                    MesiboUI.launchMessageView(ChatActivity.this, mProfile.address, 0);

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

                        pushNotif();
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

    private void pushNotif() {

        ConfigRetrofit.service.notifResep(id_transaksi).enqueue(new Callback<ResponsePushNotifResep>() {
            @Override
            public void onResponse(Call<ResponsePushNotifResep> call, Response<ResponsePushNotifResep> response) {
                if (response.isSuccessful()) {


                    Intent intent = new Intent(ChatActivity.this, TambahResepObatActivity.class);
                    intent.putExtra("id_transaksi", id_transaksi);
                    intent.putExtra("id_customer", id_customer);
                    startActivity(intent);

                } else {
                    Toast.makeText(ChatActivity.this, "Gagal Push Notif", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePushNotifResep> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadDataKonsultasi(String id_transaksi_online) {

        ProgressDialog progressDialog = new ProgressDialog(ChatActivity.this);
        progressDialog.setMessage("Memuat Data");
        progressDialog.show();

        ConfigRetrofit.service.dataKonsultasiOnline(id_transaksi_online).enqueue(new Callback<ResponseDataKonsultasiOnline>() {
            @Override
            public void onResponse(Call<ResponseDataKonsultasiOnline> call, Response<ResponseDataKonsultasiOnline> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();

                    List<id.luvie.luviedokter.model.listKonsultasiOnline.DataItem> dataKonsultasi =
                            response.body().getData();

                    for (int a = 0; a < dataKonsultasi.size(); a++) {

                        nama = dataKonsultasi.get(a).getNamaPasien();
                        url_image = (String) dataKonsultasi.get(a).getImg();
                        player_id = dataKonsultasi.get(a).getPlayerId();
                        id_transaksi = dataKonsultasi.get(a).getIdTransaksi();
                        id_customer = dataKonsultasi.get(a).getIdCustomer();
                        batas_konsultasi = (String) dataKonsultasi.get(a).getBatasKonsultasi();

                    }

                    Log.d("checkIntentHasil", "token: " + token);
                    Log.d("checkIntentHasil", "img: " + url_image);

                    txtNamaPasien.setText(nama);

                    String link = "https://aplikasicerdas.net/haloqlinic/file/customer/profile/";

                    Glide.with(ChatActivity.this)
                            .load(link + url_image)
                            .error(R.drawable.icon_user)
                            .into(imgPasien);

                    mUser1 = new DemoUser(preferencedConfig.getPreferenceToken(), preferencedConfig.getPreferenceNama());
                    mUser2 = new DemoUser(token, nama);

//        mUser1 = new DemoUser("21a938a28e33400f98dcf7f3f58f8309ea8dab7d09e7ebb365b7c",
//                "test1");
//        mUser2 = new DemoUser("7b836d5f84af0dc4d4c1b3aa9f806ca59dce548a663033b052eee365b7f",
//                "test2");

                    Log.d("demoUser", "tokenDokter: " + preferencedConfig.getPreferenceToken());
                    Log.d("demoUser", "tokenPasien: " + token);

                    mesiboInit(mUser1, mUser2);


                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ChatActivity.this, "Gagal Memuat Data",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataKonsultasiOnline> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ChatActivity.this, "Error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
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
//                    loadDataKonsultasi(id_transaksi_online);
                    long duration = TimeUnit.MINUTES.toMillis(1);

                    cdTimer = new CountDownTimer(duration, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            String sDuration = String.format(Locale.ENGLISH, "%02d : %02d",
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                            Log.d("cekSduration", "onTick: " + TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished));


                            detik = (int) TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);

                            LayoutInflater inflater = getLayoutInflater();
                            toast1 = new Toast(getApplicationContext());
                            layout1 = inflater.inflate(R.layout.custom_toast, findViewById(R.id.linear_custom_toast));
                            TextView txtToast = layout1.findViewById(R.id.text_toast_waktu);
                            txtToast.setText(sDuration);
                            toast1.setView(layout1);
                            toast1.setGravity(Gravity.TOP | Gravity.RIGHT, 10, 0);
                            toast1.setDuration(Toast.LENGTH_SHORT);
                            toast1.show();

//                            if (detik == 180) {
//                                LayoutInflater inflater = getLayoutInflater();
//                                View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.linear_custom_toast));
//                                TextView txtToast = layout.findViewById(R.id.text_toast_waktu);
//                                txtToast.setText(sDuration);
//                                toast = new Toast(getApplicationContext());
//                                toast.setGravity(Gravity.TOP | Gravity.RIGHT, 10, 0);
//                                toast.setDuration(Toast.LENGTH_LONG);
//                                toast.setView(layout);
//                                toast.show();
//                            } else if (detik < 61 && detik >= 0) {
//                                LayoutInflater inflater = getLayoutInflater();
//                                View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.linear_custom_toast));
//                                TextView txtToast = layout.findViewById(R.id.text_toast_waktu);
//                                txtToast.setText(sDuration);
//                                toast = new Toast(getApplicationContext());
//                                toast.setGravity(Gravity.TOP | Gravity.RIGHT, 10, 0);
//                                toast.setDuration(Toast.LENGTH_SHORT);
//                                toast.setView(layout);
//                                toast.show();
//                            }

                        }

                        @Override
                        public void onFinish() {

                            if (buttonChat == false) {

                                MesiboCall.Call mCall2 = MesiboCall.getInstance().getActiveCall();
                                if (mCall2 == null) {
                                    //There is no active call
                                    //We can make an outgoing call

                                    //Create a CallProperties object
                                    MesiboCall.CallProperties cp = MesiboCall.getInstance().createCallProperties(true);

                                    // Call Factory method to create a call object
                                    mCall2 = MesiboCall.getInstance().call(cp);
                                    if (mCall2 == null) {
                                        //Error
                                    }
                                }
                                mCall2.hangup();
                                updateStatus();
                                Toast.makeText(ChatActivity.this, "Silahkan Akhiri Konsultasi", Toast.LENGTH_LONG).show();

                            } else {
//                            int tutup = MesiboCall.MESIBOCALL_HANGUP_REASON_USER;
//                            mCall2.hangup();
                                updateStatus();

                            }
                        }
                    }.start();


                    if (buttonChat == true) {
                        MesiboUI.launchMessageView(ChatActivity.this, mProfile.address, 0);
                    } else {

                        MesiboCall.getInstance().callUi(ChatActivity.this, mProfile.address, true);
                    }
//                    startActivity(new Intent(ChatActivity.this, CallActivity.class));

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
//                    toast.cancel();

                    if (cdTimer!=null) {
                        cdTimer.cancel();
                    }
                    if (toast1!=null) {
                        toast1.cancel();
                    }
//                    cdTimer.cancel();
                    startActivity(new Intent(ChatActivity.this, MainActivity.class));
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
//        mProfile.name = remoteUser.address;
//        Mesibo.setUserProfile(mProfile, false);

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

//    @Override
//    public void Mesibo_onConnectionStatus(int i) {
//        if (i == 1) {
//            imgOnline.setVisibility(View.VISIBLE);
//            imgOffline.setVisibility(View.GONE);
//        } else {
//            imgOffline.setVisibility(View.VISIBLE);
//            imgOnline.setVisibility(View.GONE);
//        }
//    }
//
//    @Override
//    public boolean Mesibo_onMessage(Mesibo.MessageParams messageParams, byte[] bytes) {
//        try {
//            message = new String(bytes, "UTF-8");
//
//
//        } catch (Exception e) {
//        }
//
//        return true;
//    }

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

//    @Override
//    public void Mesibo_onMessageStatus(Mesibo.MessageParams messageParams) {
//
//        int status = messageParams.getStatus();
//        int expiry = messageParams.duration;
//        long id = messageParams.mid;
//        boolean incoming = messageParams.isIncoming();
//        Log.d("cekStatus", "id: " + id);
//        Log.d("cekStatus", "expiry: " + expiry);
//        Log.d("cekStatus", "incoming: " + incoming);
//        Log.d("cekStatus", "message: " + message);
//        Log.d("cekStatus", "Mesibo_onMessageStatus: " + status);
//
//        if (status == 1 && id != 0) {
//            pushNotification();
//        }
//    }

//    @Override
//    public void Mesibo_onActivity(Mesibo.MessageParams messageParams, int i) {
//
//        Log.d("cekMesiboActivity", "Mesibo_onActivity: " + messageParams.peer);
//
//    }
//
//    @Override
//    public void Mesibo_onLocation(Mesibo.MessageParams messageParams, Mesibo.Location location) {
//
//    }
//
//    @Override
//    public void Mesibo_onFile(Mesibo.MessageParams messageParams, Mesibo.FileInfo fileInfo) {
//
//    }


    @Override
    protected void onResume() {
        super.onResume();
        loadJadwalKonsultasi();
    }
}