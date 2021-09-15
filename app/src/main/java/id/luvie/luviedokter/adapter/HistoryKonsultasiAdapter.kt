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
import id.luvie.luviedokter.model.HistoryKonsultasi
import org.json.JSONException
import org.json.JSONObject

class HistoryKonsultasiAdapter(private val activity: Activity?, private val data: ArrayList<HistoryKonsultasi>) : RecyclerView.Adapter<HistoryKonsultasiAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                ItemHistoryKonsultasiBinding.inflate(
                        activity!!.layoutInflater, viewGroup,
                        false
                ))
    }

    override fun onBindViewHolder(v: ViewHolder, i: Int) {
        with(v){
            val item = data[i]
            binding.txtNama.text = item.nama_pasien

            if (item.status_resume == "1"){
                binding.btnTambahResep.visibility = View.VISIBLE
            }else{
                binding.btnTambahResep.visibility = View.GONE
            }

            var catatan = "tidak ada catatan"
            if (item.catatan!=""){
                catatan = item.catatan
            }
            binding.txtCatatan.text = catatan

            var diagnosis = "tidak ada diagnosis"
            if (item.diagnosis!=""){
                diagnosis = item.diagnosis
            }
            binding.txtDiagnosis.text = diagnosis

            binding.btnTambahResep.setOnClickListener {
                cekStatus(item.id_customer,item.id_transaksi,item)
            }
        }



    }


    override fun getItemCount(): Int {
        return data.size ?: 0
    }

    /**
     * View holder to display each RecylerView item
     */
    inner class ViewHolder(binding: ItemHistoryKonsultasiBinding) :
            RecyclerView.ViewHolder(binding.root) {
        val binding: ItemHistoryKonsultasiBinding = binding

    }

    private fun cekStatus(id_customer:String,id_transaksi:String,item:HistoryKonsultasi) {
        val preferencedConfig = SharedPreferencedConfig(activity)

        val host = "https://luvie.co.id/android/dokter/status.php"
        AndroidNetworking.post(host)
            .addBodyParameter("id_dokter", preferencedConfig.getPreferenceIdDokter())
            .setPriority(Priority.HIGH).build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    try {
                        val jsonArray = response.getJSONArray("response")
                        val jsonObject = jsonArray.getJSONObject(0)
                        val id_kategori = jsonObject.getString("id_kategori")

                        val intent = Intent(activity, TambahResepObatActivity::class.java)
                        intent.putExtra("status","edit")
                        intent.putExtra("id_transaksi", id_transaksi)
                        intent.putExtra("id_customer", id_customer)
                        intent.putExtra("id_kategori", id_kategori)
                        intent.putExtra("item",item)
                        activity?.startActivity(intent)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                override fun onError(anError: ANError) {
                    cekStatus(id_customer, id_transaksi,item)
                }
            })
    }

}