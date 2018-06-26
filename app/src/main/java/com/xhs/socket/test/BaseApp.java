package com.xhs.socket.test;

import android.app.Application;
import android.content.Intent;

import com.orhanobut.logger.Logger;
import com.xhs.socket.test.service.GrayService;

/**
 * Created by 布鲁斯.李 on 2018/6/26.
 * Email:zp18595658325@163.com
 */

public class BaseApp extends Application {

    private static BaseApp mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
        Logger.init("TAG");
        Intent grayIntent = new Intent(getApplicationContext(), GrayService.class);
        startService(grayIntent);
    }


    public static BaseApp getContext(){
        return mContext;
    }
}
