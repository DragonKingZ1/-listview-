package com.example.administrator.dazuoye;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class showAdapter extends BaseAdapter {
    public Context context;
    public List<Shows> list;

    public showAdapter(Context context, List<Shows> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View temp = null;
        if (view == null){
            temp = LayoutInflater.from(context)
                    .inflate(R.layout.content,viewGroup,false);
        }else {
            temp = view;
        }
        //LayoutInflater layoutInflater=LayoutInflater.from(context);
        //View view=layoutInflater.inflate(R.layout.content,parent,false);
        TextView tv1=temp.findViewById(R.id.movietitle);
        TextView tv2=temp.findViewById(R.id.actorName1);
        TextView tv3=temp.findViewById(R.id.actorName2);
        TextView tv4=temp.findViewById(R.id.directorName);
        TextView tv5=temp.findViewById(R.id.commomSpecial);
        //ImageView img=temp.findViewById(R.id.myImage);

        //绑定数据
        Shows newItem = this.list.get(position);
        String imagePath = newItem.getImage();
        //loadImageFromNet(imagePath);
        //ImageView没有从网络加载图片的方法
        //可以自己创建一个图片view去加载网络图片
        SmartImageView smartImageView = temp.findViewById(R.id.myImage);
        smartImageView.loadImageFromNet(imagePath);



        tv1.setText("影片名："+list.get(position).getTitle());
        tv2.setText("主演一："+list.get(position).getActor1());
        tv3.setText("主演二："+list.get(position).getActor2());
        tv4.setText("导演："+list.get(position).getDirector());
        tv5.setText("影片类型："+list.get(position).getType());
        //图片加载??????????
        return temp;
    }
}