package com.wukong.ttravel.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;


import com.facebook.common.references.SharedReference;
import com.wukong.ttravel.Base.Router.Router;
import com.wukong.ttravel.Base.TTApplication;
import com.wukong.ttravel.service.login.model.TTUser;

import java.util.List;

/**
 * Created by taber on 15/6/4.
 */
public class Helper {
    private static final Helper _helper = new Helper();

    public static Helper sharedHelper() {
        return _helper;
    }

    public static final int ACTIVITY_RESULT_CODE_BACK = 10;

    private static final int NETWORK_TYPE_WIFI = 1;
    private static final int NETWORK_TYPE_4G = 2;
    private static final int NETWORK_TYPE_3G = 3;
    private static final int NETWORK_TYPE_2G = 4;
    private static final int NETWORK_TYPE_OTHER = 5;
    private static final int NETWORK_TYPE_CELLNETWORK = 6;

    private Context _context;
    private Typeface iconfont;

    public void Helper() {
    }

    public void Helper(Context context) {
        this.setContext(context);
    }

    public void setContext(Context context) {
        this._context = context;
    }

    public String getToken() {
        SharedPreferences user = this._context.getSharedPreferences("user_info", 0);
        return user.getString("TOKEN", "");
    }

    public void setToken(String token) {
        SharedPreferences user = this._context.getSharedPreferences("user_info", 0);
        SharedPreferences.Editor editor = user.edit();
        editor.putString("TOKEN", token);
        editor.commit();
    }

    public String getUserId() {
        SharedPreferences user = this._context.getSharedPreferences("user_info", 0);
        return user.getString("USER_ID", "");
    }

    public String getTxPassword() {
        SharedPreferences user = this._context.getSharedPreferences("user_Info",0);
        return user.getString(Constant.USER_TX_PASSWORD,"");
    }

    public String getPayPassword() {
        SharedPreferences user = this._context.getSharedPreferences("user_Info",0);
        return user.getString(Constant.USER_PAY_PASSWORD,"");
    }

    public TTUser getCurrentUer() {
        TTUser ttUser = new TTUser();
        SharedPreferences user = this._context.getSharedPreferences("user_info", 0);
        ttUser.setUserId(user.getString("USER_ID", ""));
        ttUser.setUserAvatar(user.getString(Constant.USER_AVATAR, ""));
        ttUser.setUserPhoneNumber(user.getString(Constant.USER_MOBILE, ""));
        ttUser.setPassword(user.getString(Constant.USER_PASSWORD, ""));
        ttUser.setGender(user.getInt(Constant.USER_GENDER,0));

        return ttUser;
    }


    public void setUserId(String userId) {
        SharedPreferences user = this._context.getSharedPreferences("user_info", 0);
        SharedPreferences.Editor editor = user.edit();
        editor.putString("USER_ID", userId);
        editor.commit();
    }

    public Boolean hasUserId() {
        return getUserId() != "";
    }

    public Boolean hasToken() {
        return getToken() != "";
    }

