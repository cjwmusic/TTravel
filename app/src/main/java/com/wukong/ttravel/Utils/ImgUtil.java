package com.wukong.ttravel.Utils;

import android.net.Uri;

import com.wukong.ttravel.Base.Constant;

/**
 * Created by wukong on 2/21/16.
 */
public class ImgUtil {

    public static Uri getCDNUrlWithPathStr(String path) {
        return Uri.parse(Constant.CDN_BASE_URL + path);
    }

}
