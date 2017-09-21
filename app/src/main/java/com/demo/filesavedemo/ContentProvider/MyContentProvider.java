package com.demo.filesavedemo.ContentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.Switch;

import com.demo.filesavedemo.SQLSave.MySQliteDataHelper;

public class MyContentProvider extends ContentProvider {

    private MySQliteDataHelper mySQliteDataHelper;


    private static final int BOOK_DIR = 0;
    private static final int BOOK_ITEM = 1;
    private static final int BOOK2_DIR = 2;
    private static final int BOOK2_ITEM = 3;
    
    public  static  final String  AUTHORITY="com.demo.filesavedemo.provider";
    
    private static  UriMatcher sURIMatcher;
    static {
        sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sURIMatcher.addURI("AUTHORITY","book",BOOK_DIR);
        sURIMatcher.addURI("AUTHORITY","book/#",BOOK_ITEM);
        sURIMatcher.addURI("AUTHORITY","category",BOOK2_DIR);
        sURIMatcher.addURI("AUTHORITY","category/#",BOOK2_ITEM);
    }
    
    @Override
    public boolean onCreate() {
        mySQliteDataHelper = new MySQliteDataHelper(getContext(),"BOOKStore.db",null,2);
        return true;
    }


    @Override
    public String getType( Uri uri) {
        switch (sURIMatcher.match(uri)){
            case BOOK_DIR:
                return "vnd.android.cursor.dir/vnd.com.demo.filesavedemo.provider.book";
            case BOOK_ITEM:
                return "vnd.android.cursor.item/vnd.com.demo.filesavedemo.provider.book";
            case BOOK2_DIR:
                return "vnd.android.cursor.dir/vnd.com.demo.filesavedemo.provider.category";
            case BOOK2_ITEM:
                return "vnd.android.cursor.item/vnd.com.demo.filesavedemo.provider.category";
            default:
                break;
        }
        return null;
    }


    @Override
    public Cursor query( Uri uri,  String[] strings,  String s,  String[] strings1,  String s1) {
        //查詢數據
        SQLiteDatabase db=mySQliteDataHelper.getReadableDatabase();
        Cursor cursor=null;
        switch (sURIMatcher.match(uri)){
            case BOOK_DIR:
                cursor=db.query("Book",strings,s,strings1,null,null,s1);
                break;
            case BOOK_ITEM:
                String bookId=uri.getPathSegments().get(1);
                cursor=db.query("Book",strings,"id=?",new String[]{bookId},null,null,s1);
                //查询table1表中的单条的数据
                break;
            case BOOK2_DIR:
                cursor=db.query("Category",strings,s,strings1,null,null,s1);
                //查询table2表中的所有的数据
                break;
            case BOOK2_ITEM:
                //查询table2表中的单条的数据
                String category=uri.getPathSegments().get(1);
                cursor=db.query("Category",strings,"id=?",new String[]{category},null,null,s1);
                break;
        }
        return cursor;
    }

    //其他的方法和query方法一样，都会携带uri，然后根据UriMatcher的match()方法来判断，
// 出调方希望返回哪张表，再根据表来操作

    @Override
    public Uri insert( Uri uri,  ContentValues contentValues) {
        //添加數據
        SQLiteDatabase db=mySQliteDataHelper.getWritableDatabase();
        Uri uriReturn=null;
        switch (sURIMatcher.match(uri)){
            case BOOK_DIR:
            case BOOK_ITEM:
                long newBOOKId=db.insert("Book",null,contentValues);
                uriReturn=Uri.parse("content://"+AUTHORITY+"/book/"+newBOOKId);
                break;
            case BOOK2_DIR:
            case BOOK2_ITEM:
                long newBOOK2ID=db.insert("Category",null,contentValues);
                uriReturn = Uri.parse("content://"+AUTHORITY+"/category/"+newBOOK2ID);
                break;
            default:
                break;

        }
        return uriReturn;
    }

    @Override
    public int delete( Uri uri,  String s,  String[] strings) {
        //删除数据
        SQLiteDatabase db=mySQliteDataHelper.getWritableDatabase();
        int deleteRows =0;
        switch (sURIMatcher.match(uri)){
            case BOOK_DIR:
                deleteRows=db.delete("Book",s,strings);
                break;
            case BOOK_ITEM:
                String bookId=uri.getPathSegments().get(1);
                deleteRows = db.delete("Book","id=?",new String[]{bookId});
                break;
            case BOOK2_DIR:
                deleteRows = db.delete("Category",s,strings);
                break;
            case BOOK2_ITEM:
                String book2Id= uri.getPathSegments().get(1);
                deleteRows = db.delete("Category","id=?",new String[]{book2Id});
                break;
            default:
                break;
        }
        return deleteRows;
    }

    @Override
    public int update( Uri uri,  ContentValues contentValues,  String s,  String[] strings) {
        //更新数据
        SQLiteDatabase db=mySQliteDataHelper.getWritableDatabase();
        int updatedRows =0;
        switch (sURIMatcher.match(uri)){
            case BOOK_DIR:
               updatedRows=db.update("Book",contentValues,s,strings);
                break;
            case BOOK_ITEM:
                String bookId=uri.getPathSegments().get(1);
                updatedRows = db.update("Book",contentValues,"id=?",new String[]{bookId});
                break;
            case BOOK2_DIR:
                 updatedRows = db.update("Category",contentValues,s,strings);
                break;
            case BOOK2_ITEM:
                 String book2Id= uri.getPathSegments().get(1);
                updatedRows = db.update("Category",contentValues,"id=?",new String[]{book2Id});
                break;
            default:
                break;
        }
        return updatedRows;
    }
}
