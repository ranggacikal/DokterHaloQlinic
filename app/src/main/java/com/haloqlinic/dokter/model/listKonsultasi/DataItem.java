package com.haloqlinic.dokter.model.listKonsultasi;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("nama_pasien")
	private String namaPasien;

	@SerializedName("jadwal")
	private String jadwal;

	@SerializedName("id_customer")
	private String idCustomer;

	@SerializedName("id_konsultasi")
	private String idKonsultasi;

	@SerializedName("id_transaksi")
	private String idTransaksi;

	@SerializedName("status_transaksi")
	private String statusTransaksi;

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

	public void setStatusTransaksi(String statusTransaksi){
		this.statusTransaksi = statusTransaksi;
	}

	public String getStatusTransaksi(){
		return statusTransaksi;
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
			",id_customer = '" + idCustomer + '\'' + 
			",id_konsultasi = '" + idKonsultasi + '\'' + 
			",id_transaksi = '" + idTransaksi + '\'' + 
			",status_transaksi = '" + statusTransaksi + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}