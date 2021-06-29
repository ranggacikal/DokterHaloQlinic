package com.haloqlinic.dokter.api;

import com.haloqlinic.dokter.model.addRecipe.ResponseTambahResep;
import com.haloqlinic.dokter.model.cariProduk.ResponseCariProduk;
import com.haloqlinic.dokter.model.editAkun.ResponseEditAkun;
import com.haloqlinic.dokter.model.kategoriDokter.ResponseKategoriDokter;
import com.haloqlinic.dokter.model.kecamatan.ResponseDataKecamatan;
import com.haloqlinic.dokter.model.kota.ResponseDatakota;
import com.haloqlinic.dokter.model.listKonsultasi.ResponseDataKonsultasi;
import com.haloqlinic.dokter.model.listRecipe.ResponseDataRecipe;
import com.haloqlinic.dokter.model.listWithDrawal.ResponseListWithDrawal;
import com.haloqlinic.dokter.model.login.ResponseItem;
import com.haloqlinic.dokter.model.login.ResponseLogin;
import com.haloqlinic.dokter.model.notifChat.ResponseNotif;
import com.haloqlinic.dokter.model.provinsi.ResponseDataProvinsi;
import com.haloqlinic.dokter.model.resetPassword.ResponseResetPassword;
import com.haloqlinic.dokter.model.saldo.ResponseDataSaldo;
import com.haloqlinic.dokter.model.signUp.ResponseSignup;
import com.haloqlinic.dokter.model.status.ResponseStatus;
import com.haloqlinic.dokter.model.statusDokter.ResponseStatusDokter;
import com.haloqlinic.dokter.model.updateKonsultasi.ResponseUpdateKonsultasi;
import com.haloqlinic.dokter.model.updatePassword.ResponseUpdatePassword;
import com.haloqlinic.dokter.model.updatePhoto.ResponseUpdatePhoto;
import com.haloqlinic.dokter.model.updateRecipe.ResponseUpdateResep;
import com.haloqlinic.dokter.model.updateStatus.ResponseUpdateStatus;
import com.haloqlinic.dokter.model.withdrawal.ResponseWithdrawal;

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
                                          @Field("harga") String harga);

    @FormUrlEncoded
    @POST("recipe.php")
    Call<ResponseDataRecipe> dataResep(@Field("id_transaksi") String id_transaksi);

    @FormUrlEncoded
    @POST("update_recipe.php")
    Call<ResponseUpdateResep> updateResep(@Field("id_transaksi") String id_transaksi,
                                          @Field("id_pesan[]") ArrayList<String> id_pesan,
                                          @Field("jumlah[]") ArrayList<String> jumlah,
                                          @Field("harga[]") ArrayList<String> harga,
                                          @Field("berat_item[]") ArrayList<String> berat_item);

    @Multipart
    @POST("update_photo.php")
    Call<ResponseUpdatePhoto> updatePhoto(@Part("id_customer") RequestBody id_customer,
                                          @Part MultipartBody.Part file);

}
