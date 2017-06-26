package kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server;

import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponseKabupaten;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponseLembaga;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponsePesantren;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.GetResponseProvinsi;
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
}
