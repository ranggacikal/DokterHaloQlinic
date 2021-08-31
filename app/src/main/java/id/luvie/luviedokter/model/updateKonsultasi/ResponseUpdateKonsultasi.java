package id.luvie.luviedokter.model.updateKonsultasi;

public class ResponseUpdateKonsultasi{
	private String response;
	private String idTransaksi;

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setIdTransaksi(String idTransaksi){
		this.idTransaksi = idTransaksi;
	}

	public String getIdTransaksi(){
		return idTransaksi;
	}

	@Override
 	public String toString(){
		return 
			"ResponseUpdateKonsultasi{" + 
			"response = '" + response + '\'' + 
			",id_transaksi = '" + idTransaksi + '\'' + 
			"}";
		}
}
