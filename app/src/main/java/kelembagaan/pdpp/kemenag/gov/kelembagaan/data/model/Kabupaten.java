package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Amiral on 6/6/17.
 */

public class Kabupaten extends RealmObject{

    @PrimaryKey
    @SerializedName("id_kabupaten")
    @Expose
    private Integer idKabupaten;
    @SerializedName("nama_kabupaten")
    @Expose
    private String namaKabupaten;
    @SerializedName("provinsi_id_provinsi")
    @Expose
    private Integer provinsiIdProvinsi;
    @SerializedName("jumlah_pesantren_kabupaten")
    @Expose
    private int jumlahPesantrenKabupaten;

    public int getIdKabupaten() {
        return idKabupaten;
    }

    public void setIdKabupaten(Integer idKabupaten) {
        this.idKabupaten = idKabupaten;
    }

    public String getNamaKabupaten() {
        return namaKabupaten;
    }

    public void setNamaKabupaten(String namaKabupaten) {
        this.namaKabupaten = namaKabupaten;
    }

    public int getProvinsiIdProvinsi() {
        return provinsiIdProvinsi;
    }

    public void setProvinsiIdProvinsi(Integer provinsiIdProvinsi) {
        this.provinsiIdProvinsi = provinsiIdProvinsi;
    }

    public Integer getJumlahPesantrenKabupaten() {
        return jumlahPesantrenKabupaten;
    }

    public void setJumlahPesantrenKabupaten(Integer jumlahPesantrenKabupaten) {
        this.jumlahPesantrenKabupaten = jumlahPesantrenKabupaten;
    }
}
