package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui;

import android.app.Application;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by Amiral on 6/6/17.
 */

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
//        //kode konfigurasi Realm
//        RealmConfiguration config = new RealmConfiguration.Builder()
//                //versi database
//                .schemaVersion(0)
//                .migration(new DataMigration())
//                .build();
//
//        Realm.setDefaultConfiguration(config);

    }

    private class DataMigration implements RealmMigration {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

            //Mengambil schema
            RealmSchema schema = realm.getSchema();

            //membuat schema baru jika versi 0
            if (oldVersion == 0) {
//                schema.create("Article")
//                        .addField("id", int.class)
//                        .addField("title", String.class)
//                        .addField("description", String.class);
                oldVersion++;
            }

        }
    }
}
