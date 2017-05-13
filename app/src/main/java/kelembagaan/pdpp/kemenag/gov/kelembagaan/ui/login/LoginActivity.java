package kelembagaan.pdpp.kemenag.gov.kelembagaan.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import kelembagaan.pdpp.kemenag.gov.kelembagaan.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onForgot(View v){
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }

}
