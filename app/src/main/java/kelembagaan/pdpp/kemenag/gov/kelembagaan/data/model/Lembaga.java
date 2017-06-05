package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amiral on 5/30/17.
 */

public class Lembaga {

//      "id_lembaga": 27,
//              "npsn": "929087",
//              "nsm": "819492",
//              "nama_lembaga": "Ferry, Borer and Walter",
//              "alamat": "Apt. 166",
//              "kode_pos": "68851",
//              "telepon": "862-458-9175 x69026",
//              "pimpinan": "Leland Mraz",
//              "website": "langosh.com",
//              "status_lembaga": "negeri",
//              "masa_berlaku_ijin_operasional": "2018-02-20",
//              "longitude": "-40.1148880",
//              "latitude": "29.1094650",
//              "nama_tipe_lembaga": null,
//              "nama_jenjang_lembaga": null,
//              "status_data": "tidak aktif"

    @SerializedName("id_lembaga")
    @Expose
    int idLembaga;

    @SerializedName("npsn")
    @Expose
    String npsn;

    @SerializedName("nsm")
    @Expose
    String nsm;

    @SerializedName("nama_lembaga")
    @Expose
    String namaLembaga;

    @SerializedName("alamat")
    @Expose
    String alamat;

    @SerializedName("kode_pos")
    @Expose
    String kodepos;

    @SerializedName("telepon")
    @Expose
    String telp;

    @SerializedName("pimpinan")
    @Expose
    String pimpinan;

    @SerializedName("website")
    @Expose
    String website;

    @SerializedName("status_lembaga")
    @Expose
    String statusLembaga;

    @SerializedName("masa_berlaku_ijin_operasional")
    @Expose
    String masaBerlakuIjinOperasional;

    @SerializedName("longitude")
    @Expose
    String longitude;

    @SerializedName("latitude")
    @Expose
    String latitude;

    @SerializedName("kabupaten_id")
    @Expose
    private int kabupatenId;

    @SerializedName("nama_tipe_lembaga")
    @Expose
    String idTipeLembaga;

    @SerializedName("nama_jenjang_lembaga")
    @Expose
    String idJenjangLembaga;

    @SerializedName("status_data")
    @Expose
    String statusData;

    @SerializedName("pembaruan_terakhir")
    @Expose
    String pembaharuanTerakhir;

    int isFavorit;
    String lokasiLembaga;

    public int getKabupatenId() {
        return kabupatenId;
    }

    public void setKabupatenId(int kabupatenId) {
        this.kabupatenId = kabupatenId;
    }

    public String getLokasiLembaga() {
        return lokasiLembaga;
    }

    public void setLokasiLembaga(String lokasiLembaga) {
        this.lokasiLembaga = lokasiLembaga;
    }

    public int getIdLembaga() {
        return idLembaga;
    }

    public void setIdLembaga(int idLembaga) {
        this.idLembaga = idLembaga;
    }

    public String getNpsn() {
        return npsn;
    }

    public void setNpsn(String npsn) {
        this.npsn = npsn;
    }

    public String getNsm() {
        return nsm;
    }

    public void setNsm(String nsm) {
        this.nsm = nsm;
    }

    public String getNamaLembaga() {
        return namaLembaga;
    }

    public void setNamaLembaga(String namaLembaga) {
        this.namaLembaga = namaLembaga;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKodepos() {
        return kodepos;
    }

    public void setKodepos(String kodepos) {
        this.kodepos = kodepos;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getPimpinan() {
        return pimpinan;
    }

    public void setPimpinan(String pimpinan) {
        this.pimpinan = pimpinan;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getStatusLembaga() {
        return statusLembaga;
    }

    public void setStatusLembaga(String statusLembaga) {
        this.statusLembaga = statusLembaga;
    }

    public String getMasaBerlakuIjinOperasional() {
        return masaBerlakuIjinOperasional;
    }

    public void setMasaBerlakuIjinOperasional(String masaBerlakuIjinOperasional) {
        this.masaBerlakuIjinOperasional = masaBerlakuIjinOperasional;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getIdTipeLembaga() {
        return idTipeLembaga;
    }

    public void setIdTipeLembaga(String idTipeLembaga) {
        this.idTipeLembaga = idTipeLembaga;
    }

    public String getIdJenjangLembaga() {
        return idJenjangLembaga;
    }

    public void setIdJenjangLembaga(String idJenjangLembaga) {
        this.idJenjangLembaga = idJenjangLembaga;
    }

    public String getStatusData() {
        return statusData;
    }

    public void setStatusData(String statusData) {
        this.statusData = statusData;
    }

    public String getPembaharuanTerakhir() {
        return pembaharuanTerakhir;
    }

    public void setPembaharuanTerakhir(String pembaharuanTerakhir) {
        this.pembaharuanTerakhir = pembaharuanTerakhir;
    }

    public int getIsFavorit() {
        return isFavorit;
    }

    public void setIsFavorit(int isFavorit) {
        this.isFavorit = isFavorit;
    }
}