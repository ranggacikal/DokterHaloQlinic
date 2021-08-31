package id.luvie.luviedokter.model.pushNotifResep;

import com.google.gson.annotations.SerializedName;

public class ResponsePushNotifResep{

	@SerializedName("response")
	private Response response;

	public void setResponse(Response response){
		this.response = response;
	}

	public Response getResponse(){
		return response;
	}

	@Override
 	public String toString(){
		return 
			"ResponsePushNotifResep{" + 
			"response = '" + response + '\'' + 
			"}";
		}
}