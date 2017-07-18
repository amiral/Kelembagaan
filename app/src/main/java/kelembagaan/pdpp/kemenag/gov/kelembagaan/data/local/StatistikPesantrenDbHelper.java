package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.StatistikPesantren;

/**
 * Created by Amiral on 6/6/17.
 */

public class StatistikPesantrenDbHelper {

    private static final String TAG = "LaporanHelper";


    private Realm realm;
    private RealmResults<StatistikPesantren> realmResult;
    public Context context;

    /**
     * constructor untuk membuat instance realm
     *
     * @param context
     */
    public StatistikPesantrenDbHelper(Context context) {
        realm = Realm.getDefaultInstance();
        this.context = context;
    }




    public void addManyStatistikPesantren(List<StatistikPesantren> lsStatistik) {

        realm.beginTransaction();

        for (StatistikPesantren statistik : lsStatistik) {
            realm.copyToRealmOrUpdate(statistik);
        }
        realm.commitTransaction();


    }




    public ArrayList<StatistikPesantren> getAllStatistikPesantren() {
        ArrayList<StatistikPesantren> data = new ArrayList<>();


        realmResult = realm.where(StatistikPesantren.class).findAll();
        realmResult.sort("idPesantren", Sort.ASCENDING);
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


    public StatistikPesantren getStatistikPesantren(int id){
        StatistikPesantren kb;

        Realm realm = Realm.getDefaultInstance();
        try {
            kb = realm.where(StatistikPesantren.class).equalTo("idPesantren", id).findFirst();
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
