package id.luvie.luviedokter.model.konsultasiBerlangsung;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("nama_pasien")
	private String namaPasien;

	@SerializedName("jadwal")
	private String jadwal;

	@SerializedName("status_konsultasi")
	private String statusKonsultasi;

	@SerializedName("img")
	private String img;

	@SerializedName("player_id")
	private String playerId;

	@SerializedName("id_konsultasi")
	private String idKonsultasi;

	@SerializedName("id_transaksi")
	private String idTransaksi;

	@SerializedName("batas_konsultasi")
	private String batasKonsultasi;

	@SerializedName("token")
	private String token;

	public void setNamaPasien(String namaPasien){
		this.namaPasien = namaPasien;
	}

	public String getNamaPasien(){
		return namaPasien;
	}

	public void setJadwal(String jadwal){
		this.jadwal = jadwal;
	}

	public String getJadwal(){
		return jadwal;
	}

	public void setStatusKonsultasi(String statusKonsultasi){
		this.statusKonsultasi = statusKonsultasi;
	}

	public String getStatusKonsultasi(){
		return statusKonsultasi;
	}

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return img;
	}

	public void setPlayerId(String playerId){
		this.playerId = playerId;
	}

	public String getPlayerId(){
		return playerId;
	}

	public void setIdKonsultasi(String idKonsultasi){
		this.idKonsultasi = idKonsultasi;
	}

	public String getIdKonsultasi(){
		return idKonsultasi;
	}

	public void setIdTransaksi(String idTransaksi){
		this.idTransaksi = idTransaksi;
	}

	public String getIdTransaksi(){
		return idTransaksi;
	}

	public void setBatasKonsultasi(String batasKonsultasi){
		this.batasKonsultasi = batasKonsultasi;
	}

	public String getBatasKonsultasi(){
		return batasKonsultasi;
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
			"nama_pasien = '" + namaPasien + '\'' + 
			",jadwal = '" + jadwal + '\'' + 
			",status_konsultasi = '" + statusKonsultasi + '\'' + 
			",img = '" + img + '\'' + 
			",player_id = '" + playerId + '\'' + 
			",id_konsultasi = '" + idKonsultasi + '\'' + 
			",id_transaksi = '" + idTransaksi + '\'' + 
			",batas_konsultasi = '" + batasKonsultasi + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}