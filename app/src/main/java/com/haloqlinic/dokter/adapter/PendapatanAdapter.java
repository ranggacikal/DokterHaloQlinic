package com.haloqlinic.dokter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haloqlinic.dokter.R;
import com.haloqlinic.dokter.model.saldo.DataItem;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.List;

public class PendapatanAdapter extends RecyclerView.Adapter<PendapatanAdapter.PendapatanViewHolder> {

    Context context;
    List<DataItem> dataSaldo;

    public PendapatanAdapter(Context context, List<DataItem> dataSaldo) {
        this.context = context;
        this.dataSaldo = dataSaldo;
    }

    @NonNull
    @NotNull
    @Override
    public PendapatanViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pendapatan, parent, false);
        return new PendapatanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PendapatanViewHolder holder, int position) {

        holder.txtIdTransaksi.setText(dataSaldo.get(position).getIdTransaksi());

        String biaya = dataSaldo.get(position).getBiaya();

        if (biaya==null){
            holder.txtJumlah.setText("Rp" + NumberFormat.getInstance().format(Integer.parseInt("0")));
        }else{
            holder.txtJumlah.setText("Rp" + NumberFormat.getInstance().format(Integer.parseInt(biaya)));
        }


        holder.txtJadwal.setText(dataSaldo.get(position).getJadwal());

    }

    @Override
    public int getItemCount() {
        return dataSaldo.size();
    }

    public class PendapatanViewHolder extends RecyclerView.ViewHolder {

        TextView txtIdTransaksi, txtJumlah, txtJadwal;

        public PendapatanViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtIdTransaksi = itemView.findViewById(R.id.text_item_id_transaksi_pendapatan);
            txtJumlah = itemView.findViewById(R.id.text_item_harga_pendapatan);
            txtJadwal = itemView.findViewById(R.id.text_item_jadwal_pendapatan);
        }
    }
}
