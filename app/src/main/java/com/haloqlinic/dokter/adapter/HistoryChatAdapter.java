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
import com.haloqlinic.dokter.DetailKonsultasiActivity;
import com.haloqlinic.dokter.R;
import com.haloqlinic.dokter.SharedPreference.SharedPreferencedConfig;
import com.haloqlinic.dokter.model.listKonsultasi.DataItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class HistoryChatAdapter extends RecyclerView.Adapter<HistoryChatAdapter.HistoryChatViewHolder> {

    Context context;
    List<DataItem> dataKonsultasi;
    private SharedPreferencedConfig preferencedConfig;

    public HistoryChatAdapter(Context context, List<DataItem> dataKonsultasi) {
        this.context = context;
        this.dataKonsultasi = dataKonsultasi;
    }

    @NonNull
    @NotNull
    @Override
    public HistoryChatViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_chat, parent, false);
        return new HistoryChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HistoryChatViewHolder holder, int position) {

        preferencedConfig = new SharedPreferencedConfig(context);

        holder.txtNama.setText(dataKonsultasi.get(position).getNamaPasien());
        holder.txtTanggal.setText(dataKonsultasi.get(position).getJadwal());

        String status_transaksi = dataKonsultasi.get(position).getStatusTransaksi();

        PushDownAnim.setPushDownAnimTo(holder.itemView)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (preferencedConfig.getPreferencePositionFragment().equals("0")) {

                            Intent intent = new Intent(context, DetailKonsultasiActivity.class);
                            String name = String.valueOf(context.getApplicationContext());
                            Log.d("checkNameContext", "onClick: " + name);
                            intent.putExtra("id_transaksi", dataKonsultasi.get(position).getIdTransaksi());
                            intent.putExtra("nama_pasien", dataKonsultasi.get(position).getNamaPasien());
                            intent.putExtra("jadwal", dataKonsultasi.get(position).getJadwal());
                            intent.putExtra("id_konsultasi", dataKonsultasi.get(position).getIdKonsultasi());
                            context.startActivity(intent);
                        }else if (preferencedConfig.getPreferencePositionFragment().equals("1")){

                            if (status_transaksi.equals("1")){

                                Intent intentChat = new Intent(context, ChatActivity.class);
                                intentChat.putExtra("token", dataKonsultasi.get(position).getToken());
                                intentChat.putExtra("nama", dataKonsultasi.get(position).getNamaPasien());
                                intentChat.putExtra("player_id", dataKonsultasi.get(position).getPlayerId());
                                intentChat.putExtra("id_transaksi", dataKonsultasi.get(position).getIdTransaksi());
                                intentChat.putExtra("id_customer", dataKonsultasi.get(position).getIdCustomer());
                                Log.d("id_transaksi", "onClick: "+dataKonsultasi.get(position).getIdTransaksi());
                                Log.d("playerId", "check: "+dataKonsultasi.get(position).getPlayerId());
                                context.startActivity(intentChat);

                            }

                        }

                    }
                });
    }

    @Override
    public int getItemCount() {
        return dataKonsultasi.size();
    }

    public class HistoryChatViewHolder extends RecyclerView.ViewHolder {

        TextView txtNama, txtTanggal;

        public HistoryChatViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.text_item_nama_history_chat);
            txtTanggal = itemView.findViewById(R.id.text_item_tanggal_history_chat);
        }
    }
}
