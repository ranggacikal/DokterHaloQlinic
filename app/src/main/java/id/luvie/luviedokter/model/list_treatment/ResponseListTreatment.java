package id.luvie.luviedokter.model.list_treatment;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseListTreatment{

	@SerializedName("data")
	private List<DataItem> data;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"ResponseListTreatment{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}