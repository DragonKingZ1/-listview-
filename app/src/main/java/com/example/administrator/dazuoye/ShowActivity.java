package com.example.administrator.dazuoye;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class ShowActivity extends AppCompatActivity {
    private static final int LOAD_NEWS_SUCCESS = 1;
    private ListView lvs;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == LOAD_NEWS_SUCCESS) {
                List<Shows> showL = (List<Shows>) msg.obj;
                //数据源  --- > 适配器
                showAdapter adapter = new showAdapter(ShowActivity.this,showL);
                lvs.setAdapter(adapter);
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhuye);
        lvs = findViewById(R.id.mylistview);
        Button onshow=findViewById(R.id.onShow);
        Button willshow=findViewById(R.id.willShow);

        loadNewsFromNet1();

        onshow.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent in2 = new Intent();
                in2.setClass(ShowActivity.this, ZhuYeActivity.class);
                startActivity(in2);
            }
        } );
    }

    private void loadNewsFromNet1() {

        new Thread() {
            @Override
            public void run() {

                //访问网络拿到对应的数据
                //StringCallBack 只能把服务器返回的结果集转换成字符串
                OkHttpUtils.get()
                        .url("https://api-m.mtime.cn/Movie/MovieComingNew.api?locationId=290")
                        .build()
                        .execute(new ShowActivity.MyCallBack());
                }
            }.start();


    }

    public class MyCallBack extends Callback<List<Movie>> {
        //自定义回调对象
        //CallBack就是框架给提供的回调对象基类
        //将服务器返回的数据转成你想要的对象
        @Override
        //response ---->inputStream(json)
        public List<Movie> parseNetworkResponse(Response response, int id) throws Exception {
            String message = response.body().string();
            JSONObject jsonObject = new JSONObject(message);
            JSONArray jsonMoives = (JSONArray) jsonObject.get("attention");
            Gson gson = new Gson();
            List<Shows> shows = new ArrayList<>();
            for (int i = 0; i < jsonMoives.length(); i++) {
                JSONObject jsonMoive = (JSONObject) jsonMoives.get(i);
                //Gson  ----> jsonObject字符串 --->javabean
                //fromJson就能够把json字符串转成指定的对象
                //有一个要求，json字符串中中的属性要和对象的属性同名
                Shows show = gson.fromJson(jsonMoive.toString(), Shows.class);
                shows.add(show);
            }
            Message msg = Message.obtain();
            msg.obj = shows;
            msg.what = LOAD_NEWS_SUCCESS;
            handler.sendMessage(msg);
            // Log.i("all", "parseNetworkResponse: "+movies);
            return null;
        }


        //网络请求出错调用的方法
        @Override
        public void onError(Call call, Exception e, int id) {

        }

        //网络请求成功调用的方法
        @Override
        public void onResponse(List<Movie> response, int id) {

        }
    }
}