package com.haloqlinic.dokter.model.saldo;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("jadwal")
	private String jadwal;

	@SerializedName("biaya")
	private String biaya;

	@SerializedName("nama")
	private String nama;

	@SerializedName("id_transaksi")
	private String idTransaksi;

	public void setJadwal(String jadwal){
		this.jadwal = jadwal;
	}

	public String getJadwal(){
		return jadwal;
	}

	public void setBiaya(String biaya){
		this.biaya = biaya;
	}

	public String getBiaya(){
		return biaya;
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

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"jadwal = '" + jadwal + '\'' + 
			",biaya = '" + biaya + '\'' + 
			",nama = '" + nama + '\'' + 
			",id_transaksi = '" + idTransaksi + '\'' + 
			"}";
		}
}