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
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Pesantren;

/**
 * Created by Amiral on 6/6/17.
 */

public class PesantrenDbHelper {

    private static final String TAG = "PesantrenHelper";


    private Realm realm;
    private RealmResults<Pesantren> realmResult;
    public Context context;

    /**
     * constructor untuk membuat instance realm
     *
     * @param context
     */
    public PesantrenDbHelper(Context context) {
        realm = Realm.getDefaultInstance();
        this.context = context;
    }




    public void addManyPesantren(List<Pesantren> lsPesantren) {

        realm.beginTransaction();

        for (Pesantren kb : lsPesantren) {
            realm.copyToRealmOrUpdate(kb);
            showLog("Added ; " + kb.getNamaPesantren());
//            showToast(kb.getNamaPesantren() + " berhasil disimpan.");
        }
        realm.commitTransaction();


    }


    public void addPesantren(Pesantren kb) {

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(kb);
        realm.commitTransaction();

//        showLog("Added ; " + kb.getNamaPesantren());
//        showToast(kb.getNamaPesantren() + " berhasil disimpan.");
    }


    /**
     * method mencari semua Pesantren
     */
    public ArrayList<Pesantren> getAllPesantren() {
        ArrayList<Pesantren> data = new ArrayList<>();


        realmResult = realm.where(Pesantren.class).findAll();
        realmResult.sort("namaPesantren", Sort.ASCENDING);
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
     * method mencari semua Pesantren
     */
    public ArrayList<Pesantren> getAllBookmarkPesantren() {
        ArrayList<Pesantren> data = new ArrayList<>();

        RealmQuery<Pesantren> query = realm.where(Pesantren.class);
        query.equalTo("isFavorit", 1);

        realmResult = query.findAll();
        realmResult.sort("namaPesantren", Sort.ASCENDING);
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

    public boolean setBookmark(final int idPesantren, final int statusBookmark){
        final boolean[] isBookmark = {false};

        // Asynchronously update objects on a background thread
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Pesantren dog = bgRealm.where(Pesantren.class).equalTo("idPesantren", idPesantren).findFirst();
                dog.setIsFavorit(statusBookmark);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Original queries and Realm objects are automatically updated.
                isBookmark[0] = true;
            }
        });

        return isBookmark[0];
    }

//   public void updatePesantren (Pesantren p){
//       realm.beginTransaction();
//       Pesantren toEdit = realm.where(Pesantren.class)
//               .equalTo("idPesantren", p.getIdPesantren()).findFirst();
//       toEdit.setIsFavorit(p.getIsFavorit());
//       realm.commitTransaction();
//   }

    public void setBookmark (Pesantren p, int intBookmark){
        realm.beginTransaction();
        Pesantren toEdit = realm.where(Pesantren.class)
                .equalTo("idPesantren", p.getIdPesantren()).findFirst();
        toEdit.setIsFavorit(intBookmark);
        realm.commitTransaction();
    }


    public Pesantren getPesantren(int id){
        Pesantren kb;

        Realm realm = Realm.getDefaultInstance();
        try {
            kb = realm.where(Pesantren.class).equalTo("idPesantren", id).findFirst();
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
