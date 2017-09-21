package com.demo.filesavedemo.SQLSave;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.demo.filesavedemo.R;

import java.io.File;

public class SQLSaveActivity extends AppCompatActivity {
    private static final String TAG = "SQLSaveActivity";
    private MySQliteDataHelper mySQliteDataHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlsave);
        mySQliteDataHelper = new MySQliteDataHelper(this,"BOOKStore.db",null,3);
        Button btn_create_data= (Button) findViewById(R.id.btn_create_data);
        Button btn_delete_data = (Button) findViewById(R.id.btn_delete_data);
        Button btn_insert_data = (Button) findViewById(R.id.btn_insert_data);
        Button btn_update_data = (Button) findViewById(R.id.btn_update_data);
        Button btn_delete_data_1 = (Button) findViewById(R.id.btn_delete_data_1);
        Button btn_query_data = (Button) findViewById(R.id.btn_query_data);

        btn_create_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 mySQliteDataHelper.getWritableDatabase();
            }
        });
        btn_delete_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file1 = new File("/data/data/com.demo.filesavedemo/databases/MYBOOKStore.db");
                deleteFile(file1);
                File file2 = new File("/data/data/com.demo.filesavedemo/databases/MYBOOKStore.db-journal");
                deleteFile(file2);
                File file3 = new File("/data/data/com.demo.filesavedemo/databases/BOOKStore.db");
                deleteFile(file3);
                File file4 = new File("/data/data/com.demo.filesavedemo/databases/BOOKStore.db-journal");
                deleteFile(file4);
            }
        });
        btn_insert_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase sqLiteDatabase=mySQliteDataHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                //开始加入第一条数据
                values.put("author","Tom");
                values.put("price",20);
                values.put("pages",555);
                values.put("name","your name");
                sqLiteDatabase.insert("Book",null,values);
                values.clear();
                //开始加入第二条数据
                values.put("author","Json");
                values.put("price",60);
                values.put("pages",999);
                values.put("name","my name");
                sqLiteDatabase.insert("Book",null,values);
                Toast.makeText(SQLSaveActivity.this, "插入数据成功", Toast.LENGTH_SHORT).show();
            }
        });

        btn_update_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase sb=mySQliteDataHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("pages",1010);
                sb.update("Book",values,"name=?",new String[]{"my name"});
                Toast.makeText(SQLSaveActivity.this, "更新数据成功", Toast.LENGTH_SHORT).show();
            }
        });
        btn_delete_data_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase sb=mySQliteDataHelper.getWritableDatabase();
                sb.delete("Book","price=?",new String[]{"20"});
                Toast.makeText(SQLSaveActivity.this, "删除数据成功", Toast.LENGTH_SHORT).show();
            }
        });
        btn_query_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase sb=mySQliteDataHelper.getWritableDatabase();
                //查询表中所有的数据
                Cursor cursor=sb.query("Book",null,null,null,null,null,null);
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



    public void deleteFile(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                // 设置属性:让文件可执行，可读，可写
                file.setExecutable(true, false);
                file.setReadable(true, false);
                file.setWritable(true, false);
                file.delete(); // delete()方法
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    this.deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.setExecutable(true, false);
            file.setReadable(true, false);
            file.setWritable(true, false);
            file.delete();
            Toast.makeText(this, "成功删除"+file.getName(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, file.getName()+"不存在！！！", Toast.LENGTH_SHORT).show();
        }
    }
}
