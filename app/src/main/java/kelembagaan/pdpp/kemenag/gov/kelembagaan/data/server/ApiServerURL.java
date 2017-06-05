package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server;

/**
 * Created by Amiral on 6/5/17.
 */

public final class ApiServerURL {

    public static final String BASE_URL = "http://93.188.161.74/pdpp/api/lembaga/";
    public static final String TOKEN_PUBLIC = "10adcba89b4a410c889b66fa3a87b6a0";

    public static final String URL_GET_PESANTREN = BASE_URL +TOKEN_PUBLIC + "/ambil_pesantren/";
    public static final String URL_GET_LEMBAGA = BASE_URL + TOKEN_PUBLIC + "/ambil_lembaga/";
    public static final String URL_LOGIN = BASE_URL + TOKEN_PUBLIC + "/login/";
    public static final String URL_LUPA_PASSWORD = BASE_URL + TOKEN_PUBLIC + "/lupa_password";
    public static final String URL_GET_KABUPATEN = BASE_URL + TOKEN_PUBLIC + "/ambil_kabupaten";
    public static final String URL_GET_PROVINSI = BASE_URL + TOKEN_PUBLIC + "/ambil_provinsi";



}
