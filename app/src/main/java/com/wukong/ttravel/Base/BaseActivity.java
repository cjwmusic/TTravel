package com.wukong.ttravel.Base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.wukong.ttravel.Utils.MessageUtils;

/**
 * Created by wukong on 2/21/16.
 */
public class BaseActivity extends Activity {

    private ProgressDialog progressDialog; //加载的进度条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    protected boolean filterException(Exception e) {
        if (e != null) {
            e.printStackTrace();
            toast(e.getMessage());
            return false;
        } else {
            return true;
        }
    }

    protected void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }


    public synchronized void showProgressDialog(String msg) {
        showProgressDialog(msg, true);
    }

    public synchronized void showProgressDialog(String msg, boolean cancelable) {
        if (progressDialog == null) {
            progressDialog = MessageUtils.getProgressDialog(this, msg);
        }
        progressDialog.setCancelable(cancelable);
        progressDialog.setMessage(msg);
        if (!isFinishing()) {
            progressDialog.show();
        }
    }

    public synchronized void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
