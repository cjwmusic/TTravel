package com.wukong.ttravel.Base.request;

import android.content.res.Resources;
import android.text.TextUtils;

//import com.meidalife.shz.R;
//import com.meidalife.shz.SHZApplication;

import com.wukong.ttravel.Base.TTApplication;
import com.wukong.ttravel.R;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP 请求定义类
 */
public class HttpError extends RuntimeException {
    /**
     * 未知错误类型*
     */
    public static final int ERR_CODE_UNKNOWN = -1;
    /**
     * 未登录，需要弹出登录界面*
     */
    public static final int ERR_CODE_NOT_LOGIN = 206;
    /**
     * 账户被冻结*
     */
    public static final int ERR_CODE_ACCOUNT_FREEZE = 207;
    /**
     * 网络错误*
     */
    public static final int ERR_CODE_NETWORK_CODE = 100001;
    /**
     * 服务器异常*
     */
    public static final int ERR_CODE_SERVER_ERROR = 100002;
    /**
     * 连接超时
     */
    public static final int ERR_CODE_CONNECT_TIMEOUT = 100003;
    /**
     * SOCKET 超时
     */
    public static final int ERR_CODE_SOCKET_TIMEOUT = 100004;
    /**
     * 数据解析异常错误
     */
    public static final int ERR_CODE_PARSE_DATA_ERROR = 100010;

    public static Map<Integer, Integer> HTTP_ERROR_MAP = new HashMap<>();

    private int code = ERR_CODE_UNKNOWN;

    static {
        HTTP_ERROR_MAP.put(ERR_CODE_SERVER_ERROR, R.string.error_server_500);
        HTTP_ERROR_MAP.put(ERR_CODE_CONNECT_TIMEOUT, R.string.error_connect_timeout);
        HTTP_ERROR_MAP.put(ERR_CODE_SOCKET_TIMEOUT, R.string.error_socket_timeout);
        HTTP_ERROR_MAP.put(ERR_CODE_NETWORK_CODE, R.string.error_network_error);
    }

    public HttpError(int code) {
        this.code = code;
    }

    public HttpError(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        String errorString;
        switch (code) {
            case ERR_CODE_NETWORK_CODE: {
                errorString = TTApplication.getInstance().getResources().getString(R.string.error_network_error);
                break;
            }
            case ERR_CODE_SERVER_ERROR: {
                errorString = getMessage() + ":" + String.format(TTApplication.getInstance().
                        getResources().getString(R.string.error_code), code);
                break;
            }
            default: {
                errorString = getMessage() + ":" + String.format(TTApplication.getInstance().
                        getResources().getString(R.string.error_code), code);
                break;
            }
        }
        return errorString;
    }

    @Override
    public String getMessage() {
        Integer errResourceId = HTTP_ERROR_MAP.get(code);
        String errorMessage = "";
        try {
            if (null != errResourceId) {
                errorMessage = TTApplication.getInstance().getResources().getString(errResourceId);
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(errorMessage) && !TextUtils.isEmpty(super.getMessage())) {
            return super.getMessage();
        }

        return errorMessage;
    }
}
