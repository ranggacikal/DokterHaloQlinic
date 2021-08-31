package id.luvie.luviedokter.model.kota;

import com.google.gson.annotations.SerializedName;

public class ResponseItem{

	@SerializedName("city_name")
	private String cityName;

	@SerializedName("province")
	private String province;

	@SerializedName("province_id")
	private String provinceId;

	@SerializedName("type")
	private String type;

	@SerializedName("postal_code")
	private String postalCode;

	@SerializedName("city_id")
	private String cityId;

	public void setCityName(String cityName){
		this.cityName = cityName;
	}

	public String getCityName(){
		return cityName;
	}

	public void setProvince(String province){
		this.province = province;
	}

	public String getProvince(){
		return province;
	}

	public void setProvinceId(String provinceId){
		this.provinceId = provinceId;
	}

	public String getProvinceId(){
		return provinceId;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setPostalCode(String postalCode){
		this.postalCode = postalCode;
	}

	public String getPostalCode(){
		return postalCode;
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
			"city_name = '" + cityName + '\'' + 
			",province = '" + province + '\'' + 
			",province_id = '" + provinceId + '\'' + 
			",type = '" + type + '\'' + 
			",postal_code = '" + postalCode + '\'' + 
			",city_id = '" + cityId + '\'' + 
			"}";
		}
}