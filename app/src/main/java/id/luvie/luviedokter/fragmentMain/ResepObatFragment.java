package id.luvie.luviedokter.fragmentMain;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import id.luvie.luviedokter.R;
import id.luvie.luviedokter.SharedPreference.SharedPreferencedConfig;
import id.luvie.luviedokter.TarikDanaActivity;
import id.luvie.luviedokter.api.ConfigRetrofit;
import id.luvie.luviedokter.fragmentSaldo.SaldoKonsultasiFragment;
import id.luvie.luviedokter.fragmentSaldo.SaldoProdukFragment;
import id.luvie.luviedokter.model.saldo.DataItem;
import id.luvie.luviedokter.model.saldo.ResponseDataSaldo;
import com.thekhaeng.pushdownanim.PushDownAnim;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

import java.text.NumberFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResepObatFragment extends Fragment {

    public ResepObatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    TabLayout tabLayout;
    private SaldoKonsultasiFragment saldoKonsultasiFragment;
    private SaldoProdukFragment saldoProdukFragment;
    TextView textNamaDokter, txtJumlahPendapatan;
    Button btnTarikDana;

    private SharedPreferencedConfig preferencedConfig;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resep_obat, container, false);

        tabLayout = view.findViewById(R.id.tab_layout_pendapatan);
        textNamaDokter = view.findViewById(R.id.text_nama_dokter_pendapatan);
        preferencedConfig = new SharedPreferencedConfig(getActivity());
        btnTarikDana = view.findViewById(R.id.btn_tarik_dana_pendapatan);
        txtJumlahPendapatan = view.findViewById(R.id.text_jumlah_pendapatan);

        PushDownAnim.setPushDownAnimTo(btnTarikDana)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), TarikDanaActivity.class));
                    }
                });

        textNamaDokter.setText("Dr. "+preferencedConfig.getPreferenceNama());

        bindWidgetsWithAnEvent();
        setupTabLayout();

        return view;
    }

    private void loadSaldoKonsultasi() {

        String id_dokter = preferencedConfig.getPreferenceIdDokter();

        ConfigRetrofit.service.dataSaldo(id_dokter, "0").enqueue(new Callback<ResponseDataSaldo>() {
            @Override
            public void onResponse(Call<ResponseDataSaldo> call, Response<ResponseDataSaldo> response) {
                if (response.isSuccessful()){

                    List<DataItem> dataKonsultasi = response.body().getData();
                    int saldo = Integer.parseInt(response.body().getSaldo());

                    txtJumlahPendapatan.setText("Rp" + NumberFormat.getInstance().format(saldo));

                }else{
                    Toast.makeText(getActivity(), "Data kosong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataSaldo> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setupTabLayout() {

        saldoKonsultasiFragment = new SaldoKonsultasiFragment();
        saldoProdukFragment = new SaldoProdukFragment();

        tabLayout.addTab(tabLayout.newTab().setText("Saldo Konsultasi"));
        tabLayout.addTab(tabLayout.newTab().setText("Saldo Produk"));

    }

    private void bindWidgetsWithAnEvent() {

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentTabFragment(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    private void setCurrentTabFragment(int position) {
        switch (position)
        {
            case 0 :
                replaceFragment(saldoKonsultasiFragment);
                loadSaldoKonsultasi();
                break;
            case 1 :
                replaceFragment(saldoProdukFragment);
                break;
        }

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_pendapatan, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }
}