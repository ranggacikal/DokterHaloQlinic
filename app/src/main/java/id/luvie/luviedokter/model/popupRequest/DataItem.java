package id.luvie.luviedokter.model.popupRequest;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("jadwal")
	private String jadwal;

	@SerializedName("nama")
	private String nama;

	@SerializedName("id_transaksi")
	private String idTransaksi;

	@SerializedName("token")
	private String token;

	public void setJadwal(String jadwal){
		this.jadwal = jadwal;
	}

	public String getJadwal(){
		return jadwal;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setIdTransaksi(String idTransaksi){
		this.idTransaksi = idTransaksi;
	}

	public String getIdTransaksi(){
		return idTransaksi;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"jadwal = '" + jadwal + '\'' + 
			",nama = '" + nama + '\'' + 
			",id_transaksi = '" + idTransaksi + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}