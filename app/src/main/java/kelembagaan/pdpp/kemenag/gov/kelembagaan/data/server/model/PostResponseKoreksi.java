package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amiral on 7/15/17.
 */

public class PostResponseKoreksi {

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
    private Boolean data;

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

    public Boolean getData() {
        return data;
    }

    public void setData(Boolean data) {
        this.data = data;
    }
}
