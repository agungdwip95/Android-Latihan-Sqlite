package id.gani.latihansqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    private DatabaseSQLiteHelper mDB;
    int id;
    EditText txtWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mDB = new DatabaseSQLiteHelper(this);

        txtWord = (EditText) findViewById(R.id.txtWord);

        Intent i = getIntent();
        Bundle b = i.getExtras();

        if(b!=null){
            id = (Integer) b.get("idWord");
            txtWord.setText(mDB.getDataWhere(id));
        } else {
            id = 0;
        }

        Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDB.update(id, txtWord.getText().toString());

                Toast.makeText(getApplicationContext(),"Data Berhasil di Update!", Toast.LENGTH_LONG).show();
            }
        });

        Button btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
