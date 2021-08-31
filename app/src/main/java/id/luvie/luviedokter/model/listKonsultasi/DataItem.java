package id.luvie.luviedokter.model.listKonsultasi;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("nama_pasien")
	private String namaPasien;

	@SerializedName("jadwal")
	private String jadwal;

	@SerializedName("status_konsultasi")
	private String statusKonsultasi;

	@SerializedName("img")
	private Object img;

	@SerializedName("player_id")
	private String playerId;

	@SerializedName("id_customer")
	private String idCustomer;

	@SerializedName("id_konsultasi")
	private String idKonsultasi;

	@SerializedName("id_transaksi")
	private String idTransaksi;

	@SerializedName("batas_konsultasi")
	private Object batasKonsultasi;

	@SerializedName("status_transaksi")
	private String statusTransaksi;

	@SerializedName("token")
	private Object token;

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

	public void setImg(Object img){
		this.img = img;
	}

	public Object getImg(){
		return img;
	}

	public void setPlayerId(String playerId){
		this.playerId = playerId;
	}

	public String getPlayerId(){
		return playerId;
	}

	public void setIdCustomer(String idCustomer){
		this.idCustomer = idCustomer;
	}

	public String getIdCustomer(){
		return idCustomer;
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

	public void setBatasKonsultasi(Object batasKonsultasi){
		this.batasKonsultasi = batasKonsultasi;
	}

	public Object getBatasKonsultasi(){
		return batasKonsultasi;
	}

	public void setStatusTransaksi(String statusTransaksi){
		this.statusTransaksi = statusTransaksi;
	}

	public String getStatusTransaksi(){
		return statusTransaksi;
	}

	public void setToken(Object token){
		this.token = token;
	}

	public Object getToken(){
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
			",id_customer = '" + idCustomer + '\'' + 
			",id_konsultasi = '" + idKonsultasi + '\'' + 
			",id_transaksi = '" + idTransaksi + '\'' + 
			",batas_konsultasi = '" + batasKonsultasi + '\'' + 
			",status_transaksi = '" + statusTransaksi + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}