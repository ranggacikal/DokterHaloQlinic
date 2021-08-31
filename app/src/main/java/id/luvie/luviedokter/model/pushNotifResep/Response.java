package id.luvie.luviedokter.model.pushNotifResep;

import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("recipients")
	private int recipients;

	@SerializedName("id")
	private String id;

	@SerializedName("errors")
	private Errors errors;

	public void setRecipients(int recipients){
		this.recipients = recipients;
	}

	public int getRecipients(){
		return recipients;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setErrors(Errors errors){
		this.errors = errors;
	}

	public Errors getErrors(){
		return errors;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"recipients = '" + recipients + '\'' + 
			",id = '" + id + '\'' + 
			",errors = '" + errors + '\'' + 
			"}";
		}
}