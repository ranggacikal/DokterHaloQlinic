package id.luvie.luviedokter.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import id.luvie.luviedokter.R;
import id.luvie.luviedokter.TambahResepObatActivity;
import id.luvie.luviedokter.api.ConfigRetrofit;
import id.luvie.luviedokter.model.hapusResep.ResponseHapusResep;
import id.luvie.luviedokter.model.listRecipe.DataItem;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.List;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class ListObatAdapter extends RecyclerView.Adapter<ListObatAdapter.ListObatViewHolder> {

    Context context;
    List<DataItem> listResep;
    TambahResepObatActivity tambahResepObatActivity;

    public ListObatAdapter(Context context, List<DataItem> listResep, TambahResepObatActivity tambahResepObatActivity) {
        this.context = context;
        this.listResep = listResep;
        this.tambahResepObatActivity = tambahResepObatActivity;
    }

    @NonNull
    @NotNull
    @Override
    public ListObatViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resep_obat, parent, false);
        return new ListObatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ListObatViewHolder holder, @SuppressLint("RecyclerView") int position) {

        String url_image = listResep.get(position).getImg();
        int harga = Integer.parseInt(listResep.get(position).getHargaJual());

        Glide.with(context)
                .load(url_image)
                .error(R.mipmap.ic_launcher)
                .into(holder.imgProduk);

        holder.txtNama.setText(listResep.get(position).getNamaProduk());
        holder.txtHarga.setText("Rp" + NumberFormat.getInstance().format(harga));
        holder.txtJumlah.setText(listResep.get(position).getJumlah());

        PushDownAnim.setPushDownAnimTo(holder.imgDelete)
                .setScale(MODE_SCALE, 0.89f)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tampilDialog(listResep.get(position).getIdPesan());
                    }
                });

    }

    private void tampilDialog(String idPesan) {

        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) context)
                .setTitle("Hapus resep yg dipilih?")
                .setMessage("Apakah anda yakin ingin menghapus item ini?")
                .setCancelable(false)
                .setPositiveButton("Hapus", R.drawable.ic_delete, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        hapusResep(idPesan);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Batal", R.drawable.ic_close, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        // Show Dialog
        mBottomSheetDialog.show();

    }

    private void hapusResep(String idPesan) {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Menghapus resep obat");
        progressDialog.show();

        ConfigRetrofit.service.hapusResep(idPesan).enqueue(new Callback<ResponseHapusResep>() {
            @Override
            public void onResponse(Call<ResponseHapusResep> call, Response<ResponseHapusResep> response) {
                if (response.isSuccessful()){

                    progressDialog.dismiss();
                    tambahResepObatActivity.loadDataResep();

                }else{
                    progressDialog.dismiss();
                    Toast.makeText(context, "Gagal hapus data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHapusResep> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listResep.size();
    }

    public class ListObatViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProduk, imgDelete;
        TextView txtNama, txtHarga, txtJumlah;

        public ListObatViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgProduk = itemView.findViewById(R.id.img_item_resep_obat);
            txtNama = itemView.findViewById(R.id.text_item_nama_resep_obat);
            txtHarga = itemView.findViewById(R.id.text_item_harga_resep_obat);
            imgDelete = itemView.findViewById(R.id.img_delete_resep);
            txtJumlah = itemView.findViewById(R.id.text_item_jumlah_resep_obat);
        }
    }
}
