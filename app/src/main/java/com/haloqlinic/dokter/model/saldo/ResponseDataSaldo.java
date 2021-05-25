package com.haloqlinic.dokter.model.saldo;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDataSaldo{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("saldo")
	private String saldo;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setSaldo(String saldo){
		this.saldo = saldo;
	}

	public String getSaldo(){
		return saldo;
	}

	@Override
 	public String toString(){
		return 
			"ResponseDataSaldo{" + 
			"data = '" + data + '\'' + 
			",saldo = '" + saldo + '\'' + 
			"}";
		}
}