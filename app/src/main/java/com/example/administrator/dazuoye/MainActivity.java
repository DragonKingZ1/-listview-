package com.example.administrator.dazuoye;

import android.content.Intent;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText username=findViewById(R.id.username);
        final EditText password=findViewById(R.id.password);
        Button regist=findViewById(R.id.regist);
        Button login=findViewById(R.id.login);




        //创建库同时创建表
        final UserDataHelper userDataHelper=new UserDataHelper("user.db",this);
        userDataHelper.createTable();
        //注册按钮事件
       /* regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=username.getText().toString().trim();
                String pwd=password.getText().toString().trim();
                //按照名称查询，看是否注册时用户名已存在
               User userfind=userDataHelper.queryUserByNmae("name");
               // String findname=user.getUsername();
                if(userfind==null){
                    Toast.makeText(MainActivity.this, "注册成功！请登录", Toast.LENGTH_LONG).show();
                    User user1 = new User();
                    user1.setUsername(name);
                    user1.setPassword(pwd);
                    userDataHelper.addUser(user1);
                    username.setText("");
                    password.setText("");
                }
                else if(userfind.getUsername().equals(name)){
                    Log.i("对象传过来了","userDataHelper.UserName(name)");
                    Toast.makeText(MainActivity.this, "此用户名已经存在！", Toast.LENGTH_LONG).show();
                    username.setText("");
                    password.setText("");
                }
                //Log.i("外面的","userDataHelper.UserName(name)");
               *//* if(usernamefind.equals(name)){
                    Log.i("里面的","userDataHelper.UserName(name)");
                    Toast.makeText(MainActivity.this, "此用户名已经存在！", Toast.LENGTH_LONG).show();
                    username.setText("");
                    password.setText("");}*//*
               else if(TextUtils.isEmpty(name)||TextUtils.isEmpty(pwd)) {
                    Toast.makeText(MainActivity.this, "用户名或者密码不能为空", Toast.LENGTH_LONG).show();
                }

                }
        });*/

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=username.getText().toString().trim();
                String pwd=password.getText().toString().trim();
                //按照名称查询，看是否注册时用户名已存在
                if(TextUtils.isEmpty(name)||TextUtils.isEmpty(pwd)) {
                    Toast.makeText(MainActivity.this, "用户名或者密码不能为空", Toast.LENGTH_LONG).show();
                }
                // String findname=user.getUsername();
                try {
                    User userfind=userDataHelper.queryUserByNmae(name);
                    if(userfind.getUsername().equals(name)) {
                        Toast.makeText(MainActivity.this, "此用户名已存在，请重新注册!", Toast.LENGTH_LONG).show();
                        username.setText("");
                        password.setText("");
                    }
                }catch(NullPointerException e){
                    User user1 = new User();
                    user1.setUsername(name);
                    user1.setPassword(pwd);
                    userDataHelper.addUser(user1);
                    username.setText("");
                    password.setText("");
                    Toast.makeText(MainActivity.this, "注册成功，请登录!", Toast.LENGTH_LONG).show();
                }


                //Log.i("外面的","userDataHelper.UserName(name)");
               /* if(usernamefind.equals(name)){
                    Log.i("里面的","userDataHelper.UserName(name)");
                    Toast.makeText(MainActivity.this, "此用户名已经存在！", Toast.LENGTH_LONG).show();
                    username.setText("");
                    password.setText("");}*/


            }
        });

            //登录
        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String name=username.getText().toString().trim();
                String pwd=password.getText().toString().trim();
                if(TextUtils.isEmpty(name)||TextUtils.isEmpty(pwd)){
                    Toast.makeText(MainActivity.this,"用户名或者密码不能为空",Toast.LENGTH_LONG).show();
                }else{
                    User user=userDataHelper.queryUserByNmae(name);
                    String username=user.getUsername();
                    String password=user.getPassword();
                    if((username.equals(name)&&(password.equals(pwd)))){
                        Toast.makeText(MainActivity.this, "欢迎来到龙眼电影", Toast.LENGTH_LONG).show();
                        Intent in1 = new Intent();
                        in1.setClass(MainActivity.this, ZhuYeActivity.class);
                        startActivity(in1);
                    }else{
                        Toast.makeText(MainActivity.this,"用户名或密码错误",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


    }
}
