package com.haloqlinic.dokter.model.editAkun;

import com.google.gson.annotations.SerializedName;

public class ResponseItem{

	@SerializedName("provinsi")
	private String provinsi;

	@SerializedName("jk")
	private String jk;

	@SerializedName("img")
	private String img;

	@SerializedName("no_hp")
	private String noHp;

	@SerializedName("nama_provinsi")
	private String namaProvinsi;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("uid")
	private Object uid;

	@SerializedName("password")
	private String password;

	@SerializedName("player_id")
	private Object playerId;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("kode")
	private String kode;

	@SerializedName("spesialis")
	private String spesialis;

	@SerializedName("sip")
	private String sip;

	@SerializedName("email")
	private String email;

	@SerializedName("kota")
	private String kota;

	@SerializedName("id_kategori")
	private String idKategori;

	@SerializedName("saldo")
	private String saldo;

	@SerializedName("id_dokter")
	private String idDokter;

	@SerializedName("token")
	private Object token;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("nama_kecamatan")
	private String namaKecamatan;

	@SerializedName("tentang")
	private Object tentang;

	@SerializedName("nama")
	private String nama;

	@SerializedName("kecamatan")
	private String kecamatan;

	@SerializedName("nama_kota")
	private String namaKota;

	@SerializedName("status")
	private String status;

	public void setProvinsi(String provinsi){
		this.provinsi = provinsi;
	}

	public String getProvinsi(){
		return provinsi;
	}

	public void setJk(String jk){
		this.jk = jk;
	}

	public String getJk(){
		return jk;
	}

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return img;
	}

	public void setNoHp(String noHp){
		this.noHp = noHp;
	}

	public String getNoHp(){
		return noHp;
	}

	public void setNamaProvinsi(String namaProvinsi){
		this.namaProvinsi = namaProvinsi;
	}

	public String getNamaProvinsi(){
		return namaProvinsi;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setUid(Object uid){
		this.uid = uid;
	}

	public Object getUid(){
		return uid;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setPlayerId(Object playerId){
		this.playerId = playerId;
	}

	public Object getPlayerId(){
		return playerId;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setKode(String kode){
		this.kode = kode;
	}

	public String getKode(){
		return kode;
	}

	public void setSpesialis(String spesialis){
		this.spesialis = spesialis;
	}

	public String getSpesialis(){
		return spesialis;
	}

	public void setSip(String sip){
		this.sip = sip;
	}

	public String getSip(){
		return sip;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setKota(String kota){
		this.kota = kota;
	}

	public String getKota(){
		return kota;
	}

	public void setIdKategori(String idKategori){
		this.idKategori = idKategori;
	}

	public String getIdKategori(){
		return idKategori;
	}

	public void setSaldo(String saldo){
		this.saldo = saldo;
	}

	public String getSaldo(){
		return saldo;
	}

	public void setIdDokter(String idDokter){
		this.idDokter = idDokter;
	}

	public String getIdDokter(){
		return idDokter;
	}

	public void setToken(Object token){
		this.token = token;
	}

	public Object getToken(){
		return token;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setNamaKecamatan(String namaKecamatan){
		this.namaKecamatan = namaKecamatan;
	}

	public String getNamaKecamatan(){
		return namaKecamatan;
	}

	public void setTentang(Object tentang){
		this.tentang = tentang;
	}

	public Object getTentang(){
		return tentang;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setKecamatan(String kecamatan){
		this.kecamatan = kecamatan;
	}

	public String getKecamatan(){
		return kecamatan;
	}

	public void setNamaKota(String namaKota){
		this.namaKota = namaKota;
	}

	public String getNamaKota(){
		return namaKota;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ResponseItem{" + 
			"provinsi = '" + provinsi + '\'' + 
			",jk = '" + jk + '\'' + 
			",img = '" + img + '\'' + 
			",no_hp = '" + noHp + '\'' + 
			",nama_provinsi = '" + namaProvinsi + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",uid = '" + uid + '\'' + 
			",password = '" + password + '\'' + 
			",player_id = '" + playerId + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",kode = '" + kode + '\'' + 
			",spesialis = '" + spesialis + '\'' + 
			",sip = '" + sip + '\'' + 
			",email = '" + email + '\'' + 
			",kota = '" + kota + '\'' + 
			",id_kategori = '" + idKategori + '\'' + 
			",saldo = '" + saldo + '\'' + 
			",id_dokter = '" + idDokter + '\'' + 
			",token = '" + token + '\'' + 
			",alamat = '" + alamat + '\'' + 
			",nama_kecamatan = '" + namaKecamatan + '\'' + 
			",tentang = '" + tentang + '\'' + 
			",nama = '" + nama + '\'' + 
			",kecamatan = '" + kecamatan + '\'' + 
			",nama_kota = '" + namaKota + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}