package com.example.zhangqi.messagehandle;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Main2Activity extends AppCompatActivity {

    private static TextView tv = null;

    //自定义的Message类型
    public final static int MESSAGE_WEX_1 = 1;

    //主线程中创建Handler
    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_WEX_1:
                    tv.setText("主线程发送，子线程接收消息后回发，主线程修改UI");
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv = (TextView) findViewById(R.id.textView1);

        //创建子线程
        new LooperThread().start();

        //点击按钮向子线程发送消息
        Button btn = (Button) findViewById(R.id.btnSendMessage);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LooperThread.sHandler.sendEmptyMessage(MESSAGE_WEX_1);
            }
        });
    }

    //定义子线程
    static class LooperThread extends Thread {
        public static Handler sHandler = null;

        @Override
        public void run() {
            //创建消息循环
            Looper.prepare();

            sHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case MESSAGE_WEX_1:
                            mHandler.sendEmptyMessage(MESSAGE_WEX_1);
                            break;
                    }
                }
            };
            //开启消息循环
            Looper.loop();
        }
    }
}















