package com.haloqlinic.dokter.model.cariProduk;

import com.google.gson.annotations.SerializedName;

public class VariasiItem{

	@SerializedName("variasi")
	private Object variasi;

	@SerializedName("id")
	private String id;

	@SerializedName("stok")
	private String stok;

	public void setVariasi(Object variasi){
		this.variasi = variasi;
	}

	public Object getVariasi(){
		return variasi;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setStok(String stok){
		this.stok = stok;
	}

	public String getStok(){
		return stok;
	}

	@Override
 	public String toString(){
		return 
			"VariasiItem{" + 
			"variasi = '" + variasi + '\'' + 
			",id = '" + id + '\'' + 
			",stok = '" + stok + '\'' + 
			"}";
		}
}