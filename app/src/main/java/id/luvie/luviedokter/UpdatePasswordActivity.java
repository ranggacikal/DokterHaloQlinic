package id.luvie.luviedokter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import id.luvie.luviedokter.R;

import id.luvie.luviedokter.SharedPreference.SharedPreferencedConfig;
import id.luvie.luviedokter.api.ConfigRetrofit;

import id.luvie.luviedokter.databinding.ActivityUpdatePasswordBinding;
import id.luvie.luviedokter.model.updatePassword.ResponseUpdatePassword;
import com.thekhaeng.pushdownanim.PushDownAnim;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class UpdatePasswordActivity extends AppCompatActivity {

    private ActivityUpdatePasswordBinding binding;
    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdatePasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        preferencedConfig = new SharedPreferencedConfig(this);

        PushDownAnim.setPushDownAnimTo(binding.imgBackUpdatePassword)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        PushDownAnim.setPushDownAnimTo(binding.btnUpdatePassword)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tampilDialog();
                    }
                });

        showHidePass1(binding.imgHideUpdatePassword1);
        showHidePass2(binding.imgHideUpdatePassword2);

        checkPassword();
    }

    private void tampilDialog() {

        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(this)
                .setTitle("Ubah Password?")
                .setMessage("Apakah anda yakin ingin mengubah password?")
                .setCancelable(false)
                .setPositiveButton("Ubah", R.drawable.ic_edit, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        updatePassword();
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

    private void checkPassword() {

        binding.edtUpdatePassword2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pass1 = binding.edtUpdatePassword1.getText().toString();
                String pass2 = binding.edtUpdatePassword2.getText().toString();

                if (pass2.equals(pass1)){
                    binding.textPasswordSesuai.setVisibility(View.VISIBLE);
                    binding.textPasswordTidakSesuai.setVisibility(View.GONE);
                }else{
                    binding.textPasswordTidakSesuai.setVisibility(View.VISIBLE);
                    binding.textPasswordSesuai.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void updatePassword() {

        String password1 = binding.edtUpdatePassword1.getText().toString();
        String password2 = binding.edtUpdatePassword2.getText().toString();

        if (password1.isEmpty()){

            binding.edtUpdatePassword1.setError("Field tidak boleh kosong");
            binding.edtUpdatePassword1.requestFocus();
            return;

        }

        if (password2.isEmpty()){
            binding.edtUpdatePassword2.setError("Field tidak boleh kosong");
            binding.edtUpdatePassword2.requestFocus();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(UpdatePasswordActivity.this);
        progressDialog.setMessage("Mengupdate password");
        progressDialog.show();

        ConfigRetrofit.service.updatePassword(preferencedConfig.getPreferenceIdDokter(), password1, password2)
                .enqueue(new Callback<ResponseUpdatePassword>() {
                    @Override
                    public void onResponse(Call<ResponseUpdatePassword> call, Response<ResponseUpdatePassword> response) {
                        if (response.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(UpdatePasswordActivity.this, "Update password berhasil", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(UpdatePasswordActivity.this, "Update password gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseUpdatePassword> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(UpdatePasswordActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void showHidePass1(View view){

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(view.getId()==R.id.img_hide_update_password1){

                    if(binding.edtUpdatePassword1.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                        ((ImageView)(view)).setImageResource(R.drawable.icon_awesome_eye);
                        //Show Password
                        binding.edtUpdatePassword1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                    else{
                        ((ImageView)(view)).setImageResource(R.drawable.icon_awesome_eye_slash);

                        //Hide Password
                        binding.edtUpdatePassword1.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    }
                }

            }
        });

    }

    private void showHidePass2(View view){

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(view.getId()==R.id.img_hide_update_password2){

                    if(binding.edtUpdatePassword2.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                        ((ImageView)(view)).setImageResource(R.drawable.icon_awesome_eye);
                        //Show Password
                        binding.edtUpdatePassword2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                    else{
                        ((ImageView)(view)).setImageResource(R.drawable.icon_awesome_eye_slash);

                        //Hide Password
                        binding.edtUpdatePassword2.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    }
                }

            }
        });

    }

}