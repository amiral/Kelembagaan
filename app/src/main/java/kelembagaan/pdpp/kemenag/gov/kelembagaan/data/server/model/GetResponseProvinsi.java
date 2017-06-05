package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Provinsi;

/**
 * Created by Amiral on 6/6/17.
 */

public class GetResponseProvinsi {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("pesan")
    @Expose
    private String pesan;
    @SerializedName("tanggal")
    @Expose
    private Tanggal tanggal;
    @SerializedName("data")
    @Expose
    private List<Provinsi> data = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public Tanggal getTanggal() {
        return tanggal;
    }

    public void setTanggal(Tanggal tanggal) {
        this.tanggal = tanggal;
    }

    public List<Provinsi> getData() {
        return data;
    }

    public void setData(List<Provinsi> data) {
        this.data = data;
    }

}
