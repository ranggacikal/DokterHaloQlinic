package com.haloqlinic.dokter.model.updatePhoto;

import com.google.gson.annotations.SerializedName;

public class ResponseUpdatePhoto{

	@SerializedName("img")
	private String img;

	@SerializedName("response")
	private String response;

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return img;
	}

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	@Override
 	public String toString(){
		return 
			"ResponseUpdatePhoto{" + 
			"img = '" + img + '\'' + 
			",response = '" + response + '\'' + 
			"}";
		}
}