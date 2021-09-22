package id.luvie.luviedokter.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

 class Ulasan : Serializable {
     @SerializedName("id") var id = ""
     @SerializedName("id_transaksi") var id_transaksi = ""
     @SerializedName("nilai") var nilai = ""
     @SerializedName("komentar") var komentar = ""
     @SerializedName("created") var created = ""
     @SerializedName("nama") var nama = ""
     @SerializedName("data") var items : ArrayList<Ulasan> ?= null

 }

