package com.example.empldb;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DBHelper dbHelper;
    Cursor cursor;
    SimpleCursorAdapter adapter;
    ListView listUser;
    SQLiteDatabase db;
    EditText ename, esname, eyear;
    Button btnDel, btnAdd, btnGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getReadableDatabase();
        db.execSQL("DELETE FROM " + DBHelper.MY_TABLE);

        listUser = findViewById(R.id.List);


        btnDel = findViewById(R.id.buttonDel);
        btnAdd = findViewById(R.id.buttonAdd);
        btnGet = findViewById(R.id.buttonGet);

        btnDel.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnGet.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonDel){
            db.execSQL("DELETE FROM " + DBHelper.MY_TABLE);
        }
        if (v.getId() == R.id.buttonAdd){
            ShowWindow();
        }
        if (v.getId() == R.id.buttonGet){
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM " + DBHelper.MY_TABLE, null);
            String[] headers = new String[]{DBHelper.COLUMN_NAME, DBHelper.COLUMN_SURNAME, DBHelper.COLUMN_YEAR};
            adapter = new SimpleCursorAdapter(this, android.R.layout.simple_dropdown_item_1line, cursor, headers, new int[]{android.R.id.text1, android.R.id.text1, android.R.id.text1 }, 0);
            listUser.setAdapter(adapter);
            db.close();
            cursor.close();

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
                cv.put(DBHelper.COLUMN_SURNAME, surname.getText().toString());
                cv.put(DBHelper.COLUMN_NAME, name.getText().toString());
                cv.put(DBHelper.COLUMN_YEAR, Integer.parseInt(age.getText().toString()));

                db = dbHelper.getReadableDatabase();
                db.insert(DBHelper.MY_TABLE, null, cv);
                db.close();
            }
        });
        dialog.show();
    }
}
