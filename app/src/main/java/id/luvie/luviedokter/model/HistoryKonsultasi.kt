package id.luvie.luviedokter.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

 class HistoryKonsultasi : Serializable {
     @SerializedName("id_konsultasi") var id_konsultasi = ""
     @SerializedName("id_transaksi") var id_transaksi = ""
     @SerializedName("id_customer") var id_customer = ""
     @SerializedName("status_resep") var status_resep = ""
     @SerializedName("mulai_konsultasi") var mulai_konsultasi = ""
     @SerializedName("batas_konsultasi") var batas_konsultasi = ""
     @SerializedName("catatan") var catatan = ""
     @SerializedName("diagnosis") var diagnosis = ""
     @SerializedName("nama_pasien") var nama_pasien = ""
     @SerializedName("img") var img = ""
     @SerializedName("player_id") var player_id = ""
     @SerializedName("status_resume") var status_resume = ""
     @SerializedName("data") var items : ArrayList<HistoryKonsultasi> ?= null

 }

