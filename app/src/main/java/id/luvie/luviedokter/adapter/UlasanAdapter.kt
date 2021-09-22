package id.luvie.luviedokter.adapter

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import id.luvie.luviedokter.SharedPreference.SharedPreferencedConfig
import id.luvie.luviedokter.TambahResepObatActivity
import id.luvie.luviedokter.databinding.ItemHistoryKonsultasiBinding
import id.luvie.luviedokter.databinding.ItemUlasanBinding
import id.luvie.luviedokter.model.HistoryKonsultasi
import id.luvie.luviedokter.model.Ulasan
import org.json.JSONException
import org.json.JSONObject

class UlasanAdapter(private val activity: Activity?, private val data: ArrayList<Ulasan>) : RecyclerView.Adapter<UlasanAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                ItemUlasanBinding.inflate(
                        activity!!.layoutInflater, viewGroup,
                        false
                ))
    }

    override fun onBindViewHolder(v: ViewHolder, i: Int) {
        with(v){
            val item = data[i]
            binding.txtNama.text = item.nama
            binding.txtKomentar.text = item.komentar

            binding.ratingUlasan.rating = item.nilai.toFloat()

        }



    }


    override fun getItemCount(): Int {
        return data.size ?: 0
    }

    /**
     * View holder to display each RecylerView item
     */
    inner class ViewHolder(binding: ItemUlasanBinding) :
            RecyclerView.ViewHolder(binding.root) {
        val binding: ItemUlasanBinding = binding

    }

}