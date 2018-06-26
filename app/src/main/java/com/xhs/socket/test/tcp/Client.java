package com.xhs.socket.test.tcp;

import android.os.Message;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.ConnectCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.xhs.socket.test.constant.Constant;

import org.greenrobot.eventbus.EventBus;

import java.net.InetSocketAddress;

public class Client {

    private String host;
    private int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;

    }

    public void setup() {
        AsyncServer.getDefault().connectSocket(new InetSocketAddress(host, port), new ConnectCallback() {
            @Override
            public void onConnectCompleted(Exception ex, final AsyncSocket socket) {
                handleConnectCompleted(ex, socket);
            }
        });
    }

    private void handleConnectCompleted(Exception ex, final AsyncSocket socket) {
        if(ex != null) {
            Message message = Message.obtain();
            message.what= Constant.CONNECT_FAILED;
            EventBus.getDefault().post(message);
            setup();
            return;
        }

        Util.writeAll(socket, "Hello Server,I'm the client data".getBytes(), new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                if (ex != null) {
                    Message message = Message.obtain();
                    message.what= Constant.SEND_FAILED;
                    EventBus.getDefault().post(message);
                }else {
                    Message message = Message.obtain();
                    message.what= Constant.SEND_SUCCESS;
                    EventBus.getDefault().post(message);
                }
            }
        });

        socket.setDataCallback(new DataCallback() {
            @Override
            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                if (listener!=null){
                    listener.reciverMessage(bb);
                }
            }
        });

        socket.setClosedCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
                if(ex != null) {
                    Message message = Message.obtain();
                    message.what= Constant.ABORTIVE_DISCONNECT;
                    EventBus.getDefault().post(message);
                }else {
                    Message message = Message.obtain();
                    message.what= Constant.NORMAL_DISCONNECT;
                    EventBus.getDefault().post(message);
                  //  Logger.e("[Client] Successfully closed connection");
                }
            }
        });

        socket.setEndCallback(new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {
              /*  if(ex != null) {
                    Message message = Message.obtain();
                    message.what= Constant.ABORTIVE_DISCONNECT;
                    EventBus.getDefault().post(message);
                }
                Logger.e("[Client] Successfully end connection");*/

            }
        });
    }


    public void setMssageListener(ImMessageReciver listener){
        this.listener=listener;
    }
    public  ImMessageReciver listener;
    public interface ImMessageReciver{
         void  reciverMessage(ByteBufferList bb);
    }
}
