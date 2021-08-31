package id.luvie.luviedokter.model.checkPayment;

import com.google.gson.annotations.SerializedName;

public class ResponseCheckPayment{

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	@SerializedName("token")
	private String token;

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	@Override
 	public String toString(){
		return 
			"ResponseCheckPayment{" + 
			"message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}