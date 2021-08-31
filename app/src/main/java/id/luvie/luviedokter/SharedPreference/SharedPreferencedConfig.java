package id.luvie.luviedokter.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencedConfig {

    public static final String PREFERENCE_HQ_APP_DOKTER = "prefHaloQlinicAppDokter";
    public static final String PREFERENCE_ID_DOKTER = "prefIdDokter";
    public static final String PREFERENCE_UID = "prefUid";
    public static final String PREFERENCE_TOKEN = "prefToken";
    public static final String PREFERENCE_PLAYER_ID = "prefPlayerId";
    public static final String PREFERENCE_ID_KATEGORI = "prefIdKategori";
    public static final String PREFERENCE_KODE = "prefKode";
    public static final String PREFERENCE_EMAIL = "prefEmail";
    public static final String PREFERENCE_NAMA = "prefNama";
    public static final String PREFERENCE_ALAMAT = "prefAlamat";
    public static final String PREFERENCE_NO_HP = "prefNoHp";
    public static final String PREFERENCE_ID_KECAMATAN = "prefIdKecamatan";
    public static final String PREFERENCE_ID_KOTA = "prefIdKota";
    public static final String PREFERENCE_ID_PROVINSI = "prefIdProvinsi";
    public static final String PREFERENCE_SIP = "prefSip";
    public static final String PREFERENCE_SPESIALIS = "prefSpesialis";
    public static final String PREFERENCE_JK = "prefJk";
    public static final String PREFERENCE_TENTANG = "prefTentang";
    public static final String PREFERENCE_IMG = "prefImg";
    public static final String PREFERENCE_STATUS = "prefStatus";
    public static final String PREFERENCE_PROVINSI = "prefProvinsi";
    public static final String PREFERENCE_KOTA = "prefKota";
    public static final String PREFERENCE_KECAMATAN = "prefKecamatan";
    public static final String PREFERENCE_POSITION_FRAGMENT = "prefPositionFragment";
    public static final String PREFERENCE_IS_LOGIN = "prefIsLogin";

    SharedPreferences preferences;
    SharedPreferences.Editor preferencesEditor;

    public SharedPreferencedConfig(Context context) {

        preferences = context.getSharedPreferences(PREFERENCE_HQ_APP_DOKTER, Context.MODE_PRIVATE);
        preferencesEditor = preferences.edit();
    }

    public void savePrefString(String keyPref, String value){
        preferencesEditor.putString(keyPref, value);
        preferencesEditor.commit();
    }

    public void savePrefInt(String keyPref, int value){
        preferencesEditor.putInt(keyPref, value);
        preferencesEditor.commit();
    }

    public void savePrefBoolean(String keyPref, boolean value){
        preferencesEditor.putBoolean(keyPref, value);
        preferencesEditor.commit();
    }

    public String getPreferenceIdDokter(){
        return preferences.getString(PREFERENCE_ID_DOKTER, "");
    }

    public String getPreferenceKode(){
        return preferences.getString(PREFERENCE_KODE, "");
    }

    public String getPreferenceNama(){
        return preferences.getString(PREFERENCE_NAMA, "");
    }

    public String getPreferenceEmail(){
        return preferences.getString(PREFERENCE_EMAIL, "");
    }

    public String getPreferenceNoHp(){
        return preferences.getString(PREFERENCE_NO_HP, "");
    }

    public String getPreferenceJk(){
        return preferences.getString(PREFERENCE_JK, "");
    }

    public String getPreferenceImg(){
        return preferences.getString(PREFERENCE_IMG, "");
    }

    public String getPreferenceProvinsi(){
        return preferences.getString(PREFERENCE_PROVINSI, "");
    }

    public String getPreferenceKota(){
        return preferences.getString(PREFERENCE_KOTA, "");
    }

    public String getPreferenceKecamatan(){
        return preferences.getString(PREFERENCE_KECAMATAN, "");
    }

    public String getPreferenceAlamat(){
        return preferences.getString(PREFERENCE_ALAMAT, "");
    }

    public String getPreferenceIdProvinsi(){
        return preferences.getString(PREFERENCE_ID_PROVINSI, "");
    }

    public String getPreferenceIdKota(){
        return preferences.getString(PREFERENCE_ID_KOTA, "");
    }

    public String getPreferenceIdKecamatan(){
        return preferences.getString(PREFERENCE_ID_KECAMATAN, "");
    }

    public String getPreferenceUid() {
        return preferences.getString(PREFERENCE_UID, "");
    }

    public String getPreferenceToken() {
        return preferences.getString(PREFERENCE_TOKEN, "");
    }

    public String getPreferencePlayerId() {
        return preferences.getString(PREFERENCE_PLAYER_ID, "");
    }

    public String getPreferenceIdKategori() {
        return preferences.getString(PREFERENCE_ID_KATEGORI, "");
    }

    public String getPreferenceSip() {
        return preferences.getString(PREFERENCE_SIP, "");
    }

    public String getPreferenceSpesialis() {
        return preferences.getString(PREFERENCE_SPESIALIS, "");
    }

    public static String getPreferenceTentang() {
        return PREFERENCE_TENTANG;
    }

    public String getPreferenceStatus() {
        return preferences.getString(PREFERENCE_STATUS, "");
    }

    public String getPreferencePositionFragment(){
        return preferences.getString(PREFERENCE_POSITION_FRAGMENT, "");
    }

    public Boolean getPreferenceIsLogin(){
        return preferences.getBoolean(PREFERENCE_IS_LOGIN, false);
    }

}
