package com.haloqlinic.dokter.model.kategoriDokter;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("id_kategori")
	private String idKategori;

	@SerializedName("kategori")
	private String kategori;

	public void setIdKategori(String idKategori){
		this.idKategori = idKategori;
	}

	public String getIdKategori(){
		return idKategori;
	}

	public void setKategori(String kategori){
		this.kategori = kategori;
	}

	public String getKategori(){
		return kategori;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"id_kategori = '" + idKategori + '\'' + 
			",kategori = '" + kategori + '\'' + 
			"}";
		}
}