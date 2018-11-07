package id.gani.latihansqlite;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ListActivity extends AppCompatActivity {

    private DatabaseSQLiteHelper mDB;

    private String[] pilihan_menu = { "Edit Data", "Hapus Data" };

    private String[] word,idWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mDB = new DatabaseSQLiteHelper(this);
        mDB.getWritableDatabase();

        idWord = mDB.getDataAll(0);
        word = mDB.getDataAll(1);

        Cursor cursor=mDB.getDataAllCursor();

        String[] columns = new String[] { mDB.KEY_WORD, mDB.KEY_ID };
        int[] to = new int[] { R.id.txtWord, R.id.txtId };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.list_view, cursor, columns, to, 0);
        ListView listview =(ListView) findViewById(R.id.listWord);
        listview.setAdapter(adapter);
        registerForContextMenu(listview);

        Button btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    public void onCreateContextMenu(ContextMenu menu, View tampil, ContextMenu.ContextMenuInfo menuInfo) {
        if (tampil.getId() == R.id.listWord) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle(word[info.position]);
            for (int i = 0; i < pilihan_menu.length; i++) {
                menu.add(Menu.NONE, i, i, pilihan_menu[i]);
            }
        }
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String aksi = pilihan_menu[item.getItemId()];
        int id = Integer.parseInt(idWord[info.position]);

        if(aksi.equals("Edit Data")){
            Intent i = new Intent(ListActivity.this, EditActivity.class);

            i.putExtra("idWord", id);
            startActivity(i);
        } else {
            mDB.delete(id);

            Toast.makeText(getApplicationContext(),
                            "Data Berhasil di Hapus!",
                                Toast.LENGTH_LONG).show();

            Intent i = new Intent(ListActivity.this, MainActivity.class);
            startActivity(i);
        }

        return true;
    }


}
