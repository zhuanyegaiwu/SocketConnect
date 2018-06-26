package com.xhs.socket.test.utils;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;

import com.xhs.socket.test.BaseApp;


/**
 * 作者: 布鲁斯.李 on 2018/5/4 10 18
 * 邮箱: lzp_lizhanpeng@163.com
 */

public class ScreenUtils {

    public static void brightScreen(){
        KeyguardManager km = (KeyguardManager) BaseApp.getContext().getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unlock");
        kl.disableKeyguard();
        PowerManager pm = (PowerManager) BaseApp.getContext().getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP|PowerManager.FULL_WAKE_LOCK, "bright");
        wl.acquire();
        wl.release();
    }

    /**
     * 唤醒手机屏幕并解锁
     */
    public static void wakeUpAndUnlock() {
        // 获取电源管理器对象
        PowerManager pm = (PowerManager) BaseApp.getContext()
                .getSystemService(Context.POWER_SERVICE);
        boolean screenOn = pm.isScreenOn();
        if (!screenOn) {
            // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
            PowerManager.WakeLock wl = pm.newWakeLock(
                    PowerManager.ACQUIRE_CAUSES_WAKEUP |
                            PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
            wl.acquire(10000); // 点亮屏幕
            wl.release(); // 释放
        }
      // 屏幕解锁
        KeyguardManager keyguardManager = (KeyguardManager) BaseApp.getContext()
                .getSystemService(BaseApp.getContext().KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("unLock");
        // 屏幕锁定
        keyguardLock.reenableKeyguard();
        keyguardLock.disableKeyguard(); // 解锁
    }

}
