package com.haloqlinic.dokter.model.listRecipe;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("id_produk")
	private String idProduk;

	@SerializedName("keterangan")
	private String keterangan;

	@SerializedName("img")
	private String img;

	@SerializedName("time_limit")
	private Object timeLimit;

	@SerializedName("ongkir")
	private Object ongkir;

	@SerializedName("id_customer")
	private String idCustomer;

	@SerializedName("id_member")
	private String idMember;

	@SerializedName("variasi")
	private String variasi;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("stok")
	private String stok;

	@SerializedName("berat_item")
	private String beratItem;

	@SerializedName("layanan_kurir")
	private Object layananKurir;

	@SerializedName("jumlah")
	private String jumlah;

	@SerializedName("harga")
	private String harga;

	@SerializedName("berat")
	private String berat;

	@SerializedName("kode")
	private String kode;

	@SerializedName("harga_jual")
	private String hargaJual;

	@SerializedName("aturan")
	private String aturan;

	@SerializedName("id_pesan")
	private String idPesan;

	@SerializedName("id_transaksi")
	private String idTransaksi;

	@SerializedName("kurir")
	private Object kurir;

	@SerializedName("id_dokter")
	private String idDokter;

	@SerializedName("nama_produk")
	private String namaProduk;

	@SerializedName("id_variasi")
	private String idVariasi;

	@SerializedName("subtotal")
	private String subtotal;

	@SerializedName("status")
	private Object status;

	public void setIdProduk(String idProduk){
		this.idProduk = idProduk;
	}

	public String getIdProduk(){
		return idProduk;
	}

	public void setKeterangan(String keterangan){
		this.keterangan = keterangan;
	}

	public String getKeterangan(){
		return keterangan;
	}

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return img;
	}

	public void setTimeLimit(Object timeLimit){
		this.timeLimit = timeLimit;
	}

	public Object getTimeLimit(){
		return timeLimit;
	}

	public void setOngkir(Object ongkir){
		this.ongkir = ongkir;
	}

	public Object getOngkir(){
		return ongkir;
	}

	public void setIdCustomer(String idCustomer){
		this.idCustomer = idCustomer;
	}

	public String getIdCustomer(){
		return idCustomer;
	}

	public void setIdMember(String idMember){
		this.idMember = idMember;
	}

	public String getIdMember(){
		return idMember;
	}

	public void setVariasi(String variasi){
		this.variasi = variasi;
	}

	public String getVariasi(){
		return variasi;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setStok(String stok){
		this.stok = stok;
	}

	public String getStok(){
		return stok;
	}

	public void setBeratItem(String beratItem){
		this.beratItem = beratItem;
	}

	public String getBeratItem(){
		return beratItem;
	}

	public void setLayananKurir(Object layananKurir){
		this.layananKurir = layananKurir;
	}

	public Object getLayananKurir(){
		return layananKurir;
	}

	public void setJumlah(String jumlah){
		this.jumlah = jumlah;
	}

	public String getJumlah(){
		return jumlah;
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

	public void setHargaJual(String hargaJual){
		this.hargaJual = hargaJual;
	}

	public String getHargaJual(){
		return hargaJual;
	}

	public void setAturan(String aturan){
		this.aturan = aturan;
	}

	public String getAturan(){
		return aturan;
	}

	public void setIdPesan(String idPesan){
		this.idPesan = idPesan;
	}

	public String getIdPesan(){
		return idPesan;
	}

	public void setIdTransaksi(String idTransaksi){
		this.idTransaksi = idTransaksi;
	}

	public String getIdTransaksi(){
		return idTransaksi;
	}

	public void setKurir(Object kurir){
		this.kurir = kurir;
	}

	public Object getKurir(){
		return kurir;
	}

	public void setIdDokter(String idDokter){
		this.idDokter = idDokter;
	}

	public String getIdDokter(){
		return idDokter;
	}

	public void setNamaProduk(String namaProduk){
		this.namaProduk = namaProduk;
	}

	public String getNamaProduk(){
		return namaProduk;
	}

	public void setIdVariasi(String idVariasi){
		this.idVariasi = idVariasi;
	}

	public String getIdVariasi(){
		return idVariasi;
	}

	public void setSubtotal(String subtotal){
		this.subtotal = subtotal;
	}

	public String getSubtotal(){
		return subtotal;
	}

	public void setStatus(Object status){
		this.status = status;
	}

	public Object getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"id_produk = '" + idProduk + '\'' + 
			",keterangan = '" + keterangan + '\'' + 
			",img = '" + img + '\'' + 
			",time_limit = '" + timeLimit + '\'' + 
			",ongkir = '" + ongkir + '\'' + 
			",id_customer = '" + idCustomer + '\'' + 
			",id_member = '" + idMember + '\'' + 
			",variasi = '" + variasi + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",stok = '" + stok + '\'' + 
			",berat_item = '" + beratItem + '\'' + 
			",layanan_kurir = '" + layananKurir + '\'' + 
			",jumlah = '" + jumlah + '\'' + 
			",harga = '" + harga + '\'' + 
			",berat = '" + berat + '\'' + 
			",kode = '" + kode + '\'' + 
			",harga_jual = '" + hargaJual + '\'' + 
			",aturan = '" + aturan + '\'' + 
			",id_pesan = '" + idPesan + '\'' + 
			",id_transaksi = '" + idTransaksi + '\'' + 
			",kurir = '" + kurir + '\'' + 
			",id_dokter = '" + idDokter + '\'' + 
			",nama_produk = '" + namaProduk + '\'' + 
			",id_variasi = '" + idVariasi + '\'' + 
			",subtotal = '" + subtotal + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}