package id.luvie.luviedokter.fragmentMain;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import id.luvie.luviedokter.EditAkunActivity;
import id.luvie.luviedokter.LoginActivity;
import id.luvie.luviedokter.R;
import id.luvie.luviedokter.SharedPreference.SharedPreferencedConfig;
import id.luvie.luviedokter.UlasanActivity;
import id.luvie.luviedokter.UpdatePasswordActivity;
import com.thekhaeng.pushdownanim.PushDownAnim;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private SharedPreferencedConfig preferencedConfig;

    CircleImageView imgDokter;
    TextView txtNamaDokter, txtSpesialis;
    CardView cardEditAKun, cardUpdatePassword, cardBantuan, cardNilaiKami, cardKeluar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        preferencedConfig = new SharedPreferencedConfig(getActivity());

        imgDokter = rootView.findViewById(R.id.img_dokter_profile);
        txtNamaDokter = rootView.findViewById(R.id.text_nama_dokter_profile);
        txtSpesialis = rootView.findViewById(R.id.text_spesialis_dokter_profile);
        cardEditAKun = rootView.findViewById(R.id.card_edit_akun_profile);
        cardUpdatePassword = rootView.findViewById(R.id.card_update_password_profile);
        cardBantuan = rootView.findViewById(R.id.card_bantuan_profile);
        cardNilaiKami = rootView.findViewById(R.id.card_nilai_kami_profile);
        cardKeluar = rootView.findViewById(R.id.card_keluar_profile);

        initGambar();

        if (preferencedConfig.getPreferenceIdKategori().equals("4")){
            txtNamaDokter.setText(preferencedConfig.getPreferenceNama());
            txtSpesialis.setText("");
        }else {
            txtNamaDokter.setText("Dr. "+preferencedConfig.getPreferenceNama());
            txtSpesialis.setText("Spesialis "+preferencedConfig.getPreferenceSpesialis());
        }

        PushDownAnim.setPushDownAnimTo(cardEditAKun)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), EditAkunActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(cardUpdatePassword)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), UpdatePasswordActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(cardBantuan)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

        PushDownAnim.setPushDownAnimTo(cardNilaiKami)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getContext(), UlasanActivity.class));
                    }
                });

        PushDownAnim.setPushDownAnimTo(cardKeluar)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tampilDialog();
                    }
                });



        return rootView;
    }

    private void initGambar() {

        final String url_image = "https://aplikasicerdas.net/haloqlinic/file/dokter/profile/";
        String image_dokter = preferencedConfig.getPreferenceImg();

        Glide.with(getActivity())
                .load(url_image+image_dokter)
                .error(R.drawable.icon_user)
                .into(imgDokter);

    }

    private void tampilDialog() {

        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(getActivity())
                .setTitle("Keluar Akun?")
                .setMessage("Apakah anda yakin ingin keluar dari akun anda?")
                .setCancelable(false)
                .setPositiveButton("Iya",  new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        keluar();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Tidak",  new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        // Show Dialog
        mBottomSheetDialog.show();

    }

    private void keluar() {

        Toast.makeText(getActivity(), "Keluar akun", Toast.LENGTH_SHORT).show();
        preferencedConfig.savePrefBoolean(SharedPreferencedConfig.PREFERENCE_IS_LOGIN, false);
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();

    }

    @Override
    public void onResume() {
        super.onResume();
        initGambar();
    }
}