package id.luvie.luviedokter.model.getPlayerId;

import com.google.gson.annotations.SerializedName;

public class ResponseGetPlayerId{

	@SerializedName("player_id")
	private String playerId;

	@SerializedName("id_dokter")
	private String idDokter;

	public void setPlayerId(String playerId){
		this.playerId = playerId;
	}

	public String getPlayerId(){
		return playerId;
	}

	public void setIdDokter(String idDokter){
		this.idDokter = idDokter;
	}

	public String getIdDokter(){
		return idDokter;
	}

	@Override
 	public String toString(){
		return 
			"ResponseGetPlayerId{" + 
			"player_id = '" + playerId + '\'' + 
			",id_dokter = '" + idDokter + '\'' + 
			"}";
		}
}