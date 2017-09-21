package com.demo.filesavedemo.ContentProvider;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.demo.filesavedemo.R;
import com.demo.filesavedemo.SQLSave.SQLSaveActivity;

import java.io.File;

public class ContentProviderActivity extends AppCompatActivity {
    private static final String TAG = "ContentProviderActivity";
    private  String newId ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contentprovider);

        Button btn_insert_data = (Button) findViewById(R.id.btn_insert_data);
        Button btn_update_data = (Button) findViewById(R.id.btn_update_data);
        Button btn_delete_data_1 = (Button) findViewById(R.id.btn_delete_data_1);
        Button btn_query_data = (Button) findViewById(R.id.btn_query_data);


        btn_insert_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.demo.filesavedemo.provider/book");
                ContentValues values=new ContentValues();
                //开始加入第一条数据
                values.put("author","lalal");
                values.put("price",20);
                values.put("pages",666);
                values.put("name","your name");
                Uri newUri=getContentResolver().insert(uri,values);
                newId = newUri != null ? newUri.getPathSegments().get(1) : null;

            }
        });

        btn_update_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.demo.filesavedemo.provider/book/"+newId);
                ContentValues values=new ContentValues();
                //开始加入第一条数据
                values.put("author","hahahhahaha");
                values.put("price",20);
                values.put("pages",666);
                values.put("name","your name");
              getContentResolver().update(uri,values,null,null);


            }
        });
        btn_delete_data_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //删除数据
                Uri uri = Uri.parse("content://com.demo.filesavedemo.provider/book/"+newId);
                getContentResolver().delete(uri,null,null);
            }
        });
        btn_query_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.demo.filesavedemo.provider/book/"+newId);

                //查询表中所有的数据
                Cursor cursor=getContentResolver().query(uri,null,null,null,null);
                if(cursor.moveToFirst()){
                    do {
                        //遍历Cursor对象，取出数据打印
                        String name=cursor.getString(cursor.getColumnIndex("name"));
                        String author=cursor.getString(cursor.getColumnIndex("author"));
                        int pages=cursor.getInt(cursor.getColumnIndex("pages"));
                        double price=cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.e(TAG, "book name is"+name );
                        Log.e(TAG, "book author is"+author );
                        Log.e(TAG, "book pages is"+pages );
                        Log.e(TAG, "book price is"+price );
                    }while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }
}
