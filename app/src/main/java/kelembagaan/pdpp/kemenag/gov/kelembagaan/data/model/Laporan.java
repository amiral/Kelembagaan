package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Amiral on 7/17/17.
 */

public class Laporan extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_flag_lembaga")
    @Expose
    private Integer idFlagLembaga;
    @SerializedName("id_lembaga")
    @Expose
    private Integer idLembaga;
    @SerializedName("duplicate")
    @Expose
    private Integer duplicate;
    @SerializedName("invalid")
    @Expose
    private Integer invalid;
    @SerializedName("missing")
    @Expose
    private Integer missing;
    @SerializedName("komentar")
    @Expose
    private String komentar;
    @SerializedName("kode_kabupaten")
    @Expose
    private Integer kodeKabupaten;
    @SerializedName("kode_provinsi")
    @Expose
    private Integer kodeProvinsi;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("id_pengguna")
    @Expose
    private Integer idPengguna;
    @SerializedName("hak_akses_id")
    @Expose
    private Integer hakAksesId;
    @SerializedName("flag_type")
    @Expose
    private String flagType;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("nama_pengguna")
    @Expose
    private String namaPengguna;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdFlagLembaga() {
        return idFlagLembaga;
    }

    public void setIdFlagLembaga(Integer idFlagLembaga) {
        this.idFlagLembaga = idFlagLembaga;
    }

    public Integer getIdLembaga() {
        return idLembaga;
    }

    public void setIdLembaga(Integer idLembaga) {
        this.idLembaga = idLembaga;
    }

    public Integer getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(Integer duplicate) {
        this.duplicate = duplicate;
    }

    public Integer getInvalid() {
        return invalid;
    }

    public void setInvalid(Integer invalid) {
        this.invalid = invalid;
    }

    public Integer getMissing() {
        return missing;
    }

    public void setMissing(Integer missing) {
        this.missing = missing;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public Integer getKodeKabupaten() {
        return kodeKabupaten;
    }

    public void setKodeKabupaten(Integer kodeKabupaten) {
        this.kodeKabupaten = kodeKabupaten;
    }

    public Integer getKodeProvinsi() {
        return kodeProvinsi;
    }

    public void setKodeProvinsi(Integer kodeProvinsi) {
        this.kodeProvinsi = kodeProvinsi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public Integer getIdPengguna() {
        return idPengguna;
    }

    public void setIdPengguna(Integer idPengguna) {
        this.idPengguna = idPengguna;
    }

    public Integer getHakAksesId() {
        return hakAksesId;
    }

    public void setHakAksesId(Integer hakAksesId) {
        this.hakAksesId = hakAksesId;
    }

    public String getFlagType() {
        return flagType;
    }

    public void setFlagType(String flagType) {
        this.flagType = flagType;
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

    public String getNamaPengguna() {
        return namaPengguna;
    }

    public void setNamaPengguna(String namaPengguna) {
        this.namaPengguna = namaPengguna;
    }

}
