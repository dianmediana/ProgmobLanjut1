package com.dianmediana.progmoblanjut1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnNew;
    Button btnOpen;
    Button btnSave, btnHitung;
    EditText editTitle, editTugas, editHadir, editUTS, editUAS, editAkhir;
    Double vtugas, vhadir, vuts, vuas, vakhir ;
//    String vhuruf ;

    File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        btnNew = (Button) findViewById(R.id.button_new);
        btnOpen = (Button) findViewById(R.id.button_open);
        btnSave = (Button) findViewById(R.id.button_save);
        editTitle = (EditText) findViewById(R.id.edit_title);
        editTugas = (EditText) findViewById(R.id.tugas);
        editHadir = (EditText) findViewById(R.id.hadir);
        editUTS = (EditText) findViewById(R.id.uts);
        editUAS = (EditText) findViewById(R.id.uas);
        editAkhir = (EditText) findViewById(R.id.akhir);
//        editHuruf = (EditText) findViewById(R.id.huruf);
        btnHitung = (Button) findViewById(R.id.bthitung);

        btnNew.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        path = getFilesDir();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.button_new:
                newFile();
                break;
            case R.id.button_open:
                openFile();
                break;
            case R.id.button_save:
                saveFile();
                break;
        }
    }

    public void hitung(View view){
        vtugas = Double.parseDouble(editTugas.getText().toString());
        vhadir = Double.parseDouble(editHadir.getText().toString());
        vuts = Double.parseDouble(editUTS.getText().toString());
        vuas = Double.parseDouble(editUAS.getText().toString());


        vakhir = (0.15 * vhadir) + (0.25 * vtugas) + (0.25 * vuts) + (0.35 * vuas);
        editAkhir.setText(""+vakhir);
    }

    public void newFile() {

        editTitle.setText("");
        editTugas.setText("");
        editHadir.setText("");
        editUTS.setText("");
        editUAS.setText("");
        editAkhir.setText("");

        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show();
    }

    private void loadData(String title) {

        editHadir.setText("");
        editTugas.setText("");
        editUTS.setText("");
        editUAS.setText("");

        String text = FileHelper.readFromFile(this, title);
        editTitle.setText(title);
        editAkhir.setText(text);
        Toast.makeText(this, "Loading " + title + " data", Toast.LENGTH_SHORT).show();
    }

    public void openFile() {
        showList();
    }

    private void showList() {
        final ArrayList<String> arrayList = new ArrayList<String>();
        for (String file : path.list()) {
            arrayList.add(file);
        }
        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file yang diinginkan");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                loadData(items[item].toString());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void saveFile() {
        if (editTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else {
            String title = editTitle.getText().toString();
            String text = editAkhir.getText().toString();
            FileHelper.writeToFile(title, text, this);
            Toast.makeText(this, "Saving " + editTitle.getText().toString() + " file", Toast.LENGTH_SHORT).show();
        }
    }

}
