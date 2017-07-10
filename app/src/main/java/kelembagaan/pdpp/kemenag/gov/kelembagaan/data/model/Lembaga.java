package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import io.realm.LembagaRealmProxy;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Amiral on 5/30/17.
 */
@Parcel(implementations = { LembagaRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { Lembaga.class })
public class Lembaga extends RealmObject {

    /*"id_lembaga": 1,
            "nspp": "510011020055",
            "npsn": "593559",
            "nsm": "581213",
            "nama_lembaga": "Will Ltd",
            "alamat": "Suite 413",
            "kode_pos": "32046",
            "telepon": "+1 (891) 770-6424",
            "pimpinan": "Juliet Wolff",
            "website": "koss.com",
            "status_lembaga": "swasta",
            "masa_berlaku_ijin_operasional": "2020-06-13",
            "longitude": "-117.8137340",
            "latitude": "25.8252800",
            "kabupaten_id": 0,
            "id_tipe_lembaga": null,
            "nama_tipe_lembaga": null,
            "id_jenjang_lembaga": null,
            "nama_jenjang_lembaga": null,
            "status_data": "tidak aktif",
            "pembaruan_terakhir": "2016-12-31 18:45:12"
    */

    @PrimaryKey
    @SerializedName("id_lembaga")
    @Expose
    int idLembaga;

    @SerializedName("nspp")
    @Expose
    String nspp;

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


    @SerializedName("id_tipe_lembaga")
    @Expose
    int idTipeLembaga;

    @SerializedName("nama_tipe_lembaga")
    @Expose
    String namaTipeLembaga;

    @SerializedName("id_jenjang_lembaga")
    @Expose
    int idJenjangLembaga;

    @SerializedName("nama_jenjang_lembaga")
    @Expose
    String namaJenjangLembaga;

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

    public String getNamaTipeLembaga() {
        return namaTipeLembaga;
    }

    public void setNamaTipeLembaga(String namaTipeLembaga) {
        this.namaTipeLembaga = namaTipeLembaga;
    }

    public String getNamaJenjangLembaga() {
        return namaJenjangLembaga;
    }

    public void setNamaJenjangLembaga(String namaJenjangLembaga) {
        this.namaJenjangLembaga = namaJenjangLembaga;
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

    public String getNspp() {
        return nspp;
    }

    public void setNspp(String nspp) {
        this.nspp = nspp;
    }

    public int getIdTipeLembaga() {
        return idTipeLembaga;
    }

    public void setIdTipeLembaga(int idTipeLembaga) {
        this.idTipeLembaga = idTipeLembaga;
    }

    public int getIdJenjangLembaga() {
        return idJenjangLembaga;
    }

    public void setIdJenjangLembaga(int idJenjangLembaga) {
        this.idJenjangLembaga = idJenjangLembaga;
    }
}