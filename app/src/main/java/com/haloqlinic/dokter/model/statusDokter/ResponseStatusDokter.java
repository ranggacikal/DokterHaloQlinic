package com.haloqlinic.dokter.model.statusDokter;

import com.google.gson.annotations.SerializedName;

public class ResponseStatusDokter{

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
			"ResponseStatusDokter{" + 
			"response = '" + response + '\'' + 
			"}";
		}
}