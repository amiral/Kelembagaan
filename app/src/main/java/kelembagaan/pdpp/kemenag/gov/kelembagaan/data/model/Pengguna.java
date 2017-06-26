package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amiral on 6/24/17.
 */

public class Pengguna {
    @SerializedName("id_pengguna")
    @Expose
    private Integer idPengguna;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("kode_provinsi")
    @Expose
    private Integer kodeProvinsi;
    @SerializedName("kode_kabupaten")
    @Expose
    private Integer kodeKabupaten;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("hak_akses_id")
    @Expose
    private Integer hakAksesId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("nama_hak_akses")
    @Expose
    private String namaHakAkses;

    public Integer getIdPengguna() {
        return idPengguna;
    }

    public void setIdPengguna(Integer idPengguna) {
        this.idPengguna = idPengguna;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getKodeProvinsi() {
        return kodeProvinsi;
    }

    public void setKodeProvinsi(Integer kodeProvinsi) {
        this.kodeProvinsi = kodeProvinsi;
    }

    public Integer getKodeKabupaten() {
        return kodeKabupaten;
    }

    public void setKodeKabupaten(Integer kodeKabupaten) {
        this.kodeKabupaten = kodeKabupaten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public Integer getHakAksesId() {
        return hakAksesId;
    }

    public void setHakAksesId(Integer hakAksesId) {
        this.hakAksesId = hakAksesId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getNamaHakAkses() {
        return namaHakAkses;
    }

    public void setNamaHakAkses(String namaHakAkses) {
        this.namaHakAkses = namaHakAkses;
    }
}
