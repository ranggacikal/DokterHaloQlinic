package com.haloqlinic.dokter.model.status;

import com.google.gson.annotations.SerializedName;

public class ResponseItem{

	@SerializedName("status")
	private String status;

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ResponseItem{" + 
			"status = '" + status + '\'' + 
			"}";
		}
}