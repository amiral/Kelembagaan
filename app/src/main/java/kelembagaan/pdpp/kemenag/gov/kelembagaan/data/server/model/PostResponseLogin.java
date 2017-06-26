package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Pengguna;

/**
 * Created by Amiral on 6/24/17.
 */

public class PostResponseLogin {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("pengguna")
    @Expose
    private Pengguna pengguna;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Pengguna getPengguna() {
        return pengguna;
    }

    public void setPengguna(Pengguna pengguna) {
        this.pengguna = pengguna;
    }

}
