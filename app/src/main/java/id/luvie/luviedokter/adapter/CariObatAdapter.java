package id.luvie.luviedokter.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import id.luvie.luviedokter.CariResepActivity;
import id.luvie.luviedokter.DetailProdukActivity;
import id.luvie.luviedokter.R;

import id.luvie.luviedokter.model.cariProduk.DataItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.List;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class CariObatAdapter extends RecyclerView.Adapter<CariObatAdapter.CariObatViewHolder> {

    Context context;
    List<DataItem> dataProduk;
    CariResepActivity cariResepActivity;

    public CariObatAdapter(Context context, List<DataItem> dataProduk, CariResepActivity cariResepActivity) {
        this.context = context;
        this.dataProduk = dataProduk;
        this.cariResepActivity = cariResepActivity;
    }

    @NonNull
    @NotNull
    @Override
    public CariObatViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resep_obat, parent,false);
        return new CariObatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CariObatViewHolder holder, int position) {


        String img = dataProduk.get(position).getImg();
        String nama_produk = dataProduk.get(position).getNamaProduk();
        String harga = dataProduk.get(position).getHargaJual();

        holder.txtNama.setText(nama_produk);
        holder.txtHarga.setText("Rp" + NumberFormat.getInstance().format(Integer.parseInt(harga)));

        Glide.with(context)
                .load(img)
                .error(R.mipmap.ic_launcher)
                .into(holder.imgProduk);

        PushDownAnim.setPushDownAnimTo(holder.itemView)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DetailProdukActivity.class);
                        intent.putExtra("nama_produk", dataProduk.get(position).getNamaProduk());
                        intent.putExtra("id_transaksi", cariResepActivity.id_transaksi);
                        intent.putExtra("id_customer", cariResepActivity.id_customer);
                        intent.putExtra("jenis_obat", cariResepActivity.jenisObat);
                        context.startActivity(intent);
                    }
                });

        holder.relativeHapus.setVisibility(View.GONE);
        holder.linearJumlah.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return dataProduk.size();
    }

    public class CariObatViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProduk;
        TextView txtNama, txtHarga;
        ImageView relativeHapus;
        LinearLayout linearJumlah;

        public CariObatViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgProduk = itemView.findViewById(R.id.img_item_resep_obat);
            txtNama = itemView.findViewById(R.id.text_item_nama_resep_obat);
            txtHarga = itemView.findViewById(R.id.text_item_harga_resep_obat);
            relativeHapus = itemView.findViewById(R.id.img_delete_resep);
            linearJumlah = itemView.findViewById(R.id.linear_jumlah_resep_obat);
        }
    }
}
