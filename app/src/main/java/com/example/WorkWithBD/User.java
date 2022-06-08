package com.example.WorkWithBD;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class User extends AppCompatActivity {


    Button back, getNews;
    DataBaseHelperUsers databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getNews = findViewById(R.id.getNewsu);
        back = findViewById(R.id.backU);
        databaseHelper = new DataBaseHelperUsers(this);
        getNews.setOnClickListener(view ->{
            Cursor res = databaseHelper.getdataNews();
            if(res.getCount() == 0)
            {
                Toast.makeText(getApplicationContext(),"Нет данных",Toast.LENGTH_LONG).show();
                return;
            }
            StringBuilder buffer = new StringBuilder();
            while (res.moveToNext()){
                buffer.append("Оглавление: ").append(res.getString(1)).append("\n");
                buffer.append("Текст: ").append(res.getString(2)).append("\n");
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(User.this);
            builder.setCancelable(true);
            builder.setMessage(buffer.toString());
            builder.show();
        });
        back.setOnClickListener(view ->{
            Intent a = new Intent(this,MainActivity.class);
            startActivity(a);


        });
    }
}