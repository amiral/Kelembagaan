package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Kabupaten;

/**
 * Created by Amiral on 6/6/17.
 */

public class GetResponseKabupaten {

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
    private List<Kabupaten> data = null;

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

    public List<Kabupaten> getData() {
        return data;
    }

    public void setData(List<Kabupaten> data) {
        this.data = data;
    }
}
