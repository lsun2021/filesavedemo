package com.demo.filesavedemo.FileSave;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.filesavedemo.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileSaveActivity extends AppCompatActivity {

    private static final String TAG = "FileSaveActivity";
    private EditText editText;

    private Button  btn_load_data,btn_save_data;
    private TextView  tv_txt_load_data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_save);
        editText = (EditText) findViewById(R.id.et_txt_content);
        tv_txt_load_data= (TextView) findViewById(R.id.tv_txt_load_data);

         btn_save_data= (Button) findViewById(R.id.btn_save_data);
         btn_load_data= (Button) findViewById(R.id.btn_load_data);
        btn_save_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String  inputtext=editText.getText().toString();
                save(inputtext);

            }
        });
        btn_load_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String  inputText=load();
                Log.e(TAG, "onClick: "+inputText );
                if(!TextUtils.isEmpty(inputText)){
                    tv_txt_load_data.setText(inputText);
                }
                Toast.makeText(FileSaveActivity.this, "读取数据成功="+inputText, Toast.LENGTH_SHORT).show();
            }

        });

    }

    //保存数据到文件中
    private void save(String inputtext) {
         FileOutputStream outputStream = null;
         BufferedWriter bufferedWriter = null;
        try {
            outputStream = openFileOutput("data", Context.MODE_PRIVATE);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(inputtext);
            Toast.makeText(FileSaveActivity.this, "保存数据成功"+inputtext, Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bufferedWriter!=null){
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //读取文件中的数据
    private  String load(){
         FileInputStream inputStream=null;
         BufferedReader bufferedReader=null;
        StringBuilder content=new StringBuilder();

        try {
            inputStream = openFileInput("data");
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line=" ";
            try {
                while ((line=bufferedReader.readLine())!=null){
                     content.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(bufferedReader!=null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  content.toString();
    }
}
