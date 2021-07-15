package com.haloqlinic.dokter.model.hapusResep;

import com.google.gson.annotations.SerializedName;

public class ResponseHapusResep{

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
			"ResponseHapusResep{" + 
			"response = '" + response + '\'' + 
			"}";
		}
}