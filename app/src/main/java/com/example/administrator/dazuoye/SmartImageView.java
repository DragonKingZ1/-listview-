package com.example.administrator.dazuoye;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@SuppressLint("AppCompatCustomView")
public class SmartImageView extends ImageView {
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bitmap bitmap = (Bitmap) msg.obj;
            //任何的imageview都有这个方法
            setImageBitmap(bitmap);
        }
    };


    public SmartImageView(Context context) {
        super(context);
    }

    public SmartImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void loadImageFromNet(final String path){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection httpURLConnection =
                            (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    int resultCode = httpURLConnection.getResponseCode();
                    if (resultCode == 200){
                        InputStream inputStream = httpURLConnection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        Message message = Message.obtain();
                        message.obj = bitmap;
                        handler.sendMessage(message);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
