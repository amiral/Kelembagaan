package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Provinsi;

/**
 * Created by Amiral on 6/6/17.
 */

public class ProvinsiDbHelper {

    private static final String TAG = "ProvinsiHelper";


    private Realm realm;
    private RealmResults<Provinsi> realmResult;
    public Context context;

    /**
     * constructor untuk membuat instance realm
     *
     * @param context
     */
    public ProvinsiDbHelper(Context context) {
        realm = Realm.getDefaultInstance();
        this.context = context;
    }


    public void addManyProvinsi(List<Provinsi> lsProvinsi) {

        realm.beginTransaction();

        for (Provinsi p : lsProvinsi) {
            realm.copyToRealmOrUpdate(p);
            showLog("Added ; " + p.getNamaProvinsi());
//            showToast(kb.getNamaKabupaten() + " berhasil disimpan.");
        }
        realm.commitTransaction();


    }

    public void addProvinsi(Provinsi p) {

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(p);
        realm.commitTransaction();

//        showLog("Added ; " + p.getNamaProvinsi());
//        showToast(p.getNamaProvinsi() + " berhasil disimpan.");
    }


    /**
     * method mencari semua kabupaten
     */
    public ArrayList<Provinsi> findAllProvinsi() {
        ArrayList<Provinsi> data = new ArrayList<>();


        realmResult = realm.where(Provinsi.class).findAll();
        realmResult.sort("namaProvinsi", Sort.ASCENDING);
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

    public Provinsi getProvinsi(int id){
        Provinsi p;

        p = realm.where(Provinsi.class).equalTo("idProvinsi", id).findFirst();

        return p;
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
