package id.luvie.luviedokter.model.addRecipe;

import com.google.gson.annotations.SerializedName;

public class ResponseTambahResep{

	@SerializedName("response")
	private String response;

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	@Override
 	public String toString(){
		return 
			"ResponseTambahResep{" + 
			"response = '" + response + '\'' + 
			"}";
		}
}