package id.luvie.luviedokter.model.kecamatan;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDataKecamatan{

	@SerializedName("response")
	private List<ResponseItem> response;

	public void setResponse(List<ResponseItem> response){
		this.response = response;
	}

	public List<ResponseItem> getResponse(){
		return response;
	}

	@Override
 	public String toString(){
		return 
			"ResponseDataKecamatan{" + 
			"response = '" + response + '\'' + 
			"}";
		}
}