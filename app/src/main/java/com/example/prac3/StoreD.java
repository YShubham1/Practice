package com.example.prac3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class StoreD extends SQLiteOpenHelper {
    public StoreD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table pdetal(name varchar(30),number int(15),gender varchar(9),docter varchar(20),date varchar(20),time varchar(20),fees varchar(5))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertD(String n,String no,String g,String d,String dt,String tm,String f){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("insert into pdetal values(?,?,?,?,?,?,?)",new String[]{n,no,g,d,dt,tm,f});
        db.close();
    }

    public String dateDisplay(String dt){
        String data="";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cr= db.rawQuery("select * from pdetal where date=?",new String[]{dt});
        while (cr.moveToNext()){
            String a=cr.getString(0);
            String b=cr.getString(1);
            String d=cr.getString(2);
            String e=cr.getString(3);
            String f=cr.getString(5);
            String g=cr.getString(6);
            data ="\n name :"+a+"\n number :"+b+"\n gender :"+d+"\n docter :"+e+"\n time :"+f+"\n fees :"+g;
        }
        return data;
    }

    public String docterDisplay(String d){
        String data="";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cr= db.rawQuery("select * from pdetal where docter=?",new String[]{d});
        while (cr.moveToNext()){
            String a=cr.getString(0);
            String b=cr.getString(1);
            String dm=cr.getString(2);
            String e=cr.getString(4);
            String f=cr.getString(5);
            String g=cr.getString(6);
            data +="\n name :"+a+"\n number :"+b+"\n gender :"+dm+"\n date :"+e+"\n time :"+f+"\n fee :"+g;
        }
        return data;
    }

    public String fee(String n){
        String data="";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cr=db.rawQuery("select * from pdetal where name=?",new String[]{n});
        while(cr.moveToNext()){
            String a=cr.getString(6);
            data +="\n Total fare is"+a;
        }
        return data;
    }


}
