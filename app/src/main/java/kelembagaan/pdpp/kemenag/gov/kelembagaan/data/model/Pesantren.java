package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import io.realm.PesantrenRealmProxy;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Amiral on 5/30/17.
 */
@Parcel(implementations = { PesantrenRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { Pesantren.class })
public class Pesantren extends RealmObject{


   /* "id_pesantren": 2,
            "nama_pesantren": "Vena Bergstrom",
            "nspp": "510011010001",
            "alamat": "9440 Terrance Fort Apt. 255\nNorth Tianna, MD 65502",
            "kode_pos": "22620",
            "telepon": "+1-305-225-6132",
            "pimpinan": "Mrs. Opal Yost",
            "longitude": "1.2000000",
            "latitude": "8.4000000",
            "masa_berlaku_ijin_operasional": "2016-11-08",
            "nama_potensi_ekonomi": "Wartel/Warnet/Rental Komputer",
            "nama_tipologi": "Salafiyah",
            "nama_konsentrasi": "Hadits"

    tahun_berdiri,nspp,idkabupaten,idprovinsi,website,sejarah_singkat,pembaruan_terakhir
    */

    @PrimaryKey
    @SerializedName("id_pesantren")
    @Expose
    int idPesantren;

    @SerializedName("nama_pesantren")
    @Expose
    String namaPesantren;

    String tahunBerdiri;

    @SerializedName("nspp")
    @Expose
    String nspp;

    @SerializedName("alamat")
    @Expose
    String alamat;

    @SerializedName("kabupaten_id")
    @Expose
    String kodeKabupaten;

    int kodeProvinsi;

    @SerializedName("kode_pos")
    @Expose
    String kodepos;

    @SerializedName("telepon")
    @Expose
    String telp;

    @SerializedName("website")
    @Expose
    String website;

    @SerializedName("pimpinan")
    @Expose
    String pimpinan;

    @SerializedName("sejarah_singkat")
    @Expose
    String sejarahSingkat;

    @SerializedName("longitude")
    @Expose
    String longitude;

    @SerializedName("latitude")
    @Expose
    String latitude;

    @SerializedName("masa_berlaku_ijin_operasional")
    @Expose
    String masaBerlakuIjinOperasional;

    @SerializedName("nama_potensi_ekonomi")
    @Expose
    String potensiEkonomi;

    @SerializedName("nama_tipologi")
    @Expose
    String idTipologi;

    @SerializedName("nama_konsentrasi")
    @Expose
    String idKonsentrasi;

    int statusData;

    @SerializedName("pembaruan_terakhir")
    @Expose
    String pembaharuanTerakhir;
    int isFavorit;
    String lokasiPesantren;
    String luasPesantren;

    public String getLuasPesantren() {
        return luasPesantren;
    }

    public void setLuasPesantren(String luasPesantren) {
        this.luasPesantren = luasPesantren;
    }

    public int getIdPesantren() {
        return idPesantren;
    }

    public void setIdPesantren(int idPesantren) {
        this.idPesantren = idPesantren;
    }

    public String getNamaPesantren() {
        return namaPesantren;
    }

    public void setNamaPesantren(String namaPesantren) {
        this.namaPesantren = namaPesantren;
    }

    public String getTahunBerdiri() {
        return tahunBerdiri;
    }

    public void setTahunBerdiri(String tahunBerdiri) {
        this.tahunBerdiri = tahunBerdiri;
    }

    public String getNspp() {
        return nspp;
    }

    public void setNspp(String nspp) {
        this.nspp = nspp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKodeKabupaten() {
        return kodeKabupaten;
    }

    public void setKodeKabupaten(String kodeKabupaten) {
        this.kodeKabupaten = kodeKabupaten;
    }

    public int getKodeProvinsi() {
        return kodeProvinsi;
    }

    public void setKodeProvinsi(int kodeProvinsi) {
        this.kodeProvinsi = kodeProvinsi;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPimpinan() {
        return pimpinan;
    }

    public void setPimpinan(String pimpinan) {
        this.pimpinan = pimpinan;
    }

    public String getSejarahSingkat() {
        return sejarahSingkat;
    }

    public void setSejarahSingkat(String sejarahSingkat) {
        this.sejarahSingkat = sejarahSingkat;
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

    public String getMasaBerlakuIjinOperasional() {
        return masaBerlakuIjinOperasional;
    }

    public void setMasaBerlakuIjinOperasional(String masaBerlakuIjinOperasional) {
        this.masaBerlakuIjinOperasional = masaBerlakuIjinOperasional;
    }

    public String getPotensiEkonomi() {
        return potensiEkonomi;
    }

    public void setPotensiEkonomi(String potensiEkonomi) {
        this.potensiEkonomi = potensiEkonomi;
    }

    public String getIdTipologi() {
        return idTipologi;
    }

    public void setIdTipologi(String idTipologi) {
        this.idTipologi = idTipologi;
    }

    public String getIdKonsentrasi() {
        return idKonsentrasi;
    }

    public void setIdKonsentrasi(String idKonsentrasi) {
        this.idKonsentrasi = idKonsentrasi;
    }

    public int getStatusData() {
        return statusData;
    }

    public void setStatusData(int statusData) {
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

    public String getLokasiPesantren() {
        return lokasiPesantren;
    }

    public void setLokasiPesantren(String lokasiPesantren) {
        this.lokasiPesantren = lokasiPesantren;
    }
}
