package com.example.WorkWithBD;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Admin extends AppCompatActivity {


    EditText oglavlenie,textN;
    Button getNewsA,addNewsA,delNewsA,editNewsA,back;
    DataBaseHelperUsers databaseHelper;
    String editingOglavlenie = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getNewsA = findViewById(R.id.GetAll);
        addNewsA = findViewById(R.id.addN);
        delNewsA = findViewById(R.id.delN);
        editNewsA = findViewById(R.id.changeN);
        back = findViewById(R.id.back);
        databaseHelper = new DataBaseHelperUsers(this);
        oglavlenie = findViewById(R.id.oglavlenie);
        textN = findViewById(R.id.textNov);

        getNewsA.setOnClickListener(view ->{
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
            AlertDialog.Builder builder = new AlertDialog.Builder(Admin.this);
            builder.setCancelable(true);
            builder.setMessage(buffer.toString());
            builder.show();
        });
        addNewsA.setOnClickListener(view ->{
            Boolean checkInsertData = databaseHelper.insertNews(oglavlenie.getText().toString(),
                    textN.getText().toString());
            if(checkInsertData)
            {
                Toast.makeText(getApplicationContext(),"Данные успешно добавлены",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Произошла ошибка",Toast.LENGTH_LONG).show();
            }
        });
        delNewsA.setOnClickListener(view ->{
            Boolean checkInsertData = databaseHelper.deleteNews(oglavlenie.getText().toString());
            if(checkInsertData)
            {
                Toast.makeText(getApplicationContext(),"Данные успешно удалены",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Произошла ошибка",Toast.LENGTH_LONG).show();
            }
        });
        editNewsA.setOnClickListener(view ->{
            if(oglavlenie.getText().toString() == null || oglavlenie.getText().toString() == "")
            {
                Toast.makeText(getApplicationContext(),"Введите сначала оглавление новости, которую хотите изменить, а после новые данные",Toast.LENGTH_LONG).show();
            }
            else
            {
                Cursor res = databaseHelper.getdataNews();
                if(res.getCount() == 0)
                {
                    Toast.makeText(getApplicationContext(),"Нет данных чтобы изменить",Toast.LENGTH_LONG).show();
                    return;
                }
                while (res.moveToNext()){

                        if(editingOglavlenie.equals(""))
                        {
                            editingOglavlenie = oglavlenie.getText().toString();
                            Toast.makeText(getApplicationContext(),"Теперь введите новые данные для изменение новости",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Boolean checkInsertData =  databaseHelper.editNews(editingOglavlenie,oglavlenie.getText().toString(),textN.getText().toString());
                            if(checkInsertData)
                            {
                                Toast.makeText(getApplicationContext(),"Данные успешно изменены",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Произошла ошибка",Toast.LENGTH_LONG).show();
                            }
                            editingOglavlenie = "";
                        }
                }
            }
        });
        back.setOnClickListener(view ->{
            Intent a = new Intent(this,MainActivity.class);
            startActivity(a);

        });

    }
}