package com.xhs.socket.test.constant;

/**
 * Created by 布鲁斯.李 on 2018/6/26.
 * Email:zp18595658325@163.com
 */

public class Constant {
    //socket ip
    public static final String IP="192.168.1.104";
    //socket port
    public static final int PORT=60000;
    //连接失败
    public static final int CONNECT_FAILED=0x00000001;
    //发送失败
    public static final int SEND_FAILED =0x00000002 ;
    //异常断开
    public static final int ABORTIVE_DISCONNECT = 0x00000003;
    //收到消息
    public static final int MESSAGE_RECEIVED = 0x00000004;
    //发送成功
    public static final int SEND_SUCCESS = 0x00000005;
    //正常断开
    public static final int NORMAL_DISCONNECT =0x00000006 ;
}
