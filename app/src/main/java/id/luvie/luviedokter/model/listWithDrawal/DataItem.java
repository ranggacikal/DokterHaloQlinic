package id.luvie.luviedokter.model.listWithDrawal;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("jumlah")
	private String jumlah;

	@SerializedName("kode")
	private String kode;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private String id;

	@SerializedName("id_dokter")
	private String idDokter;

	@SerializedName("status")
	private String status;

	public void setJumlah(String jumlah){
		this.jumlah = jumlah;
	}

	public String getJumlah(){
		return jumlah;
	}

	public void setKode(String kode){
		this.kode = kode;
	}

	public String getKode(){
		return kode;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setIdDokter(String idDokter){
		this.idDokter = idDokter;
	}

	public String getIdDokter(){
		return idDokter;
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
			"DataItem{" + 
			"jumlah = '" + jumlah + '\'' + 
			",kode = '" + kode + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",id_dokter = '" + idDokter + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}