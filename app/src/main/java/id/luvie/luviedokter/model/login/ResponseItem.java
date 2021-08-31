package id.luvie.luviedokter.model.login;

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

	@SerializedName("alumni")
	private String alumni;

	@SerializedName("uid")
	private String uid;

	@SerializedName("password")
	private String password;

	@SerializedName("player_id")
	private String playerId;

	@SerializedName("updated_at")
	private Object updatedAt;

	@SerializedName("kode")
	private String kode;

	@SerializedName("pengalaman")
	private String pengalaman;

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

	@SerializedName("tempat_praktik")
	private Object tempatPraktik;

	@SerializedName("saldo")
	private String saldo;

	@SerializedName("id_dokter")
	private String idDokter;

	@SerializedName("token")
	private String token;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("str")
	private String str;

	@SerializedName("nama_kecamatan")
	private String namaKecamatan;

	@SerializedName("tentang")
	private String tentang;

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

	public void setAlumni(String alumni){
		this.alumni = alumni;
	}

	public String getAlumni(){
		return alumni;
	}

	public void setUid(String uid){
		this.uid = uid;
	}

	public String getUid(){
		return uid;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setPlayerId(String playerId){
		this.playerId = playerId;
	}

	public String getPlayerId(){
		return playerId;
	}

	public void setUpdatedAt(Object updatedAt){
		this.updatedAt = updatedAt;
	}

	public Object getUpdatedAt(){
		return updatedAt;
	}

	public void setKode(String kode){
		this.kode = kode;
	}

	public String getKode(){
		return kode;
	}

	public void setPengalaman(String pengalaman){
		this.pengalaman = pengalaman;
	}

	public String getPengalaman(){
		return pengalaman;
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

	public void setTempatPraktik(Object tempatPraktik){
		this.tempatPraktik = tempatPraktik;
	}

	public Object getTempatPraktik(){
		return tempatPraktik;
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

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setStr(String str){
		this.str = str;
	}

	public String getStr(){
		return str;
	}

	public void setNamaKecamatan(String namaKecamatan){
		this.namaKecamatan = namaKecamatan;
	}

	public String getNamaKecamatan(){
		return namaKecamatan;
	}

	public void setTentang(String tentang){
		this.tentang = tentang;
	}

	public String getTentang(){
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
			",alumni = '" + alumni + '\'' + 
			",uid = '" + uid + '\'' + 
			",password = '" + password + '\'' + 
			",player_id = '" + playerId + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",kode = '" + kode + '\'' + 
			",pengalaman = '" + pengalaman + '\'' + 
			",spesialis = '" + spesialis + '\'' + 
			",sip = '" + sip + '\'' + 
			",email = '" + email + '\'' + 
			",kota = '" + kota + '\'' + 
			",id_kategori = '" + idKategori + '\'' + 
			",tempat_praktik = '" + tempatPraktik + '\'' + 
			",saldo = '" + saldo + '\'' + 
			",id_dokter = '" + idDokter + '\'' + 
			",token = '" + token + '\'' + 
			",alamat = '" + alamat + '\'' + 
			",str = '" + str + '\'' + 
			",nama_kecamatan = '" + namaKecamatan + '\'' + 
			",tentang = '" + tentang + '\'' + 
			",nama = '" + nama + '\'' + 
			",kecamatan = '" + kecamatan + '\'' + 
			",nama_kota = '" + namaKota + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}