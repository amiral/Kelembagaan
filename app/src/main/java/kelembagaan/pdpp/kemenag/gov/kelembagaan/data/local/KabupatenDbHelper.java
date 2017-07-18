package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Kabupaten;

/**
 * Created by Amiral on 6/6/17.
 */

public class KabupatenDbHelper {

    private static final String TAG = "KabupatenHelper";


    private Realm realm;
    private RealmResults<Kabupaten> realmResult;
    public Context context;

    /**
     * constructor untuk membuat instance realm
     *
     * @param context
     */
    public KabupatenDbHelper(Context context) {
        realm = Realm.getDefaultInstance();
        this.context = context;
    }




    public void addManyKabupaten(List<Kabupaten> lsKabupaten) {

        realm.beginTransaction();

        for (Kabupaten kb : lsKabupaten) {
            realm.copyToRealmOrUpdate(kb);
            showLog("Added ; " + kb.getNamaKabupaten());
//            showToast(kb.getNamaKabupaten() + " berhasil disimpan.");
        }
        realm.commitTransaction();


    }


    public void addKabupaten(Kabupaten kb) {

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(kb);
        realm.commitTransaction();

        showLog("Added ; " + kb.getNamaKabupaten());
//        showToast(kb.getNamaKabupaten() + " berhasil disimpan.");
    }


    /**
     * method mencari semua kabupaten provinsi
     */
    public ArrayList<Kabupaten> findAllProvinsiKabupaten(int idProvinsi) {
        ArrayList<Kabupaten> data = new ArrayList<>();


        realmResult = realm.where(Kabupaten.class).equalTo("provinsiIdProvinsi", idProvinsi).findAll();
        realmResult.sort("namaKabupaten", Sort.ASCENDING);
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());
            for (int i = 0; i < realmResult.size(); i++) {
                data.add(realmResult.get(i));
            }

        } else {
            showLog("Size : 0");
//            showToast("Database Kosong!");
        }

        return data;
    }

    public ArrayList<Kabupaten> findAllKabupaten() {
        ArrayList<Kabupaten> data = new ArrayList<>();


        realmResult = realm.where(Kabupaten.class).findAll();
        realmResult.sort("namaKabupaten", Sort.ASCENDING);
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());
            for (int i = 0; i < realmResult.size(); i++) {
                data.add(realmResult.get(i));
            }

        } else {
            showLog("Size : 0");
//            showToast("Database Kosong!");
        }

        return data;
    }

    /**
     * method mencari semua kabupaten
     */
    /*public ArrayList<Kabupaten> findAllProvinsiKabupaten(int idProvinsi, ArrayList<Integer> filteredKabupaten) {
        ArrayList<Kabupaten> data = new ArrayList<>();


        realmResult = realm.where(Kabupaten.class).equalTo("provinsiIdProvinsi", idProvinsi).findAll();
        realmResult.sort("namaKabupaten", Sort.ASCENDING);
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());
            for (int i = 0; i < realmResult.size(); i++) {

                if (filteredKabupaten.contains(realmResult.get(i).getIdKabupaten()))
                    realmResult.get(i).setSelected(true);
                data.add(realmResult.get(i));
            }

        } else {
            showLog("Size : 0");
            showToast("Database Kosong!");
        }

        return data;
    }*/


    public Kabupaten getKabupaten(int id){
        Kabupaten kb;

        kb = realm.where(Kabupaten.class).equalTo("idKabupaten", id).findFirst();

        return kb;
    }

    /**
     * membuat log
     *
     * @param s
     */
    private void showLog(String s) {
        Log.d(TAG, s);

    }

    /**
     * Membuat Toast Informasi
     */
//    private void showToast(String s) {
//        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
//    }
}
