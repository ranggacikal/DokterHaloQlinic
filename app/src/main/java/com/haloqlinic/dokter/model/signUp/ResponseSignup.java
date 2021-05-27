package com.haloqlinic.dokter.model.signUp;

import com.google.gson.annotations.SerializedName;

public class ResponseSignup{

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
			"ResponseSignup{" + 
			"response = '" + response + '\'' + 
			"}";
		}
}