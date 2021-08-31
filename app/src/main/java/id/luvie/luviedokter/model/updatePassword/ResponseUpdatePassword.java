package id.luvie.luviedokter.model.updatePassword;

import com.google.gson.annotations.SerializedName;

public class ResponseUpdatePassword{

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
			"ResponseUpdatePassword{" + 
			"response = '" + response + '\'' + 
			"}";
		}
}