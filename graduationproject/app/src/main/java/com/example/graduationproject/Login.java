package com.example.graduationproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;

/**
 * Created by 731775721 on 2017/3/4.
 */

public class Login extends Activity {
    AVUser user = new AVUser();

    EditText etUserName, etPass;
    Button bnLogin, bnCancel,bn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        if (AVUser.getCurrentUser() != null) {
            startActivity(new Intent(Login.this, MainpageActivity.class));
            Login.this.finish();
        }

        //获取所有控件
        etUserName = (EditText) findViewById(R.id.userEditText);
        etPass = (EditText) findViewById(R.id.pwdEditText);
        bnLogin = (Button) findViewById(R.id.bnLogin);
        bnCancel = (Button) findViewById(R.id.bnCancel);

        String password= etPass.getText().toString();
        String username=etUserName.getText().toString();

        user.setUsername(username);// 设置用户名
        user.setPassword(password);// 设置密码


        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 注册成功，把用户对象赋值给当前用户 AVUser.getCurrentUser()
                    startActivity(new Intent(Login.this, MainpageActivity.class));
                    Login.this.finish();
                } else {
                    // 失败的原因可能有多种，常见的是用户名已经存在。
                   // showProgress(false);
                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
//        bnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (validate()) {
//                    Intent intent = new Intent(Login.this, MainpageActivity.class);
//                    startActivity(intent);
//                   // onDestroy();
//                }
//            }
//        });
        AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
                    Login.this.finish();
                    startActivity(new Intent(Login.this, MainActivity.class));
                } else {
                    //showProgress(false);
                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean validate() {
        String name = etUserName.getText().toString();
        String pass = etPass.getText().toString();
        if (name.equals("")) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
        }
        if (pass.equals("")) {
            Toast.makeText(this, "密码", Toast.LENGTH_SHORT).show();

        }
        if (name.equals("1") && pass.equals("1")) {
        return true;
        }

        return false;
    }



}
