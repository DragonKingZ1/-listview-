package com.example.administrator.dazuoye;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

public class ZhuYeActivity extends AppCompatActivity {

    private static final int LOAD_NEWS_SUCCESS = 1;
    private ListView lvs;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == LOAD_NEWS_SUCCESS) {
                List<Movie> movieL = (List<Movie>) msg.obj;
                //数据源  --- > 适配器
                movieAdapter adapter = new movieAdapter(ZhuYeActivity.this,movieL);
                lvs.setAdapter(adapter);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhuye);
        Button onshow=findViewById(R.id.onShow);
        Button willshow=findViewById(R.id.willShow);
        lvs = findViewById(R.id.mylistview);


        //加载网络喜xml文件,正在售票
        loadNewsFromNet();

        //即将上映
        willshow.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent in2 = new Intent();
                in2.setClass(ZhuYeActivity.this, ShowActivity.class);
                startActivity(in2);
            }
        } );

        }

    private void loadNewsFromNet() {

        new Thread() {
            @Override
            public void run() {
                //String path = "https://api-m.mtime.cn/PageSubArea/HotPlayMovies.api?locationId=290";
                //try {
                   /* URL url = new URL(path);
                    HttpURLConnection httpURLConnection
                            = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    int returnCode = httpURLConnection.getResponseCode();*/
                    //if (returnCode == 200) {
                        //服务器正常处理请求
                       /* InputStream inputStream = httpURLConnection.getInputStream();
                        //inputstream中存储着xml文件的流数据，要求根据流解析xml文件
                        //DOM SAX
                        //读取StreamUtil那个类，得到解析文件，放到LIST集合中，传给主线程
                        List<News> newsList = StreamUtil.decodeInputStreamByXml(inputStream);*/

                       //访问网络拿到对应的数据
                        //StringCallBack 只能把服务器返回的结果集转换成字符串
                        OkHttpUtils.get()
                                .url("https://api-m.mtime.cn/Showtime/LocationMovies.api?locationId=290")
                                .build()
                                .execute(new MyCallBack());



                    }

               /* } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
           // }
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
            JSONArray jsonMoives = (JSONArray) jsonObject.get("ms");
            Gson gson = new Gson();
            List<Movie> movies = new ArrayList<>();
            for (int i = 0; i < jsonMoives.length(); i++) {
                JSONObject jsonMoive = (JSONObject) jsonMoives.get(i);
                //Gson  ----> jsonObject字符串 --->javabean
                //fromJson就能够把json字符串转成指定的对象
                //有一个要求，json字符串中中的属性要和对象的属性同名
                Movie movie = gson.fromJson(jsonMoive.toString(), Movie.class);
                movies.add(movie);
            }
            Message msg = Message.obtain();
            msg.obj = movies;
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




























