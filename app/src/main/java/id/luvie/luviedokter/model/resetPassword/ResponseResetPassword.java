package id.luvie.luviedokter.model.resetPassword;

import com.google.gson.annotations.SerializedName;

public class ResponseResetPassword{

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
			"ResponseResetPassword{" + 
			"response = '" + response + '\'' + 
			"}";
		}
}