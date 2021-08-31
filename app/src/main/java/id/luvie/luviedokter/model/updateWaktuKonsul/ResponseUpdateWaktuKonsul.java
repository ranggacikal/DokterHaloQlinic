package id.luvie.luviedokter.model.updateWaktuKonsul;

import com.google.gson.annotations.SerializedName;

public class ResponseUpdateWaktuKonsul{

	@SerializedName("response")
	private String response;

	@SerializedName("mulai_konsultasi")
	private String mulaiKonsultasi;

	@SerializedName("batas_konsultasi")
	private String batasKonsultasi;

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setMulaiKonsultasi(String mulaiKonsultasi){
		this.mulaiKonsultasi = mulaiKonsultasi;
	}

	public String getMulaiKonsultasi(){
		return mulaiKonsultasi;
	}

	public void setBatasKonsultasi(String batasKonsultasi){
		this.batasKonsultasi = batasKonsultasi;
	}

	public String getBatasKonsultasi(){
		return batasKonsultasi;
	}

	@Override
 	public String toString(){
		return 
			"ResponseUpdateWaktuKonsul{" + 
			"response = '" + response + '\'' + 
			",mulai_konsultasi = '" + mulaiKonsultasi + '\'' + 
			",batas_konsultasi = '" + batasKonsultasi + '\'' + 
			"}";
		}
}