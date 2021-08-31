package id.luvie.luviedokter.model.list_mitra;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("nama")
	private String nama;

	@SerializedName("id_member")
	private String idMember;

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setIdMember(String idMember){
		this.idMember = idMember;
	}

	public String getIdMember(){
		return idMember;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"nama = '" + nama + '\'' + 
			",id_member = '" + idMember + '\'' + 
			"}";
		}
}