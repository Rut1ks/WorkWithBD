package com.example.WorkWithBD;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    Button back,regist;
    EditText login,password;
    RadioButton admin,user;
    DataBaseHelperUsers databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        databaseHelper = new DataBaseHelperUsers(this);
        back = findViewById(R.id.button4);
        regist = findViewById(R.id.button3);
        login = findViewById(R.id.loginN);
        password = findViewById(R.id.passwordN);
        admin = findViewById(R.id.radioButton);
        user = findViewById(R.id.radioButton2);

        back.setOnClickListener(view ->{
            Intent back = new Intent(this,MainActivity.class);
            startActivity(back);

        });

        regist.setOnClickListener(view ->{
            Cursor res = databaseHelper.getdataUsers();
            if(res.getCount() == 0)
            {
                String userType = "0";
                if(admin.isChecked() == true)
                    userType = "1";
                Boolean checkInsertData = databaseHelper.insertUser(login.getText().toString(),
                        password.getText().toString(),userType);
                if(checkInsertData)
                {
                    Toast.makeText(getApplicationContext(),"Данные успешно добавлены",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Произошла ошибка",Toast.LENGTH_LONG).show();
                }
                Intent back = new Intent(this,MainActivity.class);
                startActivity(back);
            }
            while (res.moveToNext()){
                //начинается отсчёт с 0
                if( login.getText().toString() == res.getString(1))
                {
                    Toast.makeText(getApplicationContext(),"Такого пользователя уже есть!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    String userType = "0";
                    if(admin.isChecked() == true)
                        userType = "1";
                    Boolean checkInsertData = databaseHelper.insertUser(login.getText().toString(),
                            password.getText().toString(),userType);
                    if(checkInsertData)
                    {
                        Toast.makeText(getApplicationContext(),"Данные успешно добавлены",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Произошла ошибка",Toast.LENGTH_LONG).show();
                    }
                    Intent back = new Intent(this,MainActivity.class);
                    startActivity(back);
                }
            }

        });

    }
}