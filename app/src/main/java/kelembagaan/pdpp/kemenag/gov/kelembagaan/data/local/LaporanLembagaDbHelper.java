package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Laporan;

/**
 * Created by Amiral on 6/6/17.
 */

public class LaporanLembagaDbHelper {

    private static final String TAG = "LaporanHelper";


    private Realm realm;
    private RealmResults<Laporan> realmResult;
    public Context context;

    /**
     * constructor untuk membuat instance realm
     *
     * @param context
     */
    public LaporanLembagaDbHelper(Context context) {
        realm = Realm.getDefaultInstance();
        this.context = context;
    }




    public void addManyLaporan(List<Laporan> lsLaporan) {

        realm.beginTransaction();

        for (Laporan laporan : lsLaporan) {
            realm.copyToRealmOrUpdate(laporan);
        }
        realm.commitTransaction();


    }



    /**
     * method getLaporan
     */
    public ArrayList<Laporan> getMissingLaporan() {
        ArrayList<Laporan> data = new ArrayList<>();


        realmResult = realm.where(Laporan.class).equalTo("missing", 1).findAll();
        realmResult.sort("createdAt", Sort.ASCENDING);
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());
            for (int i = 0; i < realmResult.size(); i++) {
                    data.add(realmResult.get(i));
            }

        } else {
            showLog("Size : 0");
            showToast("Database Kosong!");
        }

        return data;
    }


    /**
     * method getLaporan
     */
    public ArrayList<Laporan> getTidakAkuratLaporan() {
        ArrayList<Laporan> data = new ArrayList<>();


        realmResult = realm.where(Laporan.class).equalTo("invalid", 1).findAll();
        realmResult.sort("createdAt", Sort.ASCENDING);
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());
            for (int i = 0; i < realmResult.size(); i++) {
                data.add(realmResult.get(i));
            }

        } else {
            showLog("Size : 0");
            showToast("Database Kosong!");
        }

        return data;
    }

    public ArrayList<Laporan> getDuplikatLaporan() {
        ArrayList<Laporan> data = new ArrayList<>();


        realmResult = realm.where(Laporan.class).equalTo("duplicate", 1).findAll();
        realmResult.sort("createdAt", Sort.ASCENDING);
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());
            for (int i = 0; i < realmResult.size(); i++) {
                data.add(realmResult.get(i));
            }

        } else {
            showLog("Size : 0");
            showToast("Database Kosong!");
        }

        return data;
    }

    public ArrayList<Laporan> getAllLaporan() {
        ArrayList<Laporan> data = new ArrayList<>();


        realmResult = realm.where(Laporan.class).findAll();
        realmResult.sort("createdAt", Sort.ASCENDING);
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());
            for (int i = 0; i < realmResult.size(); i++) {
                data.add(realmResult.get(i));
            }

        } else {
            showLog("Size : 0");
            showToast("Database Kosong!");
        }

        return data;
    }


    public Laporan getLaporan(int id){
        Laporan kb;

        Realm realm = Realm.getDefaultInstance();
        try {
            kb = realm.where(Laporan.class).equalTo("idFlagLembaga", id).findFirst();
            // do something with the person ...
        } finally {
            realm.close();
        }

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
    private void showToast(String s) {
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
    }
}
