package com.haloqlinic.dokter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haloqlinic.dokter.SharedPreference.SharedPreferencedConfig;
import com.mesibo.api.Mesibo;
import com.mesibo.calls.api.MesiboCall;
import com.mesibo.messaging.MesiboUI;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity implements Mesibo.ConnectionListener, Mesibo.MessageListener{


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

        String token = getIntent().getStringExtra("token");
        String nama = getIntent().getStringExtra("nama");

        txtNamaPasien.setText(nama);

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

        Log.d("demoUser", "tokenDokter: "+preferencedConfig.getPreferenceToken());
        Log.d("demoUser", "tokenPasien: "+token);

        mesiboInit(mUser1, mUser2);

        mUiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MesiboUI.launchMessageView(ChatActivity.this, mRemoteUser.address, 0);
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
        if (i == 1){
            imgOnline.setVisibility(View.VISIBLE);
            imgOffline.setVisibility(View.GONE);
        }else{
            imgOffline.setVisibility(View.VISIBLE);
            imgOnline.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean Mesibo_onMessage(Mesibo.MessageParams messageParams, byte[] bytes) {
        try {
            String message = new String(bytes, "UTF-8");

        } catch (Exception e) {
        }

        return true;
    }

    @Override
    public void Mesibo_onMessageStatus(Mesibo.MessageParams messageParams) {

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


}