package com.demo.filesavedemo.SharePreferences;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.demo.filesavedemo.R;

public class FileSharePreferenceActivity extends AppCompatActivity {
    private static final String TAG = "FileSharePreferenceActi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_share_preference);
        Button button= (Button) findViewById(R.id.btn_share_save);
        Button button2= (Button) findViewById(R.id.btn_share_load);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDate();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDate();
            }
        });

    }

    public void saveDate(){
        SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
        editor.putString("name","小强");
        editor.putInt("age",18);
        editor.putBoolean("married",false);
        editor.apply();
        Toast.makeText(FileSharePreferenceActivity.this, "保存数据成功", Toast.LENGTH_SHORT).show();
    }

    public  void loadDate(){
         SharedPreferences spf=getSharedPreferences("data",MODE_PRIVATE);
        String name= spf.getString("name","");//字符串默认为空
        boolean  married= spf.getBoolean("married",false);
       int age=  spf.getInt("age",0);
        Log.i(TAG, "loadDate: name="+name);
        Log.i(TAG, "loadDate: married="+married);
        Log.i(TAG, "loadDate: age="+age);
    }

}
