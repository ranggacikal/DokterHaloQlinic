package com.haloqlinic.dokter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haloqlinic.dokter.R;
import com.haloqlinic.dokter.model.listWithDrawal.DataItem;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.List;

public class RiwayatPenarikanAdapter extends RecyclerView.Adapter<RiwayatPenarikanAdapter.RiwayatPenarikanViewHolder> {

    Context context;
    List<DataItem> dataListDrawal;

    public RiwayatPenarikanAdapter(Context context, List<DataItem> dataListDrawal) {
        this.context = context;
        this.dataListDrawal = dataListDrawal;
    }

    @NonNull
    @NotNull
    @Override
    public RiwayatPenarikanViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_riwayat_penarikan, parent, false);
        return new RiwayatPenarikanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RiwayatPenarikanViewHolder holder, int position) {

        holder.txtKode.setText(dataListDrawal.get(position).getKode());
        holder.txtJumlah.setText("Rp" + NumberFormat.getInstance().format(Integer.parseInt(dataListDrawal.get(position).getJumlah())));

    }

    @Override
    public int getItemCount() {
        return dataListDrawal.size();
    }

    public class RiwayatPenarikanViewHolder extends RecyclerView.ViewHolder {

        TextView txtKode, txtJumlah;

        public RiwayatPenarikanViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtKode = itemView.findViewById(R.id.text_item_kode_penarikan);
            txtJumlah = itemView.findViewById(R.id.text_item_jumlah_penarikan);
        }
    }
}
