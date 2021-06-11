package com.haloqlinic.dokter.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haloqlinic.dokter.ChatActivity;
import com.haloqlinic.dokter.R;
import com.haloqlinic.dokter.model.listKonsultasi.DataItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class JadwalKonsultasiAdapter extends RecyclerView.Adapter<JadwalKonsultasiAdapter.JadwalKonsultasiViewHolder> {

    Context context;
    List<DataItem> dataKonsultasi;

    public JadwalKonsultasiAdapter(Context context, List<DataItem> dataKonsultasi) {
        this.context = context;
        this.dataKonsultasi = dataKonsultasi;
    }

    @NonNull
    @NotNull
    @Override
    public JadwalKonsultasiViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jadwal_konsultasi, parent, false);
        return new JadwalKonsultasiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull JadwalKonsultasiViewHolder holder, int position) {

        holder.txtNamaDokter.setText(dataKonsultasi.get(position).getNamaPasien());
        holder.txtWaktu.setText(dataKonsultasi.get(position).getJadwal());

        PushDownAnim.setPushDownAnimTo(holder.itemView)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentChat = new Intent(context, ChatActivity.class);
                        intentChat.putExtra("token", dataKonsultasi.get(position).getToken());
                        intentChat.putExtra("nama", dataKonsultasi.get(position).getNamaPasien());
                        intentChat.putExtra("player_id", dataKonsultasi.get(position).getPlayerId());
                        intentChat.putExtra("id_transaksi", dataKonsultasi.get(position).getIdTransaksi());
                        intentChat.putExtra("id_customer", dataKonsultasi.get(position).getIdCustomer());
                        Log.d("id_transaksi", "onClick: "+dataKonsultasi.get(position).getIdTransaksi());
                        context.startActivity(intentChat);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return dataKonsultasi.size();
    }

    public class JadwalKonsultasiViewHolder extends RecyclerView.ViewHolder {

        TextView txtNamaDokter, txtWaktu;

        public JadwalKonsultasiViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtNamaDokter = itemView.findViewById(R.id.text_item_nama_jadwal_konsultasi);
            txtWaktu = itemView.findViewById(R.id.text_item_tanggal_jadwal_konsultasi);
        }
    }
}
