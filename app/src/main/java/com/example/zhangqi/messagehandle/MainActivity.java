package com.example.zhangqi.messagehandle;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Android中的消息处理实例之一
 * * 一、在主线程内发送消息
 * 1.使用post
 * 2.使用postDelay
 * 3.使用sendMessage
 * 4.使用Message.sentToTarget
 * 二、在子线程中使用Handler
 * 1.使用post
 * 2.使用postDelay
 * 3.使用sendMessage
 * 4.使用Message.sentToTarget
 */
public class MainActivity extends AppCompatActivity {

    private Runnable runnable = null;
    private Runnable runnableDelay = null;
    private Runnable runnableInThread = null;
    private Runnable runnableDelayInThread = null;
    private static TextView tv;
    private static TextView tvOnOtherThread;

    //自定义Message类型
    public final static int MESSAGE_WXB_1 = 1;
    public final static int MESSAGE_WXB_2 = 2;
    public final static int MESSAGE_WXB_3 = 3;
    public final static int MESSAGE_WXB_4 = 4;

    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_WXB_1:
                    tv.setText("invoke sendMessage in main thread");
                    break;
                case MESSAGE_WXB_2:
                    tv.setText("Message.sendToTarget in main thread");
                    break;
                case MESSAGE_WXB_3:
                    tvOnOtherThread.setText("invoke sendMessage in other thread");
                    break;
                case MESSAGE_WXB_4:
                    tvOnOtherThread.setText("Message.sendToTarget in other thread");
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tvOnMainThread);
        tvOnOtherThread = (TextView) findViewById(R.id.tvOnOtherThread);

        //1. post
        runnable = new Runnable() {
            @Override
            public void run() {
                tv.setText(getString(R.string.postRunnable));
            }
        };
        Button handler_post = (Button) findViewById(R.id.btnHandlerpost);
        handler_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.post(runnable);
            }
        });

        //2. postDelay
        runnableDelay = new Runnable() {
            @Override
            public void run() {
                tv.setText(getString(R.string.postRunnableDelay));
            }
        };
        Button handler_post_delay = (Button) findViewById(R.id.btnHandlerPostdelay);
        handler_post_delay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.postDelayed(runnableDelay, 1000); //1秒后执行
            }
        });

        //3. sendMessage
        Button btnSendMessage = (Button) findViewById(R.id.btnSendMessage);
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = mHandler.obtainMessage();
                msg.what = MESSAGE_WXB_1;
                mHandler.sendMessage(msg);
            }
        });

        //4. Message.sendToTarget
        Button btnSendtoTarget = (Button) findViewById(R.id.btnSendtoTarget);
        btnSendtoTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = mHandler.obtainMessage();
                msg.what = MESSAGE_WXB_2;
                msg.sendToTarget();
            }
        });

        //在其他线程中发送消息
        //1. post
        runnableInThread = new Runnable() {
            @Override
            public void run() {
                tvOnOtherThread.setText(getString(R.string.postRunnableInThread));
            }
        };

        Button btnPost = (Button) findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        mHandler.post(runnableInThread);
                    }
                }.start();
            }
        });

        //2. postDelay
        runnableDelayInThread = new Runnable() {
            @Override
            public void run() {
                tvOnOtherThread.setText(getString(R.string.postRunnableDelayInThread));
            }
        };
        Button btnPostDelay = (Button) findViewById(R.id.btnPostDelay);
        btnPostDelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        mHandler.postDelayed(runnableDelayInThread, 1000);
                    }
                }.start();
            }
        });

        //3. sendMessage
        Button btnSendMessage2 = (Button) findViewById(R.id.btnSendMessage2);
        btnSendMessage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        Message msg = mHandler.obtainMessage();
                        msg.what = MESSAGE_WXB_3;
                        mHandler.sendMessage(msg);
                    }
                }.start();
            }
        });

        //4. Message.sendToTarget
        Button btnSendToTarget2 = (Button) findViewById(R.id.btnSendToTarget2);
        btnSendToTarget2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        mHandler.obtainMessage(MESSAGE_WXB_4).sendToTarget();
                    }
                }.start();
            }
        });
    }
}


















