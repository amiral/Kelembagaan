package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.local;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Lembaga;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.utils.Tools;

/**
 * Created by Amiral on 6/6/17.
 */

public class LembagaDbHelper {

    private static final String TAG = "LembagaHelper";


    private Realm realm;
    private RealmResults<Lembaga> realmResult;
    public Context context;

    /**
     * constructor untuk membuat instance realm
     *
     * @param context
     */
    public LembagaDbHelper(Context context) {
        realm = Realm.getDefaultInstance();
        this.context = context;
    }




    public void addManyLembaga(List<Lembaga> lsLembaga) {

        realm.beginTransaction();

        for (Lembaga lembaga : lsLembaga) {
            realm.copyToRealmOrUpdate(lembaga);
            showLog("Added ; " + lembaga.getNamaLembaga());
        }
        realm.commitTransaction();


    }


    public void addLembaga(Lembaga lembaga) {

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(lembaga);
        realm.commitTransaction();

    }


    /**
     * method mencari semua Pesantren
     */
    public ArrayList<Lembaga> getAllLembaga() {
        ArrayList<Lembaga> data = new ArrayList<>();


        realmResult = realm.where(Lembaga.class).findAll();
        realmResult.sort("namaLembaga", Sort.ASCENDING);
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

    public ArrayList<Lembaga> getAllLembagaPesantren(String nspp) {
        ArrayList<Lembaga> data = new ArrayList<>();


        realmResult = realm.where(Lembaga.class).equalTo("nspp", nspp).findAll();
        realmResult.sort("idJenjangLembaga", Sort.ASCENDING);
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());
            for (int i = 0; i < realmResult.size(); i++) {
                data.add(realmResult.get(i));
            }

        } else {
//            showLog("Size : 0");
//            showToast("Data Kosong!");
        }

        return data;
    }
    /**
     * method mencari semua Pesantren
     */
    public ArrayList<Lembaga> getAllBookmarkLembaga() {
        ArrayList<Lembaga> data = new ArrayList<>();

        RealmQuery<Lembaga> query = realm.where(Lembaga.class);
        query.equalTo("isFavorit", 1);

        realmResult = query.findAll();
        realmResult.sort("namaLembaga", Sort.ASCENDING);
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


//   public void updateLembaga (Pesantren p){
//       realm.beginTransaction();
//       Pesantren toEdit = realm.where(Pesantren.class)
//               .equalTo("idPesantren", p.getIdPesantren()).findFirst();
//       toEdit.setIsFavorit(p.getIsFavorit());
//       realm.commitTransaction();
//   }

    public void setBookmark (Lembaga p, int intBookmark){
        realm.beginTransaction();
        Lembaga toEdit = realm.where(Lembaga.class)
                .equalTo("idLembaga", p.getIdLembaga()).findFirst();
        toEdit.setIsFavorit(intBookmark);
        realm.commitTransaction();
    }


    public Lembaga getLembaga(int id){
        Lembaga kb;

        Realm realm = Realm.getDefaultInstance();
        try {
            kb = realm.where(Lembaga.class).equalTo("idLembaga", id).findFirst();
            // do something with the person ...
        } finally {
            realm.close();
        }

        return kb;
    }

    public ArrayList<Lembaga> getAllWarningLembaga(int jumlahHari) {
        ArrayList<Lembaga> data = new ArrayList<>();

        realmResult = realm.where(Lembaga.class).findAll();
        realmResult.sort("namaLembaga", Sort.ASCENDING);
        if (realmResult.size() > 0) {
            showLog("Size : " + realmResult.size());
            for (int i = 0; i < realmResult.size(); i++) {
                String masaBerlaku = realmResult.get(i).getMasaBerlakuIjinOperasional();
                long sisa = Tools.sisaHariMasaberlaku(masaBerlaku);
                if (sisa <= jumlahHari && sisa > 0) {
                    data.add(realmResult.get(i));
                }
            }

        } else {
            showLog("Size : 0");
            showToast("Database Kosong!");
        }

        return data;
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
