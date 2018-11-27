package id.gani.latihansqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputActivity extends AppCompatActivity {

    private DatabaseSQLiteHelper mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        mDB = new DatabaseSQLiteHelper(this);

        final EditText txtWord = (EditText) findViewById(R.id.txtWord);
        final EditText txtKeterangan = (EditText) findViewById(R.id.txtKeterangan);

        Button btnInsert = (Button) findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDB.insert(txtWord.getText().toString(),txtKeterangan.getText().toString());

                Toast.makeText(getApplicationContext(),"Data Berhasil di Insert!",Toast.LENGTH_LONG).show();
            }
        });

        Button btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InputActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
}
