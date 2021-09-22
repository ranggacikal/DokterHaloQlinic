package id.luvie.luviedokter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import id.luvie.luviedokter.SharedPreference.SharedPreferencedConfig
import id.luvie.luviedokter.adapter.UlasanAdapter
import id.luvie.luviedokter.databinding.ActivityUlasanBinding
import id.luvie.luviedokter.model.Ulasan
import splitties.toast.toast

class UlasanActivity : AppCompatActivity() {
    lateinit var binding : ActivityUlasanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUlasanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUlasan()
        binding.imgBackPenarikanDana.setOnClickListener {
            onBackPressed()
        }
    }

    fun getUlasan(){
        val preferencedConfig = SharedPreferencedConfig(this)
        val id_dokter = preferencedConfig.preferenceIdDokter
        val host = "https://luvie.co.id/android/dokter/list_feedback.php"

        AndroidNetworking.post(host).addBodyParameter("id_dokter",id_dokter)
            .setPriority(Priority.MEDIUM).build()
            .getAsObject(Ulasan::class.java, object : ParsedRequestListener<Ulasan> {
                override fun onResponse(response: Ulasan?) {
                    binding.recyclerView.layoutManager = LinearLayoutManager(this@UlasanActivity)

                    val adapter = UlasanAdapter(this@UlasanActivity,response?.items!!)
                    binding.recyclerView.adapter = adapter
                }

                override fun onError(anError: ANError?) {
                    toast("Gagal mendapatkan data")
                }
            })
    }
}