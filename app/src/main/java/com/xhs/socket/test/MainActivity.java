package com.xhs.socket.test;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.koushikdutta.async.ByteBufferList;
import com.xhs.socket.test.constant.Constant;
import com.xhs.socket.test.tcp.Client;
import com.xhs.socket.test.utils.ScreenUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.UnsupportedEncodingException;


public class MainActivity extends AppCompatActivity {


    private Client client;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void activity_main(Message message){

        switch (message.what){
            case Constant.CONNECT_FAILED:
                ToastUtils.setBgColor(Color.RED);
                ToastUtils.setMsgColor(Color.GREEN);
                ToastUtils.showShort("连接失败!!");
                break;
            case Constant.SEND_FAILED:
                ToastUtils.setBgColor(Color.RED);
                ToastUtils.setMsgColor(Color.GREEN);
                ToastUtils.showShort("发送失败!!");
                break;
            case Constant.ABORTIVE_DISCONNECT:
                ToastUtils.setBgColor(Color.RED);
                ToastUtils.setMsgColor(Color.GREEN);
                ToastUtils.showShort("异常断开，稍后重连...!!");
                client.setup();
                break;
            case Constant.MESSAGE_RECEIVED:
                //点亮屏幕
                ScreenUtils.wakeUpAndUnlock();
                startActivity(new Intent(this,MainActivity.class));
                ToastUtils.setBgColor(Color.GRAY);
                ToastUtils.setMsgColor(Color.GREEN);
                ToastUtils.showShort("收到消息=="+(String) message.obj);
                break;
            case Constant.SEND_SUCCESS:
                ToastUtils.setBgColor(Color.GREEN);
                ToastUtils.setMsgColor(Color.RED);
                ToastUtils.showShort("发送成功!!");
                break;
            case Constant.NORMAL_DISCONNECT:
                ToastUtils.setBgColor(Color.GREEN);
                ToastUtils.setMsgColor(Color.RED);
                ToastUtils.showShort("正常断开,正在重连...!!");
                client.setup();
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        initClient();
        //initNetty();

    }

    private void initNetty() {

    }

    private void initClient() {
        client = new Client(Constant.IP, Constant.PORT);
        client.setup();
        client.setMssageListener(new Client.ImMessageReciver() {
            @Override
            public void reciverMessage(ByteBufferList bb) {
                Message message = Message.obtain();
                message.what= Constant.MESSAGE_RECEIVED;
                try {
                    String str = new String(bb.getAllByteArray());
                    byte[] gbkbt = str.getBytes("GB2312");
                    message.obj=new String(gbkbt, "GB2312");
                    EventBus.getDefault().post(message);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                // Logger.e("收到消息=="+ message.obj);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
