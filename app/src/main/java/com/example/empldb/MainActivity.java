package com.example.empldb;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DataBaseHelper dataBaseHelper;
    SQLiteDatabase db;
    Cursor cursor;
    SimpleCursorAdapter adapter;
    ListView listUser;
    Button btnDel, btnAdd, btnGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBaseHelper = new DataBaseHelper(this);
        db = dataBaseHelper.getReadableDatabase();

        listUser = findViewById(R.id.List);

        btnDel = findViewById(R.id.buttonDel);
        btnDel.setOnClickListener(this);
        btnAdd = findViewById(R.id.buttonAdd);
        btnAdd.setOnClickListener(this);
        btnGet = findViewById(R.id.buttonGet);
        btnGet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonDel){
            db.execSQL("DELETE FROM " + DataBaseHelper.MY_TABLE);
        }
        if (v.getId() == R.id.buttonAdd){
            ShowWindow();
        }
        if (v.getId() == R.id.buttonGet){
            db = dataBaseHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM " + DataBaseHelper.MY_TABLE, null);
            String[] headers = new String[]{DataBaseHelper.COLUMN_NAME, DataBaseHelper.COLUMN_SURNAME, DataBaseHelper.COLUMN_YEAR};
            adapter = new SimpleCursorAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, cursor, headers, new int[]{android.R.id.text1, android.R.id.text1, android.R.id.text1 }, 0);
            listUser.setAdapter(adapter);
        }
    }



    public void ShowWindow (){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Добавить ФИГ");

        LayoutInflater flat = LayoutInflater.from(MainActivity.this);
        View addWindow = flat.inflate(R.layout.add_window, null);

        dialog.setView(addWindow);

        TextInputEditText surname = addWindow.findViewById(R.id.surname);
        TextInputEditText name = addWindow.findViewById(R.id.name);
        TextInputEditText age = addWindow.findViewById(R.id.age);

        dialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ContentValues cv = new ContentValues();
                cv.put(DataBaseHelper.COLUMN_SURNAME, surname.getText().toString());
                cv.put(DataBaseHelper.COLUMN_NAME, name.getText().toString());
                cv.put(DataBaseHelper.COLUMN_YEAR, Integer.parseInt(age.getText().toString()));

                db = dataBaseHelper.getReadableDatabase();
                db.insert(DataBaseHelper.MY_TABLE, null, cv);
                db.close();
            }
        });
        dialog.show();
    }
}
