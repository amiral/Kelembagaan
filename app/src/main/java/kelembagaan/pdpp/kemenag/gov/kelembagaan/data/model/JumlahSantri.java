package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Amiral on 7/17/17.
 */

public class JumlahSantri extends RealmObject{
    @SerializedName("pria")
    @Expose
    private Integer pria;
    @SerializedName("wanita")
    @Expose
    private Integer wanita;

    public Integer getPria() {
        return pria;
    }

    public void setPria(Integer pria) {
        this.pria = pria;
    }

    public Integer getWanita() {
        return wanita;
    }

    public void setWanita(Integer wanita) {
        this.wanita = wanita;
    }
}
