package id.luvie.luviedokter.model.cariProduk;

import com.google.gson.annotations.SerializedName;

public class VariasiItem{

	@SerializedName("variasi")
	private String variasi;

	@SerializedName("id")
	private String id;

	@SerializedName("stok")
	private String stok;

	public void setVariasi(String variasi){
		this.variasi = variasi;
	}

	public String getVariasi(){
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