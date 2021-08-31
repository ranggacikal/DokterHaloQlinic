package id.luvie.luviedokter.model.list_mitra;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseListMitra{

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
			"ResponseListMitra{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}