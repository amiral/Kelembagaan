package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Amiral on 7/17/17.
 */

public class StatistikPesantren extends RealmObject{

    @PrimaryKey
    @SerializedName("id_pesantren")
    @Expose
    private Integer idPesantren;
    @SerializedName("nspp")
    @Expose
    private String nspp;
    @SerializedName("jumlah_santri_mukim")
    @Expose
    private JumlahSantriMukim jumlahSantriMukim;
    @SerializedName("jumlah_santri")
    @Expose
    private JumlahSantri jumlahSantri;

    public Integer getIdPesantren() {
        return idPesantren;
    }

    public void setIdPesantren(Integer idPesantren) {
        this.idPesantren = idPesantren;
    }

    public String getNspp() {
        return nspp;
    }

    public void setNspp(String nspp) {
        this.nspp = nspp;
    }

    public JumlahSantriMukim getJumlahSantriMukim() {
        return jumlahSantriMukim;
    }

    public void setJumlahSantriMukim(JumlahSantriMukim jumlahSantriMukim) {
        this.jumlahSantriMukim = jumlahSantriMukim;
    }

    public JumlahSantri getJumlahSantri() {
        return jumlahSantri;
    }

    public void setJumlahSantri(JumlahSantri jumlahSantri) {
        this.jumlahSantri = jumlahSantri;
    }
}
