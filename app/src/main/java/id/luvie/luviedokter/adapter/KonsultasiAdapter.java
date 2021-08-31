package id.luvie.luviedokter.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import id.luvie.luviedokter.DetailKonsultasiActivity;
import id.luvie.luviedokter.KonsultasiActivity;
import id.luvie.luviedokter.R;
import id.luvie.luviedokter.model.listKonsultasi.DataItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class KonsultasiAdapter extends RecyclerView.Adapter<KonsultasiAdapter.KonsultasiViewHolder> {

    Context context;
    List<DataItem> dataKonsultasi;

    public KonsultasiAdapter(Context context, List<DataItem> dataKonsultasi) {
        this.context = context;
        this.dataKonsultasi = dataKonsultasi;
    }

    @NonNull
    @NotNull
    @Override
    public KonsultasiViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_chat, parent, false);
        return new KonsultasiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull KonsultasiViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txtNama.setText(dataKonsultasi.get(position).getNamaPasien());
        holder.txtTanggal.setText(dataKonsultasi.get(position).getJadwal());

        String status_transaksi = dataKonsultasi.get(position).getStatusTransaksi();

        PushDownAnim.setPushDownAnimTo(holder.itemView)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DetailKonsultasiActivity.class);
                        String name = String.valueOf(context.getApplicationContext());
                        Log.d("checkNameContext", "onClick: " + name);
                        intent.putExtra("id_transaksi", dataKonsultasi.get(position).getIdTransaksi());
                        intent.putExtra("nama_pasien", dataKonsultasi.get(position).getNamaPasien());
                        intent.putExtra("id_customer", dataKonsultasi.get(position).getIdCustomer());
                        intent.putExtra("jadwal", dataKonsultasi.get(position).getJadwal());
                        intent.putExtra("token", (String) dataKonsultasi.get(position).getToken());
                        intent.putExtra("id_konsultasi", dataKonsultasi.get(position).getIdKonsultasi());
                        context.startActivity(intent);
                        ((KonsultasiActivity)context).finish();
                    }
                });

    }

    @Override
    public int getItemCount() {
        return dataKonsultasi.size();
    }

    public class KonsultasiViewHolder extends RecyclerView.ViewHolder {
        TextView txtNama, txtTanggal;
        public KonsultasiViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.text_item_nama_history_chat);
            txtTanggal = itemView.findViewById(R.id.text_item_tanggal_history_chat);
        }
    }
}
