package id.gani.latihansqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private DatabaseSQLiteHelper mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDB = new DatabaseSQLiteHelper(this);

        final EditText txtUsername = (EditText) findViewById(R.id.txtUsername);
        final EditText txtPassword = (EditText) findViewById(R.id.txtPassword);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean cek = mDB.cekLogin(txtUsername.getText().toString(),txtPassword.getText().toString());

                if(cek == true){
                    Intent i = new Intent(LoginActivity.this, ListActivity.class);
                    Toast.makeText(getApplicationContext(),"Login Berhasil",Toast.LENGTH_LONG).show();
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(),"Login Gagal!",Toast.LENGTH_LONG).show();
                }
            }
        });

        Button btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
