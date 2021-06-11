package com.haloqlinic.dokter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.haloqlinic.dokter.R;
import com.haloqlinic.dokter.model.listRecipe.DataItem;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.List;

public class ListObatAdapter extends RecyclerView.Adapter<ListObatAdapter.ListObatViewHolder> {

    Context context;
    List<DataItem> listResep;

    public ListObatAdapter(Context context, List<DataItem> listResep) {
        this.context = context;
        this.listResep = listResep;
    }

    @NonNull
    @NotNull
    @Override
    public ListObatViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resep_obat, parent, false);
        return new ListObatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ListObatViewHolder holder, int position) {

        String url_image = listResep.get(position).getImg();
        int harga = Integer.parseInt(listResep.get(position).getHargaJual());

        Glide.with(context)
                .load(url_image)
                .error(R.mipmap.ic_launcher)
                .into(holder.imgProduk);

        holder.txtNama.setText(listResep.get(position).getNamaProduk());
        holder.txtHarga.setText("Rp" + NumberFormat.getInstance().format(harga));

    }

    @Override
    public int getItemCount() {
        return listResep.size();
    }

    public class ListObatViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProduk;
        TextView txtNama, txtHarga;

        public ListObatViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgProduk = itemView.findViewById(R.id.img_item_resep_obat);
            txtNama = itemView.findViewById(R.id.text_item_nama_resep_obat);
            txtHarga = itemView.findViewById(R.id.text_item_harga_resep_obat);
        }
    }
}
