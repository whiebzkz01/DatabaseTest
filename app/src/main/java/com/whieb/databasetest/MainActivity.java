package com.whieb.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDatabaseHelper(this,"BookStore.db",null,5);
        Button createDatabase = (Button) findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });

        Button addData = (Button) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("name","The Da Vinci Code");
                values.put("author","Dan Brown");
                values.put("pages",454);
                values.put("price",16.96);
                db.insert("Book",null,values);
                values.clear();

                /*
                values.put("name","The Lost Symbol");
                values.put("author", "Dan Brown");
                values.put("pages",510);
                values.put("price",19.95);
                db.insert("Book",null,values);*/

                db.execSQL("insert into Book (name, author, pages, price) values (?,?,?,?)", new String[]{
                        "The Lost Symbol","Dan Brown","510","19.95"});

            }
        });

        Button updateData  = (Button) findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
              /*  ContentValues values = new ContentValues();
                values.put("price", 20.99);
                db.update("Book", values,"name = ?", new String[]{"The Da Vinci Code"});*/
              db.execSQL("update Book set price = ? where name = ? ", new String[]{"30.99", "The Da Vinci Code"});
            }
        });

//        detele Test
        Button deteleData = (Button) findViewById(R.id.delete_data);
        deteleData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
            /*    db.delete("Book","pages > ? ",new String[]{"500"});*/

                db.execSQL("delete from Book where pages > ?", new String[]{"500"});
            }
        });

        Button queryData  = (Button) findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
//                Cursor cursor = db.query("Book",null,null,null,null,null,null);

                Cursor cursor = db.rawQuery("select * from Book", null);
                if(cursor.moveToFirst()){
                    do{
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        /*Log.d("MainActivity","book name is " + name);
                        Log.d("MainActivity","book author is " + author);
                        Log.d("MainActivity","book pages is " + pages);
                        Log.d("MainActivity","book price is " + price);
                        */

                        Log.d("MainActivity","book is " +"{" +name+","+author+","+pages+","+price);

                    }while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }
}
