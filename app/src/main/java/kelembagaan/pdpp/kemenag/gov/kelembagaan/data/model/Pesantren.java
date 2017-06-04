package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model;

import org.parceler.Parcel;

/**
 * Created by Amiral on 5/30/17.
 */

@Parcel(Parcel.Serialization.BEAN)
public class Pesantren {


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
    int idPesantren;
    String namaPesantren;
    String tahunBerdiri;
    String nspp;
    String alamat;
    int kodeKabupaten;
    int kodeProvinsi;
    String kodepos;
    String telp;
    String website;
    String pimpinan;
    String sejarahSingkat;
    long longitude;
    long latitude;
    String masaBerlakuIjinOperasional;
    int potensiEkonomi;
    int idTipologi;
    int idKonsentrasi;
    int statusData;
    String pembaharuanTerakhir;
    int isFavorit;
    String lokasiPesantren;


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

    public int getKodeKabupaten() {
        return kodeKabupaten;
    }

    public void setKodeKabupaten(int kodeKabupaten) {
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

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public String getMasaBerlakuIjinOperasional() {
        return masaBerlakuIjinOperasional;
    }

    public void setMasaBerlakuIjinOperasional(String masaBerlakuIjinOperasional) {
        this.masaBerlakuIjinOperasional = masaBerlakuIjinOperasional;
    }

    public int getPotensiEkonomi() {
        return potensiEkonomi;
    }

    public void setPotensiEkonomi(int potensiEkonomi) {
        this.potensiEkonomi = potensiEkonomi;
    }

    public int getIdTipologi() {
        return idTipologi;
    }

    public void setIdTipologi(int idTipologi) {
        this.idTipologi = idTipologi;
    }

    public int getIdKonsentrasi() {
        return idKonsentrasi;
    }

    public void setIdKonsentrasi(int idKonsentrasi) {
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
