package id.luvie.luviedokter.model.popupRequest;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseRequestKonsultasi{

	@SerializedName("total_request")
	private int totalRequest;

	@SerializedName("data")
	private List<DataItem> data;

	public void setTotalRequest(int totalRequest){
		this.totalRequest = totalRequest;
	}

	public int getTotalRequest(){
		return totalRequest;
	}

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"ResponseRequestKonsultasi{" + 
			"total_request = '" + totalRequest + '\'' + 
			",data = '" + data + '\'' + 
			"}";
		}
}