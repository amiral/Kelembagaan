package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amiral on 7/10/17.
 */

public class PostResponseForgotPassword {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("pesan")
    @Expose
    private String pesan;

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
}
