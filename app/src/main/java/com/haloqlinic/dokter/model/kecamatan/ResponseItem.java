package com.haloqlinic.dokter.model.kecamatan;

import com.google.gson.annotations.SerializedName;

public class ResponseItem{

	@SerializedName("subdistrict_id")
	private String subdistrictId;

	@SerializedName("subdistrict_name")
	private String subdistrictName;

	@SerializedName("city_id")
	private String cityId;

	public void setSubdistrictId(String subdistrictId){
		this.subdistrictId = subdistrictId;
	}

	public String getSubdistrictId(){
		return subdistrictId;
	}

	public void setSubdistrictName(String subdistrictName){
		this.subdistrictName = subdistrictName;
	}

	public String getSubdistrictName(){
		return subdistrictName;
	}

	public void setCityId(String cityId){
		this.cityId = cityId;
	}

	public String getCityId(){
		return cityId;
	}

	@Override
 	public String toString(){
		return 
			"ResponseItem{" + 
			"subdistrict_id = '" + subdistrictId + '\'' + 
			",subdistrict_name = '" + subdistrictName + '\'' + 
			",city_id = '" + cityId + '\'' + 
			"}";
		}
}