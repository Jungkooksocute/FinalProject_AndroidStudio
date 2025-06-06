package com.example.easytutofilemanager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;


//显示指定路径下的文件和文件夹列表
public class FileListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_file_list);



        //获取RecyclerView和TextView（用于显示“无文件”提示
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView noFilesText = findViewById(R.id.nofiles_textview);



        //从Intent中获取路径path，之前MainActivity传过来的。并创建File对象
        String path = getIntent().getStringExtra("path");
        File root = new File(path);

        //获取该路径下的所有文件和文件夹（数组）
        File[] filesAndFolders = root.listFiles();


        //如果没有文件或文件夹，则显示“无文件”提示，并返回
        if(filesAndFolders==null || filesAndFolders.length ==0){
            noFilesText.setVisibility(View.VISIBLE);
            return;
        }


        //如果有文件，则隐藏提示，并设置RecyclerView的布局管理器和适配器（MyAdapter）
        noFilesText.setVisibility(View.INVISIBLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(getApplicationContext(),filesAndFolders));




    }
}