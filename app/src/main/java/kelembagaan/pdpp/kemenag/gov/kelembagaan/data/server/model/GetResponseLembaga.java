package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Lembaga;

/**
 * Created by Amiral on 6/5/17.
 */

public class GetResponseLembaga {

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
    private List<Lembaga> data = null;

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

    public List<Lembaga> getData() {
        return data;
    }

    public void setData(List<Lembaga> data) {
        this.data = data;
    }
}
