package id.luvie.luviedokter.model.notifChat;

import com.google.gson.annotations.SerializedName;

public class ResponseNotif{

	@SerializedName("recipients")
	private int recipients;

	@SerializedName("external_id")
	private Object externalId;

	@SerializedName("id")
	private String id;

	public void setRecipients(int recipients){
		this.recipients = recipients;
	}

	public int getRecipients(){
		return recipients;
	}

	public void setExternalId(Object externalId){
		this.externalId = externalId;
	}

	public Object getExternalId(){
		return externalId;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"ResponseNotif{" + 
			"recipients = '" + recipients + '\'' + 
			",external_id = '" + externalId + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}