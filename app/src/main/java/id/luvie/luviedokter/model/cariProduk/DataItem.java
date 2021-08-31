package id.luvie.luviedokter.model.cariProduk;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("id_produk")
	private String idProduk;

	@SerializedName("nama_produk")
	private String namaProduk;

	@SerializedName("img")
	private String img;

	@SerializedName("harga")
	private String harga;

	@SerializedName("berat")
	private String berat;

	@SerializedName("kode")
	private String kode;

	@SerializedName("id_member")
	private String idMember;

	@SerializedName("variasi")
	private List<VariasiItem> variasi;

	@SerializedName("disc")
	private String disc;

	@SerializedName("harga_jual")
	private String hargaJual;

	public void setIdProduk(String idProduk){
		this.idProduk = idProduk;
	}

	public String getIdProduk(){
		return idProduk;
	}

	public void setNamaProduk(String namaProduk){
		this.namaProduk = namaProduk;
	}

	public String getNamaProduk(){
		return namaProduk;
	}

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return img;
	}

	public void setHarga(String harga){
		this.harga = harga;
	}

	public String getHarga(){
		return harga;
	}

	public void setBerat(String berat){
		this.berat = berat;
	}

	public String getBerat(){
		return berat;
	}

	public void setKode(String kode){
		this.kode = kode;
	}

	public String getKode(){
		return kode;
	}

	public void setIdMember(String idMember){
		this.idMember = idMember;
	}

	public String getIdMember(){
		return idMember;
	}

	public void setVariasi(List<VariasiItem> variasi){
		this.variasi = variasi;
	}

	public List<VariasiItem> getVariasi(){
		return variasi;
	}

	public void setDisc(String disc){
		this.disc = disc;
	}

	public String getDisc(){
		return disc;
	}

	public void setHargaJual(String hargaJual){
		this.hargaJual = hargaJual;
	}

	public String getHargaJual(){
		return hargaJual;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"id_produk = '" + idProduk + '\'' + 
			",nama_produk = '" + namaProduk + '\'' + 
			",img = '" + img + '\'' + 
			",harga = '" + harga + '\'' + 
			",berat = '" + berat + '\'' + 
			",kode = '" + kode + '\'' + 
			",id_member = '" + idMember + '\'' + 
			",variasi = '" + variasi + '\'' + 
			",disc = '" + disc + '\'' + 
			",harga_jual = '" + hargaJual + '\'' + 
			"}";
		}
}