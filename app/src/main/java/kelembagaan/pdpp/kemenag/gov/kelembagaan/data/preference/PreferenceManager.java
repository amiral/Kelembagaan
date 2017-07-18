package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Pengguna;

/**
 * Created by Amiral on 4/21/17.
 */

public class PreferenceManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "kelembagaan";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_LOGIN = "IsLogin";
    private static final String LAST_UPDATE_PESANTREN = "lastUpdatePesantren";
    private static final String LAST_UPDATE_LEMBAGA = "lastUpdateLembaga";
    private static final String LAST_UPDATE_LAPORAN_LEMBAGA = "lastUpdateLaporanLembaga";
    private static final String USER = "pengguna";
    private static final String FILTER_KABUPATEN = "kabupatenId";
    private static final String FILTER_LEMBAGA = "tipeLembaga";
    private static final String FILTER_JENJANG = "jenjangId";
    private static final String LAST_UPDATE_STATISTIK_PESANTREN = "lastUpdateStatistikPesantren";
    private static final String LAST_UPDATE_STATISTIK_LEMBAGA = "lastUpdateStatistikLembaga";

    public PreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setLogin(boolean isLogin) {
        editor.putBoolean(IS_LOGIN, isLogin);
        editor.commit();
    }

    public boolean isLogin() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void setLastUpdatePesantren(String lastUpdate){
        editor.putString(LAST_UPDATE_PESANTREN,lastUpdate);
        editor.commit();
    }

    public String getLastUpdatePesantren(){
        return pref.getString(LAST_UPDATE_PESANTREN, "2016-01-01 00:00:00");
    }

    public void setLastUpdateLembaga(String lastUpdate){
        editor.putString(LAST_UPDATE_LEMBAGA,lastUpdate);
        editor.commit();
    }

    public String getLastUpdateLembaga(){
        return pref.getString(LAST_UPDATE_LEMBAGA, "2016-01-01 00:00:00");
    }

    public void setLastUpdateLaporanLembaga(String lastUpdate){
        editor.putString(LAST_UPDATE_LAPORAN_LEMBAGA,lastUpdate);
        editor.commit();
    }

    public String getLastUpdateLaporanLembaga(){
        return pref.getString(LAST_UPDATE_LAPORAN_LEMBAGA, "2016-01-01 00:00:00");
    }

    public void setLastUpdateStatistikLembaga(String lastUpdate){
        editor.putString(LAST_UPDATE_STATISTIK_LEMBAGA,lastUpdate);
        editor.commit();
    }

    public String getLastUpdateStatistikLembaga(){
        return pref.getString(LAST_UPDATE_STATISTIK_LEMBAGA, "2016-01-01 00:00:00");
    }

    public void setLastUpdateStatistikPesantren(String lastUpdate){
        editor.putString(LAST_UPDATE_STATISTIK_PESANTREN,lastUpdate);
        editor.commit();
    }

    public String getLastUpdateStatistikPesantren(){
        return pref.getString(LAST_UPDATE_STATISTIK_PESANTREN, "2016-01-01 00:00:00");
    }

    public void setPengguna(Pengguna p){
        editor.putInt(USER+"1", p.getIdPengguna());
        editor.putString(USER+"2", p.getNama());
        editor.putInt(USER+"3", p.getKodeProvinsi());
        editor.putInt(USER+"4", p.getKodeKabupaten());
        editor.putString(USER+"5", p.getEmail());
        editor.putString(USER+"6", p.getToken());
        editor.putString(USER+"7", p.getApiToken());
        editor.putInt(USER+"8", p.getHakAksesId());
        editor.putString(USER+"9", p.getCreatedAt());
        editor.putString(USER+"10", p.getUpdatedAt());
        editor.putString(USER+"11", p.getNamaHakAkses());
        editor.commit();
    }

    public void clearPengguna(){
        editor.putInt(USER+"1", 0);
        editor.putString(USER+"2", "");
        editor.putInt(USER+"3", 0);
        editor.putInt(USER+"4", 0);
        editor.putString(USER+"5", "");
        editor.putString(USER+"6", "");
        editor.putString(USER+"7", "");
        editor.putInt(USER+"8", 0);
        editor.putString(USER+"9", "");
        editor.putString(USER+"10", "");
        editor.putString(USER+"11", "");
        editor.commit();
    }

    public Pengguna getPengguna(){
        Pengguna p = new Pengguna();
        p.setIdPengguna(pref.getInt(USER+"1",0));
        p.setNama(pref.getString(USER+"2",""));
        p.setKodeProvinsi(pref.getInt(USER+"3",0));
        p.setKodeKabupaten(pref.getInt(USER+"4",0));
        p.setEmail(pref.getString(USER+"5",""));
        p.setToken(pref.getString(USER+"6",""));
        p.setApiToken(pref.getString(USER+"7",""));
        p.setHakAksesId(pref.getInt(USER+"8",0));
        p.setCreatedAt(pref.getString(USER+"9",""));
        p.setUpdatedAt(pref.getString(USER+"10",""));
        p.setNamaHakAkses(pref.getString(USER+"11",""));

        return p;
    }

    public void setFilterKabupaten(ArrayList<Integer> listKabupatenId){
        Gson gson = new Gson();
        String json = gson.toJson(listKabupatenId);
        editor.putString(FILTER_KABUPATEN, json);
        editor.commit();
    }

    public ArrayList<Integer> getFilterKabupaten(){
        Gson gson = new Gson();
        String json = pref.getString(FILTER_KABUPATEN, null);
        Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
        ArrayList<Integer> arrayList = gson.fromJson(json, type) ;

        arrayList = arrayList == null ? new ArrayList<Integer>() : arrayList;
        return arrayList;
    }

    public void setFilterJenjang(ArrayList<Integer> listJijangId){
        Gson gson = new Gson();
        String json = gson.toJson(listJijangId);
        editor.putString(FILTER_JENJANG, json);
        editor.commit();
    }

    public ArrayList<Integer> getFilterJenjang(){
        Gson gson = new Gson();
        String json = pref.getString(FILTER_JENJANG, null);
        Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
        ArrayList<Integer> arrayList = gson.fromJson(json, type);
        arrayList = arrayList == null ? new ArrayList<Integer>() : arrayList;
        return arrayList;
    }

    public void setFilterLembaga(ArrayList<Integer> listTipeLembaga){
        Gson gson = new Gson();
        String json = gson.toJson(listTipeLembaga);
        editor.putString(FILTER_LEMBAGA, json);
        editor.commit();
    }

    public ArrayList<Integer> getFilterLembaga(){
        Gson gson = new Gson();
        String json = pref.getString(FILTER_LEMBAGA, null);
        Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
        ArrayList<Integer> arrayList = gson.fromJson(json, type);
        arrayList = arrayList == null ? new ArrayList<Integer>() : arrayList;
        return arrayList;
    }
}
