package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server;

import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponseKabupaten;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponseLaporanLembaga;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponseLembaga;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponsePesantren;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponseProvinsi;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponseStatistikLembaga;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponseStatistikPesantren;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.PostResponseForgotPassword;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.PostResponseKoreksi;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.PostResponseLogin;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Amiral on 6/5/17.
 */

public interface ApiHelper {


    @GET("/pdpp/api/lembaga/{token}/ambil_pesantren/{last_update}")
    Call<GetResponsePesantren> getPesantren(@Path("token") String token, @Path("last_update") String lastUpdate);

    @GET("/pdpp/api/lembaga/{token}/ambil_lembaga/{last_update}")
    Call<GetResponseLembaga> getLembaga(@Path("token") String token, @Path("last_update") String lastUpdate);

    @GET("/pdpp/api/lembaga/{token}/ambil_provinsi")
    Call<GetResponseProvinsi> getProvinsi(@Path("token") String token);

    @GET("/pdpp/api/lembaga/{token}/ambil_kabupaten")
    Call<GetResponseKabupaten> getKabupaten(@Path("token") String token);

    @FormUrlEncoded
    @POST("/pdpp/api/lembaga/{token}/login")
    Call<PostResponseLogin> postLogin(@Path("token") String token, @Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/pdpp/api/lembaga/{token}/lupa_password")
    Call<PostResponseForgotPassword> postForgotPassword(@Path("token") String token, @Field("email") String email);

    @FormUrlEncoded
    @POST("/pdpp/api/lembaga/{token}/flag_lembaga")
    Call<PostResponseKoreksi> postKoreksiLembaga(@Path("token") String token, @Field("id_pengguna") int idPengguna, @Field("id_lembaga") int idLembaga,
                                                 @Field("id_kabupaten") int idKabupaten,
                                                 @Field("id_flag") int idTipe, @Field("text") String pesan);

    @FormUrlEncoded
    @POST("/pdpp/api/lembaga/{token}/flag_pesantren")
    Call<PostResponseKoreksi> postKoreksiPesantren(@Path("token") String token, @Field("id_pengguna") int idPengguna, @Field("id_pesantren") int idPesantren,
                                                 @Field("id_kabupaten") int idKabupaten,
                                                 @Field("id_flag") int idTipe, @Field("text") String pesan);
    @FormUrlEncoded
    @POST("/pdpp/api/lembaga/{token}/lapor_pesantren")
    Call<PostResponseKoreksi> postLaporPesantren(@Path("token") String token, @Field("id_pengguna") int idPengguna, @Field("id_hak_akses") int idHakAkses,
                                                   @Field("id_kabupaten") int idKabupaten, @Field("text") String pesan);

    @FormUrlEncoded
    @POST("/pdpp/api/lembaga/{token}/lapor_lembaga")
    Call<PostResponseKoreksi> postLaporLembaga(@Path("token") String token, @Field("id_pengguna") int idPengguna, @Field("id_hak_akses") int idHakAkses,
                                                 @Field("id_kabupaten") int idKabupaten, @Field("text") String pesan);


    @GET("/pdpp/api/lembaga/{token}/t_m_flag_lembaga/{last_update}")
    Call<GetResponseLaporanLembaga> getLaporanLembaga(@Path("token") String token, @Path("last_update") String lastUpdate);

    @GET("/pdpp/api/lembaga/{token}/statistik_santri_lembaga/{last_update}")
    Call<GetResponseStatistikLembaga> getStatistikLembaga(@Path("token") String token, @Path("last_update") String lastUpdate);

    @GET("/pdpp/api/lembaga/{token}/statistik_santri_pesantren/{last_update}")
    Call<GetResponseStatistikPesantren> getStatistikPesantren(@Path("token") String token, @Path("last_update") String lastUpdate);
}
