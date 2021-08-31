package id.luvie.luviedokter.model.pushNotifResep;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Errors{

	@SerializedName("invalid_player_ids")
	private List<String> invalidPlayerIds;

	public void setInvalidPlayerIds(List<String> invalidPlayerIds){
		this.invalidPlayerIds = invalidPlayerIds;
	}

	public List<String> getInvalidPlayerIds(){
		return invalidPlayerIds;
	}

	@Override
 	public String toString(){
		return 
			"Errors{" + 
			"invalid_player_ids = '" + invalidPlayerIds + '\'' + 
			"}";
		}
}