package com.example.easytutofilemanager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;


//RecyclerView的适配器（Adapter），用于显示文件和文件夹列表
//适配器类`MyAdapter`继承自`RecyclerView.Adapter`，
// 使用自定义的`ViewHolder`。
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

Context context;//上下文对象，启动Activity等
File[] filesAndFolders;//文件数组，包含要显示的文件和文件夹。

public MyAdapter(Context context, File[] filesAndFolders){
    this.context = context;
    this.filesAndFolders = filesAndFolders;
}


//内部类ViewHolder，包含每个列表项的视图引用。
//textView：显示文件/文件夹名称。
//imageView：显示图标。
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        return new ViewHolder(view);    }

    //创建新的`ViewHolder`实例，通过布局填充器将`recycler_item`布局文件转换为视图
    @Override
public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {

    File selectedFile = filesAndFolders[position];
    holder.textView.setText(selectedFile.getName());
    // 根据类型设置图标


    if(selectedFile.isDirectory()){
        holder.imageView.setImageResource(R.drawable.ic_baseline_folder_24);
    }else{
        holder.imageView.setImageResource(R.drawable.ic_baseline_insert_drive_file_24);
    }



    holder.itemView.setOnClickListener(new View.OnClickListener() {

    //点击事件：打开文件（夹）
        @Override
        public void onClick(View v) {
            if(selectedFile.isDirectory()){
                //如果文件夹，启动FileListActivity并传入路径，打开操作
                Intent intent = new Intent(context, FileListActivity.class);


                String path = selectedFile.getAbsolutePath();
                intent.putExtra("path",path);


                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }else{
                //打开文件，使用ACTION_VIEW打开文件（这里固定为图片类型，但实际文件可能不是图片）。
                try {
                    Intent intent = new Intent();
                    intent.setAction(android.content.Intent.ACTION_VIEW);
                    String type = "image/*";
                    intent.setDataAndType(Uri.parse(selectedFile.getAbsolutePath()), type);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(context.getApplicationContext(),"Cannot open the file",Toast.LENGTH_SHORT).show();
                }
            }
        }
    });

    //长按事件
    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {


            //（弹出菜单）
            PopupMenu popupMenu = new PopupMenu(context,v);
            popupMenu.getMenu().add("DELETE");
            popupMenu.getMenu().add("MOVE");
            popupMenu.getMenu().add("RENAME");

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {


                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if(item.getTitle().equals("DELETE")){
                        // 删除文件
                        boolean deleted = selectedFile.delete();
                        if(deleted){
                            Toast.makeText(context.getApplicationContext(),"DELETED ",Toast.LENGTH_SHORT).show();
                            v.setVisibility(View.GONE);
                            // 隐藏该项
                        }
                    }// 其他菜单项暂未实现
                    if(item.getTitle().equals("MOVE")){

                        Toast.makeText(context.getApplicationContext(),"MOVED ",Toast.LENGTH_SHORT).show();

                    }
                    if(item.getTitle().equals("RENAME")){
                        Toast.makeText(context.getApplicationContext(),"RENAME ",Toast.LENGTH_SHORT).show();

                    }

//                    长按列表项时，弹出包含DELETE、MOVE、RENAME选项的菜单。
//
//                    目前只实现了DELETE功能，其他两个选项仅显示Toast。

                    return true;
                }
            });

            popupMenu.show();
            return true;
        }
    });


}


//返回文件数组的长度
@Override
public int getItemCount() {
    return filesAndFolders.length;
}


public class ViewHolder extends RecyclerView.ViewHolder{

    TextView textView;
    ImageView imageView;

    public ViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.file_name_text_view);
        imageView = itemView.findViewById(R.id.icon_view);
    }
}
}
