package id.luvie.luviedokter.api;

import id.luvie.luviedokter.model.addRecipe.ResponseTambahResep;
import id.luvie.luviedokter.model.cariProduk.ResponseCariProduk;
import id.luvie.luviedokter.model.checkPayment.ResponseCheckPayment;
import id.luvie.luviedokter.model.editAkun.ResponseEditAkun;
import id.luvie.luviedokter.model.getPlayerId.ResponseGetPlayerId;
import id.luvie.luviedokter.model.hapusResep.ResponseHapusResep;
import id.luvie.luviedokter.model.kategoriDokter.ResponseKategoriDokter;
import id.luvie.luviedokter.model.kecamatan.ResponseDataKecamatan;
import id.luvie.luviedokter.model.konsultasiBerlangsung.ResponseKonsultasiBerlangsung;
import id.luvie.luviedokter.model.kota.ResponseDatakota;
import id.luvie.luviedokter.model.listKonsultasi.ResponseDataKonsultasi;
import id.luvie.luviedokter.model.listKonsultasiOnline.ResponseDataKonsultasiOnline;
import id.luvie.luviedokter.model.listRecipe.ResponseDataRecipe;
import id.luvie.luviedokter.model.listWithDrawal.ResponseListWithDrawal;
import id.luvie.luviedokter.model.list_mitra.ResponseListMitra;
import id.luvie.luviedokter.model.list_treatment.ResponseListTreatment;
import id.luvie.luviedokter.model.login.ResponseLogin;
import id.luvie.luviedokter.model.notifChat.ResponseNotif;
import id.luvie.luviedokter.model.popupRequest.ResponseRequestKonsultasi;
import id.luvie.luviedokter.model.provinsi.ResponseDataProvinsi;
import id.luvie.luviedokter.model.pushNotifResep.ResponsePushNotifResep;
import id.luvie.luviedokter.model.resetPassword.ResponseResetPassword;
import id.luvie.luviedokter.model.saldo.ResponseDataSaldo;
import id.luvie.luviedokter.model.signUp.ResponseSignup;
import id.luvie.luviedokter.model.status.ResponseStatus;
import id.luvie.luviedokter.model.statusDokter.ResponseStatusDokter;
import id.luvie.luviedokter.model.updateKonsultasi.ResponseUpdateKonsultasi;
import id.luvie.luviedokter.model.updatePassword.ResponseUpdatePassword;
import id.luvie.luviedokter.model.updatePhoto.ResponseUpdatePhoto;
import id.luvie.luviedokter.model.updateRecipe.ResponseUpdateResep;
import id.luvie.luviedokter.model.updateStatus.ResponseUpdateStatus;
import id.luvie.luviedokter.model.updateWaktuKonsul.ResponseUpdateWaktuKonsul;
import id.luvie.luviedokter.model.withdrawal.ResponseWithdrawal;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("signin.php")
    Call<ResponseLogin> login(@Field("email") String email,
                              @Field("password") String password,
                              @Field("player_id") String player_id);

    @FormUrlEncoded
    @POST("forgot_password.php")
    Call<ResponseResetPassword> resetPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("list_konsultasi.php")
    Call<ResponseDataKonsultasi> dataKonsultasi(@Field("id_dokter") String id_dokter,
                                                @Field("status") String status);

    @FormUrlEncoded
    @POST("saldo.php")
    Call<ResponseDataSaldo> dataSaldo(@Field("id_dokter") String id_dokter,
                                      @Field("status") String status);

    @GET("province.php")
    Call<ResponseDataProvinsi> dataProvinsi();

    @FormUrlEncoded
    @POST("city.php")
    Call<ResponseDatakota> dataKota(@Field("province_id") String province_id);

    @FormUrlEncoded
    @POST("subdistrict.php")
    Call<ResponseDataKecamatan> dataKecamatan(@Field("city_id") String city_id);

    @FormUrlEncoded
    @POST("update_profile.php")
    Call<ResponseEditAkun> editAkun(@Field("id_dokter") String id_dokter,
                                    @Field("nama") String nama,
                                    @Field("alamat") String alamat,
                                    @Field("id_provinsi") String id_provinsi,
                                    @Field("id_kota") String id_kota,
                                    @Field("id_kecamatan") String id_kecamatan,
                                    @Field("no_hp") String no_hp,
                                    @Field("jk") String jk);

    @FormUrlEncoded
    @POST("update_password.php")
    Call<ResponseUpdatePassword> updatePassword(@Field("id_dokter") String id_dokter,
                                                @Field("password1") String password1,
                                                @Field("password2") String password2);

    @FormUrlEncoded
    @POST("list_withdrawal.php")
    Call<ResponseListWithDrawal> listDrawal(@Field("id_dokter") String id_dokter);

    @FormUrlEncoded
    @POST("withdrawal.php")
    Call<ResponseWithdrawal> postWithdrawal(@Field("id_dokter") String id_dokter,
                                            @Field("nominal") String nominal);

    @FormUrlEncoded
    @POST("update_konsultasi.php")
    Call<ResponseUpdateKonsultasi> updateKonsultasu(@Field("id_transaksi") String id_transaksi,
                                                    @Field("status") String status);

    @GET("kategori.php")
    Call<ResponseKategoriDokter> kategoriDokter();

    @FormUrlEncoded
    @POST("signup.php")
    Call<ResponseSignup> signup(@Field("id_kategori") String id_kategori,
                                @Field("email") String email,
                                @Field("password") String password,
                                @Field("nama") String nama,
                                @Field("alamat") String alamat,
                                @Field("no_hp") String no_hp,
                                @Field("id_kecamatan") String id_kecamatan,
                                @Field("id_kota") String id_kota,
                                @Field("id_provinsi") String id_provinsi,
                                @Field("sip") String sip,
                                @Field("spesialis") String spesialis,
                                @Field("jk") String jk,
                                @Field("tentang") String tentang,
                                @Field("str") String str,
                                @Field("alumni") String alumni,
                                @Field("tempat_praktik") String tempat_praktik,
                                @Field("pengalaman") String pengalaman);

    @FormUrlEncoded
    @POST("update_account.php")
    Call<ResponseStatusDokter> statusDokter(@Field("id_dokter") String id_dokter,
                                            @Field("status") String status);

    @FormUrlEncoded
    @POST("status.php")
    Call<ResponseStatus> status(@Field("id_dokter") String id_dokter,
                                @Field("player_id") String player_id);

    @FormUrlEncoded
    @POST("notif.php")
    Call<ResponseNotif> notifChat(@Field("player_id") String player_id);

    @FormUrlEncoded
    @POST("cari_produk.php")
    Call<ResponseCariProduk> cariProduk(@Field("jenis") String jenis,
                                        @Field("id_dokter") String id_dokter,
                                        @Field("nama_produk") String nama_produk);

    @FormUrlEncoded
    @POST("update_status.php")
    Call<ResponseUpdateStatus> updateStatus(@Field("id_transaksi") String id_transaksi);

    @FormUrlEncoded
    @POST("add_recipe.php")
    Call<ResponseTambahResep> tambahResep(@Field("id_transaksi") String id_transaksi,
                                          @Field("id_customer") String id_customer,
                                          @Field("id_dokter") String id_dokter,
                                          @Field("id_produk") String id_produk,
                                          @Field("id_member") String id_member,
                                          @Field("berat") String berat,
                                          @Field("jumlah") String jumlah,
                                          @Field("id") String id,
                                          @Field("variasi") String variasi,
                                          @Field("aturan") String aturan,
                                          @Field("keterangan") String keterangan,
                                          @Field("harga") String harga,
                                          @Field("id_kategori") String id_kategori);

    @FormUrlEncoded
    @POST("recipe.php")
    Call<ResponseDataRecipe> dataResep(@Field("id_transaksi") String id_transaksi);

    @FormUrlEncoded
    @POST("update_recipe.php")
    Call<ResponseUpdateResep> updateResep(@Field("id_transaksi") String id_transaksi,
                                          @Field("id_pesan[]") ArrayList<String> id_pesan,
                                          @Field("jumlah[]") ArrayList<String> jumlah,
                                          @Field("harga[]") ArrayList<String> harga,
                                          @Field("berat_item[]") ArrayList<String> berat_item,
                                          @Field("catatan") String catatan,
                                          @Field("diagnosis") String diagnosis,
                                          @Field("id_member") String id_member);

    @Multipart
    @POST("update_photo.php")
    Call<ResponseUpdatePhoto> updatePhoto(@Part("id_customer") RequestBody id_customer,
                                          @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("update_waktu_konsul.php")
    Call<ResponseUpdateWaktuKonsul> updateWaktuKonsul(@Field("id_transaksi") String id_transaksi);

    @FormUrlEncoded
    @POST("delete_recipe.php")
    Call<ResponseHapusResep> hapusResep(@Field("id_pesan") String id_pesan);

    @FormUrlEncoded
    @POST("list_konsultasi_online.php")
    Call<ResponseDataKonsultasiOnline> dataKonsultasiOnline(@Field("id_transaksi") String id_transaksi);

    @GET("check_request.php")
    Call<ResponseRequestKonsultasi> requestKonsultasi(@Query("id_dokter") String id_dokter);

    @GET("check_payment.php")
    Call<ResponseCheckPayment> checkPayment(@Query("id_transaksi") String id_transaksi);

    @GET("list_mitra.php")
    Call<ResponseListMitra> dataMitra();

    @GET("list_treatment.php")
    Call<ResponseListTreatment> dataTreatment(@Query("id_member") String id_member);

    @FormUrlEncoded
    @POST("get_playerId.php")
    Call<ResponseGetPlayerId> getPlayerId(@Field("id_dokter") String id_dokter,
                                          @Field("player_id") String player_id);

    @FormUrlEncoded
    @POST("konsultasi.php")
    Call<ResponseKonsultasiBerlangsung> konsultasiBerlangsung(@Field("id_dokter") String id_dokter);

    @FormUrlEncoded
    @POST("notif_recipe.php")
    Call<ResponsePushNotifResep> notifResep (@Field("id_transaksi") String id_transaksi);

}