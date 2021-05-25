package com.haloqlinic.dokter.api;

import com.haloqlinic.dokter.model.editAkun.ResponseEditAkun;
import com.haloqlinic.dokter.model.kecamatan.ResponseDataKecamatan;
import com.haloqlinic.dokter.model.kota.ResponseDatakota;
import com.haloqlinic.dokter.model.listKonsultasi.ResponseDataKonsultasi;
import com.haloqlinic.dokter.model.listWithDrawal.ResponseListWithDrawal;
import com.haloqlinic.dokter.model.login.ResponseItem;
import com.haloqlinic.dokter.model.login.ResponseLogin;
import com.haloqlinic.dokter.model.provinsi.ResponseDataProvinsi;
import com.haloqlinic.dokter.model.resetPassword.ResponseResetPassword;
import com.haloqlinic.dokter.model.saldo.ResponseDataSaldo;
import com.haloqlinic.dokter.model.updateKonsultasi.ResponseUpdateKonsultasi;
import com.haloqlinic.dokter.model.updatePassword.ResponseUpdatePassword;
import com.haloqlinic.dokter.model.withdrawal.ResponseWithdrawal;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("signin.php")
    Call<ResponseLogin> login(@Field("email") String email,
                              @Field("password") String password);

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
}
