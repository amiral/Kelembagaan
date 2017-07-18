package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Amiral on 7/17/17.
 */

public class JumlahSantriMukim extends RealmObject{
    @SerializedName("mukim")
    @Expose
    private Integer mukim;
    @SerializedName("tidak_mukim")
    @Expose
    private Integer tidakMukim;

    public Integer getMukim() {
        return mukim;
    }

    public void setMukim(Integer mukim) {
        this.mukim = mukim;
    }

    public Integer getTidakMukim() {
        return tidakMukim;
    }

    public void setTidakMukim(Integer tidakMukim) {
        this.tidakMukim = tidakMukim;
    }
}
