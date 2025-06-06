package com.example.easytutofilemanager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import android.Manifest;

/*
* MainActivity:
* 主要功能是请求存储权限，并在权限允许后跳转到文件列表界面。
* */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main); // 设置布局文件


        //初始化按钮并设置点击事件
        MaterialButton storageBtn = findViewById(R.id.storage_btn);

        storageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermission()){
                    //授予权限
                    Intent intent = new Intent(MainActivity.this, FileListActivity.class);
                    //MainActivity当前的Activity类，FileListActivity要启用的目标类
                    String path = Environment.getExternalStorageDirectory().getPath();
                    // 获取外部存储根目录路径

                    intent.putExtra("path",path);
                    // 将路径作为额外数据传递给下一个Activity

                    startActivity(intent);
                }else{
                    //权限未授予，请求权限
                    requestPermission();

                }
            }
        });

    }


    //当用户点击按钮时，首先检查是否具有写外部存储的权限
    private boolean checkPermission(){
        //使用`ContextCompat.checkSelfPermission`
        // 检查应用是否被授予了`WRITE_EXTERNAL_STORAGE`权限。
        int result = ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED){
            return true;
        }else
            return false;
    }

    private void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            // 如果用户之前拒绝过权限请求，则提示用户需要权限
            Toast.makeText(MainActivity.this,"Storage permission is requires,please allow from settings",Toast.LENGTH_SHORT).show();
        }else// 否则，直接请求权限
            ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},111);
    }



}