package com.haloqlinic.dokter.model.updateStatus;

import com.google.gson.annotations.SerializedName;

public class ResponseUpdateStatus{

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
			"ResponseUpdateStatus{" + 
			"response = '" + response + '\'' + 
			"}";
		}
}