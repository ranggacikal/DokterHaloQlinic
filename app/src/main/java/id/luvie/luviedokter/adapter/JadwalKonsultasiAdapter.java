package id.luvie.luviedokter.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import id.luvie.luviedokter.ChatActivity;
import id.luvie.luviedokter.R;
import id.luvie.luviedokter.model.listKonsultasi.DataItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
    public void onBindViewHolder(@NonNull @NotNull JadwalKonsultasiViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txtNamaDokter.setText(dataKonsultasi.get(position).getNamaPasien());
        holder.txtWaktu.setText(dataKonsultasi.get(position).getJadwal());

        String link = "https://aplikasicerdas.net/haloqlinic/file/customer/profile/";
        String img = (String) dataKonsultasi.get(position).getImg();

        String url_image = link+img;

        Glide.with(context)
                .load(url_image)
                .error(R.drawable.icon_user)
                .into(holder.imgPasien);

        PushDownAnim.setPushDownAnimTo(holder.itemView)
                .setScale( MODE_SCALE, 0.89f  )
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String status = dataKonsultasi.get(position).getStatusKonsultasi();

                        if (status.equals("0")) {

                            String batas_konsultasi = (String) dataKonsultasi.get(position).getBatasKonsultasi();

                            Intent intentChat = new Intent(context, ChatActivity.class);
                            intentChat.putExtra("token", String.valueOf(dataKonsultasi.get(position).getToken()));
                            intentChat.putExtra("nama", dataKonsultasi.get(position).getNamaPasien());
                            intentChat.putExtra("player_id", dataKonsultasi.get(position).getPlayerId());
                            intentChat.putExtra("id_transaksi", dataKonsultasi.get(position).getIdTransaksi());
                            intentChat.putExtra("id_customer", dataKonsultasi.get(position).getIdCustomer());
                            intentChat.putExtra("batas_konsultasi", batas_konsultasi);
                            intentChat.putExtra("url_image", url_image);
                            intentChat.putExtra("activity", "jadwal_konsultasi");
                            Log.d("checkIdTransaksi", "adapter: " + dataKonsultasi.get(position).getIdTransaksi());
                            context.startActivity(intentChat);
                        }else{
                            Toast.makeText(context, "Anda sudah menyelesaikan konsultasi ini", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return dataKonsultasi.size();
    }

    public class JadwalKonsultasiViewHolder extends RecyclerView.ViewHolder {

        TextView txtNamaDokter, txtWaktu;
        CircleImageView imgPasien;

        public JadwalKonsultasiViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtNamaDokter = itemView.findViewById(R.id.text_item_nama_jadwal_konsultasi);
            txtWaktu = itemView.findViewById(R.id.text_item_tanggal_jadwal_konsultasi);
            imgPasien = itemView.findViewById(R.id.img_item_jadwal_konsultasi);
        }
    }
}
