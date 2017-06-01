package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model;

/**
 * Created by Amiral on 5/30/17.
 */

public class Lembaga {
    int idLembaga;
    String npsn;
    String nspp;
    String nsm;
    String namaLembaga;
    String alamat;
    String kodepos;
    String telp;
    String pimpinan;
    String website;
    String statusLembaga;
    String masaBerlakuIjinOperasional;
    long longitude;
    long latitude;
    int idTipeLembaga;
    int idJenjangLembaga;
    int statusData;
    String pembaharuanTerakhir;
    int isFavorit;

    public int getIsFavorit() {
        return isFavorit;
    }

    public void setIsFavorit(int isFavorit) {
        this.isFavorit = isFavorit;
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

    public String getNspp() {
        return nspp;
    }

    public void setNspp(String nspp) {
        this.nspp = nspp;
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
}