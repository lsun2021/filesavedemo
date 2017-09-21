package com.demo.filesavedemo.SQLSave;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.IccOpenLogicalChannelResponse;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ${momoThree} on 2017/9/20.
 * Title:
 */

public class MySQliteDataHelper extends SQLiteOpenHelper {

    public  static final String CREATE_BOOK = "create table Book("
            +"id integer primary key autoincrement,"
            +"author text,"
            +"price real,"
            +"pages integer,"
            +"name text)";
    public static  final String  CREATE_CATEGORY = "create table Category("
            +"id integer primary key autoincrement, "
            +"category_name text,"
            +"category_code integer)";

    private Context  mContext;

    public MySQliteDataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_BOOK);
        sqLiteDatabase.execSQL(CREATE_CATEGORY);
        Toast.makeText(mContext, "创建数据库成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int olderVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop table if exists BOOK");
        sqLiteDatabase.execSQL("drop table if exists Category");
        onCreate(sqLiteDatabase);
    }
}
