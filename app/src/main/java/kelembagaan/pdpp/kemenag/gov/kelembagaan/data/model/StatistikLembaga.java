package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Amiral on 7/17/17.
 */

public class StatistikLembaga extends RealmObject{

    @PrimaryKey
    @SerializedName("id_lembaga")
    @Expose
    private Integer idLembaga;
    @SerializedName("nama_lembaga")
    @Expose
    private String namaLembaga;
    @SerializedName("jumlah_santri_mukim")
    @Expose
    private JumlahSantriMukim jumlahSantriMukim;
    @SerializedName("jumlah_santri")
    @Expose
    private JumlahSantri jumlahSantri;

    public Integer getIdLembaga() {
        return idLembaga;
    }

    public void setIdLembaga(Integer idLembaga) {
        this.idLembaga = idLembaga;
    }

    public String getNamaLembaga() {
        return namaLembaga;
    }

    public void setNamaLembaga(String namaLembaga) {
        this.namaLembaga = namaLembaga;
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
