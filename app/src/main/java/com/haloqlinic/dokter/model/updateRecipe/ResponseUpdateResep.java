package com.haloqlinic.dokter.model.updateRecipe;

import com.google.gson.annotations.SerializedName;

public class ResponseUpdateResep{

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
			"ResponseUpdateResep{" + 
			"response = '" + response + '\'' + 
			"}";
		}
}