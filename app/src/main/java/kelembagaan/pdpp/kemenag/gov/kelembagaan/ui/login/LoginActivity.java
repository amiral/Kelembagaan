package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.login;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.model.Pengguna;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.preference.PreferenceManager;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.ApiClient;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.ApiHelper;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.ApiServerURL;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.data.server.model.PostResponseLogin;
import kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.home.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.edit_username)
    EditText etUsername;

    @BindView(R.id.edit_password)
    EditText etPassword;

    @BindView(R.id.base_layout)
    LinearLayout baseLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_login)
    public void onLogin(){

        String user = etUsername.getText().toString();
        String pass = etPassword.getText().toString();
        if (user.isEmpty()){
            etUsername.setError("Nama Pengguna wajid diisi");
        }else if(pass.isEmpty()){
            etPassword.setError("Password wajib diisi");
        }else{
            loginProcess(user, pass);
        }
    }

    public void loginProcess(String user, String pass){
        ApiHelper apiService = ApiClient.getClient().create(ApiHelper.class);

        final String TAG = "RetrofitLogin";

        Call<PostResponseLogin> call = apiService.postLogin(ApiServerURL.TOKEN_PUBLIC, user, pass);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Proses Login");
        progressDialog.setTitle("Memproses...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();
        call.enqueue(new Callback<PostResponseLogin>() {
            @Override
            public void onResponse(Call<PostResponseLogin>call, Response<PostResponseLogin> response) {
                progressDialog.dismiss();
                if (response != null){
                    if (response.body().getError()){
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                        alertDialogBuilder.setMessage("Username atau Password yang anda masukkan salah!");
                                alertDialogBuilder.setPositiveButton("OKE",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {

                                            }
                                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }else{
                        Pengguna pengguna= response.body().getPengguna();

                        PreferenceManager pref = new PreferenceManager(LoginActivity.this);
                        pref.setPengguna(pengguna);
                        pref.setLogin(true);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
//                        Snackbar.make(baseLayout, "BETUL! "+ pengguna.getNama(), Snackbar.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<PostResponseLogin>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                progressDialog.dismiss();
            }
        });
    }
    public void onForgot(View v){
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }

}
