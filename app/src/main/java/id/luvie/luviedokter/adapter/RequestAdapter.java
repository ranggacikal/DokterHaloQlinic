package id.luvie.luviedokter.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.luvie.luviedokter.DetailKonsultasiActivity;
import id.luvie.luviedokter.R;
import id.luvie.luviedokter.model.popupRequest.DataItem;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    Context context;
    List<DataItem> dataRequest;

    public RequestAdapter(Context context, List<DataItem> dataRequest) {
        this.context = context;
        this.dataRequest = dataRequest;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request, parent, false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txtNama.setText(dataRequest.get(position).getNama());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailKonsultasiActivity.class);
                intent.putExtra("nama_pasien", dataRequest.get(position).getNama());
                intent.putExtra("jadwal", dataRequest.get(position).getJadwal());
                intent.putExtra("id_transaksi", dataRequest.get(position).getIdTransaksi());
                intent.putExtra("token", dataRequest.get(position).getToken());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataRequest.size();
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder {

        TextView txtNama;
        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.text_item_nama_request);
        }
    }
}