    public void setBooleanUserInfo(String key, Boolean value) {
        SharedPreferences user = this._context.getSharedPreferences("user_info", 0);
        SharedPreferences.Editor editor = user.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public Boolean getBooleanUserInfo(String key, Boolean defaultValue) {
        SharedPreferences user = this._context.getSharedPreferences("user_info", 0);
        return user.getBoolean(key, defaultValue);
    }

    public void setIntUserInfo(String key, int value) {
        SharedPreferences user = this._context.getSharedPreferences("user_info", 0);
        SharedPreferences.Editor editor = user.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getIntUserInfo(String key, int defaultValue) {
        SharedPreferences user = this._context.getSharedPreferences("user_info", 0);
        return user.getInt(key, defaultValue);
    }

    public void setLongUserInfo(String key, long value) {
        SharedPreferences user = this._context.getSharedPreferences("user_info", 0);
        SharedPreferences.Editor editor = user.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public long getLongUserInfo(String key, long defaultValue) {
        SharedPreferences user = this._context.getSharedPreferences("user_info", 0);
        return user.getLong(key, defaultValue);
    }


    public void setStringUserInfo(String key, String value) {
        SharedPreferences user = this._context.getSharedPreferences("user_info", 0);
        SharedPreferences.Editor editor = user.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getStringUserInfo(String key) {
        SharedPreferences user = this._context.getSharedPreferences("user_info", 0);
        return user.getString(key, "");
    }

    public void clearAllUserInfo() {
        SharedPreferences user = this._context.getSharedPreferences("user_info", 0);
        SharedPreferences.Editor editor = user.edit();
        editor.clear();
        editor.apply();
    }

    public void removeUserInfo(String key) {
        SharedPreferences user = this._context.getSharedPreferences("user_info", 0);
        SharedPreferences.Editor editor = user.edit();
        editor.remove(key);
        editor.apply();
    }

    public SharedPreferences getCityPref() {
        return this._context.getSharedPreferences("SHZ_CITY_PREF", Activity.MODE_PRIVATE);
    }

    public void clearToken() {
        this.setToken("");
    }

    public void clearUserId() {
        this.setUserId("");
    }

    public Typeface getIconFont() {
        if (iconfont == null) {
            iconfont = Typeface.createFromAsset(_context.getAssets(), "iconfont/iconfont.ttf");
        }

        return iconfont;
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    public static String formatMobileNumber(String contents) {
        int length = contents.length();
        if (length == 4) {
            if (contents.substring(3).equals(new String(" "))) {
                contents = contents.substring(0, 3);
            } else {
                contents = contents.substring(0, 3) + " " + contents.substring(3);
            }
        } else if (length == 9) {
            if (contents.substring(8).equals(new String(" "))) {
                contents = contents.substring(0, 8);
            } else {
                contents = contents.substring(0, 8) + " " + contents.substring(8);
            }
        }
        return contents;
    }

    public String getVersionName() throws Exception {
        //获取packagemanager的实例
        PackageManager packageManager = this._context.getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(this._context.getPackageName(), 0);
        return packInfo.versionName;
    }

    public int getVersionNumber() throws Exception {
        //获取packagemanager的实例
        PackageManager packageManager = this._context.getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(this._context.getPackageName(), 0);
        return packInfo.versionCode;
    }

    public void jumpToLogin(String action, Activity activity) {
        Bundle bundle = new Bundle();
        bundle.putString("action", action);
        Router.sharedRouter().open("signin", bundle);
        if (activity != null) {
            activity.finish();
        }
    }

    public void signOut() {
//        if (AuthService.getInstance().isLogin()) {
//            AuthService.getInstance().logout();
//        }
        //ut user logout
//        UTAnalytics.getInstance().updateUserAccount("", "");

        clearToken();
        clearUserId();
        removeUserInfo(Constant.USER_AVATAR);
        removeUserInfo(Constant.USER_GENDER);
        removeUserInfo(Constant.USER_NICK);
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    public static boolean isLocationEnabled(Context context) {
        try {
            LocationManager locationManager = (LocationManager) context.
                    getSystemService(Context.LOCATION_SERVICE);
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void openLocationSetting(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            // The Android SDK doc says that the location settings activity
            // may not be found. In that case show the general settings.
            // General settings activity
            intent.setAction(Settings.ACTION_SETTINGS);
            try {
                context.startActivity(intent);
            } catch (Exception e) {
            }
        }
    }

    /**
     * 获取网络类型，发送给服务端
     *
     * @return
     */
    public static int getNetWorkType() {
        int networkType = NETWORK_TYPE_OTHER;
        ConnectivityManager manager = (ConnectivityManager) TTApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return NETWORK_TYPE_WIFI;
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String subtypeName = networkInfo.getSubtypeName();
                switch (networkInfo.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        networkType = NETWORK_TYPE_2G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        networkType = NETWORK_TYPE_3G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        networkType = NETWORK_TYPE_4G;
                        break;
                    default:
                        //TD-SCDMA 中国移动 WCDMA 联通 CDMA2000电信 三种3G制式
                        if (subtypeName.equalsIgnoreCase("TD-SCDMA") || subtypeName.equalsIgnoreCase("WCDMA")
                                || subtypeName.equalsIgnoreCase("CDMA2000")) {
                            networkType = NETWORK_TYPE_3G;
                        }
                        break;
                }
            }
        }
        return networkType;
    }

    public static boolean isPackageExist(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageList = packageManager.getInstalledPackages(0);
        for (PackageInfo info : packageList) {
            if (packageName.equals(info.packageName)) {
                return true;
            }
        }
        return false;
    }

    public static String getSignature(Context context, String packageName) {
        if (!TextUtils.isEmpty(packageName)) {
            try {
                PackageManager manager = context.getPackageManager();
                /** 通过包管理器获得指定包名包含签名的包信息 **/
                PackageInfo packageInfo = manager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);

                StringBuilder builder = new StringBuilder();
                for (Signature signature : packageInfo.signatures) {
                    builder.append(signature.toCharsString());
                }

                return builder.toString();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void makeCall(Context context, String telNumber) {
        try {
            if (PackageManager.PERMISSION_GRANTED ==
                    context.checkCallingPermission(Manifest.permission.CALL_PHONE)) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + telNumber));
                context.startActivity(intent);
            } else {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + telNumber));
                context.startActivity(intent);
            }
        } catch (Exception e) {
            MessageUtils.showToast("拨号失败，请检查是否开启拨打电话权限");
            e.printStackTrace();
        }
    }

    public static boolean hasPermission(String permission){
        return PackageManager.PERMISSION_GRANTED ==
                TTApplication.getInstance().checkCallingPermission(permission);
    }

}
