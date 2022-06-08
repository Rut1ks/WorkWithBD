package com.example.WorkWithBD;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.Executor;


import android.database.Cursor;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button btnScan;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    EditText login,password;
    Button avtoriz,registr;
    DataBaseHelperUsers databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //btnScan = findViewById(R.id.btnScan);
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Log.e("ErrorAUTH",errString.toString());
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(MainActivity.this, "Успешно", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Log.e("FailedAUTH","FAIL!!!");
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Авторизация").setSubtitle("Прислоните палец").setNegativeButtonText("Отмена").build();

            biometricPrompt.authenticate(promptInfo);






        databaseHelper = new DataBaseHelperUsers(this);
        avtoriz = findViewById(R.id.button);
        registr = findViewById(R.id.button2);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);

        avtoriz.setOnClickListener(view ->{
                    Cursor res = databaseHelper.getdataUsers();
                    if(res.getCount() == 0)
                    {
                        Toast.makeText(getApplicationContext(),"Нет пользователей",Toast.LENGTH_LONG).show();
                        return;
                    }
                    while (res.moveToNext()){
                        //начинается отсчёт с 0
                        String loginIn = login.getText().toString();
                        String loginOut = res.getString(1);
                        String paswIn = password.getText().toString();
                        String pasOut = res.getString(2);
                        if(loginIn.equals(loginOut))
                        {
                            if(paswIn.equals(pasOut))
                            {
                                String role = res.getString(3).toString();
                                if(role.equals("1"))
                                {
                                    Intent intent = new Intent(this,Admin.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Intent intent = new Intent(this,User.class);
                                    startActivity(intent);
                                }
                            }
                            else
                                Toast.makeText(getApplicationContext(),"Не верный пароль",Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(),"Такого пользователя нет",Toast.LENGTH_LONG).show();


                    }

        }
        );

        registr.setOnClickListener(view ->{
            Intent back = new Intent(this,Registration.class);
            startActivity(back);
        }
        );
    }
}