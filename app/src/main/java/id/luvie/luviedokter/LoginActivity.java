package id.luvie.luviedokter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import id.luvie.luviedokter.SharedPreference.SharedPreferencedConfig;
import id.luvie.luviedokter.api.ConfigRetrofit;
import id.luvie.luviedokter.model.login.ResponseItem;
import id.luvie.luviedokter.model.login.ResponseLogin;
import id.luvie.luviedokter.model.resetPassword.ResponseResetPassword;
import com.onesignal.OSDeviceState;
import com.onesignal.OneSignal;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    TextView txtLupaPassword;
    Button btnSignIn, btnSignup;
    CheckBox cbKebijakan;
    TextView txtKebijakan;
    ImageView showPassBtn;
    View dialogLupaPassword;
    String token, token_from, user_id, user_id_from;

    private SharedPreferencedConfig preferencedConfig;
    private static final String ONESIGNAL_APP_ID = "e0a7b99b-6b25-4557-8e4a-b276c9ab8d3e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferencedConfig = new SharedPreferencedConfig(this);

        edtEmail = findViewById(R.id.edt_login_email);
        edtPassword = findViewById(R.id.edt_login_password);
        txtLupaPassword = findViewById(R.id.text_lupa_password);
        btnSignIn = findViewById(R.id.btn_signin);
        btnSignup = findViewById(R.id.btn_signup_login);
        showPassBtn = findViewById(R.id.img_hide_password_login);
        cbKebijakan = findViewById(R.id.cb_kebijakan_privasi);
        txtKebijakan = findViewById(R.id.text_kebijakan_privasi);

        SpannableString content = new SpannableString("Kebijakan Privasi");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        txtKebijakan.setText(content);

        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

        OSDeviceState device = OneSignal.getDeviceState();

        token = device.getPushToken();
        user_id = device.getUserId();

        Log.d("user_id_dokter", "onCreate: "+user_id);

        PushDownAnim.setPushDownAnimTo(txtLupaPassword)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tampilDialog();
                    }
                });

        PushDownAnim.setPushDownAnimTo(btnSignup)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(txtKebijakan)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LoginActivity.this, KebijakanPrivasiActivity.class);
                        intent.putExtra("url", "file:///android_asset/kebijakan_privasi.html");
                        startActivity(intent);
                    }
                });

        if (preferencedConfig.getPreferenceIsLogin()){

            Intent intentIsLogin = new Intent(getApplicationContext(), MainActivity.class);
            intentIsLogin.putExtra("tab", 0);
            startActivity(intentIsLogin);
            finish();

        }

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        showHidePass(showPassBtn);
    }

    private void tampilDialog() {

        Dialog dialog = new Dialog(LoginActivity.this);

        dialog.setContentView(R.layout.dialog_lupa_password);
        dialog.setCancelable(false);

        EditText edtEmail = dialog.findViewById(R.id.edt_email_lupa_password);
        Button btnReset = dialog.findViewById(R.id.btn_reset_password);
        Button btnBatal = dialog.findViewById(R.id.btn_batal_reset_password);

        dialog.show();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword(edtEmail, dialog);
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void resetPassword(EditText edtEmail, Dialog dialog) {

        String email = edtEmail.getText().toString();

        if (email.isEmpty()){

            edtEmail.setError("Email tidak boleh kosong");
            edtEmail.requestFocus();
            return;
        }

        ProgressDialog progressDialogReset = new ProgressDialog(LoginActivity.this);

        progressDialogReset.setMessage("Reset Password");
        progressDialogReset.show();

        ConfigRetrofit.service.resetPassword(email).enqueue(new Callback<ResponseResetPassword>() {
            @Override
            public void onResponse(Call<ResponseResetPassword> call, Response<ResponseResetPassword> response) {
                if (response.isSuccessful()){

                    dialog.dismiss();
                    progressDialogReset.dismiss();
                    Toast.makeText(LoginActivity.this, "Silahkan cek email anda", Toast.LENGTH_SHORT).show();


                }else{
                    progressDialogReset.dismiss();
                    Toast.makeText(LoginActivity.this, "Gagal reset password, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseResetPassword> call, Throwable t) {
                progressDialogReset.dismiss();
                Toast.makeText(LoginActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showHidePass(View view){

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(view.getId()==R.id.img_hide_password_login){

                    if(edtPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                        ((ImageView)(view)).setImageResource(R.drawable.icon_awesome_eye);
                        //Show Password
                        edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                    else{
                        ((ImageView)(view)).setImageResource(R.drawable.icon_awesome_eye_slash);

                        //Hide Password
                        edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    }
                }

            }
        });

    }

    private void signIn() {

        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        if (email.isEmpty()){
            edtEmail.setError("email tidak boleh kosong");
            edtEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError("Masukan Alamat email yang valid");
            edtEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            edtPassword.setError("Password tidak boleh kosong");
            edtPassword.requestFocus();
            return;
        }

        if (!cbKebijakan.isChecked()){
            Toast.makeText(this, "Anda Belum menyetujui Kebijakan privasi", Toast.LENGTH_SHORT).show();
            return;
        }

        android.app.ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Login");
        progressDialog.show();

        ConfigRetrofit.service.login(email, password, user_id).enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    Log.d("user_id_dokter", "buttonLogin: "+user_id);

                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    String id_dokter = "";
                    String uid = "";
                    String token = "";
                    String player_id = "";
                    String id_kategori = "";
                    String kode = "";
                    String email = "";
                    String nama = "";
                    String alamat = "";
                    String no_hp = "";
                    String id_kecamatan = "";
                    String id_kota = "";
                    String id_provinsi = "";
                    String sip = "";
                    String spesialis = "";
                    String jk = "";
                    String tentang = "";
                    String img = "";
                    String status = "";
                    String nama_provinsi = "";
                    String nama_kota = "";
                    String nama_kecamatan = "";

                    List<ResponseItem> dataLogin = response.body().getResponse();

                    for (int a = 0; a<dataLogin.size(); a++){

                        id_dokter = dataLogin.get(a).getIdDokter();
                        uid = (String) dataLogin.get(a).getUid();
                        token = (String) dataLogin.get(a).getToken();
                        player_id = (String) dataLogin.get(a).getPlayerId();
                        id_kategori = dataLogin.get(a).getIdKategori();
                        kode = dataLogin.get(a).getKode();
                        email = dataLogin.get(a).getEmail();
                        nama = dataLogin.get(a).getNama();
                        alamat = (String) dataLogin.get(a).getAlamat();
                        no_hp = (String) dataLogin.get(a).getNoHp();
                        id_kecamatan = dataLogin.get(a).getKecamatan();
                        id_kota = dataLogin.get(a).getKota();
                        id_provinsi = dataLogin.get(a).getProvinsi();
                        sip = dataLogin.get(a).getSip();
                        spesialis = dataLogin.get(a).getSpesialis();
                        jk = dataLogin.get(a).getJk();
                        tentang = (String) dataLogin.get(a).getTentang();
                        img = dataLogin.get(a).getImg();
                        status = dataLogin.get(a).getStatus();
                        nama_provinsi = dataLogin.get(a).getNamaProvinsi();
                        nama_kota = dataLogin.get(a).getNamaKota();
                        nama_kecamatan = dataLogin.get(a).getNamaKecamatan();

                    }

                    Log.d("checkToken", "onResponse: "+token);

                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_ID_DOKTER, id_dokter);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_UID, uid);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_TOKEN, token);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_PLAYER_ID, player_id);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_ID_KATEGORI, id_kategori);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_KODE, kode);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_EMAIL, email);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_NAMA, nama);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_ALAMAT, alamat);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_NO_HP, no_hp);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_ID_KECAMATAN, id_kecamatan);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_ID_KOTA, id_kota);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_ID_PROVINSI, id_provinsi);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_SIP, sip);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_SPESIALIS, spesialis);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_JK, jk);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_TENTANG, tentang);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_IMG, img);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_STATUS, status);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_PROVINSI, nama_provinsi);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_KOTA, nama_kota);
                    preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_KECAMATAN, nama_kecamatan);

                    Log.d("checkToken", "user_id: "+user_id);
                    Log.d("checkToken", "player_id: "+player_id);

                    preferencedConfig.savePrefBoolean(SharedPreferencedConfig.PREFERENCE_IS_LOGIN, true);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Email / Password Salah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}