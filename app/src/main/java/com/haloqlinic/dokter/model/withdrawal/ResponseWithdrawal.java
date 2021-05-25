package com.haloqlinic.dokter.model.withdrawal;

import com.google.gson.annotations.SerializedName;

public class ResponseWithdrawal{

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
			"ResponseWithdrawal{" + 
			"response = '" + response + '\'' + 
			"}";
		}
}