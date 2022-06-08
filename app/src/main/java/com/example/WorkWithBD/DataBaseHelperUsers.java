package com.example.WorkWithBD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelperUsers extends SQLiteOpenHelper {
    public DataBaseHelperUsers(Context context) {
        super(context, "UserDataBase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table UserInfo(ID_User INTEGER NOT NULL primary key AUTOINCREMENT UNIQUE, " +
                "login TEXT, password TEXT, role TEXT)"); //role - 1 админ \ 0 пользователь
        db.execSQL("create Table NewsInfo(ID_News INTEGER NOT NULL primary key AUTOINCREMENT UNIQUE, " +
                "name_news TEXT, text TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists UserInfo");
        db.execSQL("drop Table if exists NewsInfo");
    }


    public Boolean insertUser(String _login, String _passwd, String role)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("login",_login);
        contentValues.put("password",_passwd);
        contentValues.put("role",role);
        long result = db.insert("UserInfo",null,contentValues);
        return result != -1;
    }
    public Boolean insertNews(String _name_news, String _text)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name_news",_name_news);
        contentValues.put("text",_text);
        long result = db.insert("NewsInfo",null,contentValues);
        return result != -1;
    }


    public Boolean deleteUser(String login)
    {
        SQLiteDatabase db = this.getWritableDatabase();


        int result = db.delete("UserInfo",
                "login = " + "'" + login + "'", null
        );
        db.close();

        return result > 0;
    }
    public Boolean deleteNews(String name_news)
    {
        SQLiteDatabase db = this.getWritableDatabase();


        int result = db.delete("NewsInfo",
                "name_news = " + "'" + name_news + "'", null
        );
        db.close();

        return result > 0;
    }

    public Boolean editNews(String name_news, String new_name, String new_text)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name_news",new_name);
        values.put("text",new_text);
        int result = db.update("NewsInfo",
          values,"name_news = " + "'" + name_news + "'",null);
        db.close();
        return result > 0;
    }


    public Cursor getdataUsers()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("Select * from UserInfo",null);
    }
    public Cursor getdataNews()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("Select * from NewsInfo",null);
    }


}
