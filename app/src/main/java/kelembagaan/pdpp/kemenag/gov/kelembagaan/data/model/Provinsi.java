package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Amiral on 6/6/17.
 */

public class Provinsi extends RealmObject{
    @PrimaryKey
    @SerializedName("id_provinsi")
    @Expose
    private Integer idProvinsi;
    @SerializedName("nama_provinsi")
    @Expose
    private String namaProvinsi;
    @SerializedName("jumlah_pesantren_provinsi")
    @Expose
    private Integer jumlahPesantrenProvinsi;

    public Integer getIdProvinsi() {
        return idProvinsi;
    }

    public void setIdProvinsi(Integer idProvinsi) {
        this.idProvinsi = idProvinsi;
    }

    public String getNamaProvinsi() {
        return namaProvinsi;
    }

    public void setNamaProvinsi(String namaProvinsi) {
        this.namaProvinsi = namaProvinsi;
    }

    public Integer getJumlahPesantrenProvinsi() {
        return jumlahPesantrenProvinsi;
    }

    public void setJumlahPesantrenProvinsi(Integer jumlahPesantrenProvinsi) {
        this.jumlahPesantrenProvinsi = jumlahPesantrenProvinsi;
    }

}
