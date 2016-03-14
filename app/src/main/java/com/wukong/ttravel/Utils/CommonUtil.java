/**
 * Project Name:TongCheng File Name:CommonUtil.java Package Name:com.taobao.tongcheng.util Date:2013-3-21下午8:24:30
 * Copyright (c) 2013, weiping.yu@alibaba-inc.com All Rights Reserved.
 */

package com.wukong.ttravel.Utils;

import android.content.Context;
import android.os.PowerManager;

/**
 *
 */
public class CommonUtil {

    private static PowerManager.WakeLock wakeLock;

    public static PowerManager.WakeLock acquireLock(Context context) {
        if (wakeLock == null || !wakeLock.isHeld()) {
            PowerManager powerManager = (PowerManager) context
                    .getSystemService(Context.POWER_SERVICE);
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    "TT");
            wakeLock.setReferenceCounted(true);
            wakeLock.acquire();
        }
        return wakeLock;
    }

    public static void releaseLock(Context context) {
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
        }
    }

}
