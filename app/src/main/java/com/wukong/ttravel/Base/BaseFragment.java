package com.wukong.ttravel.Base;


import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

import com.wukong.ttravel.Utils.MessageUtils;

/**
 * Created by wukong on 16/2/17.
 */
public class BaseFragment extends Fragment {

    private ProgressDialog progressDialog; //加载的进度条

    public synchronized void showProgressDialog(String msg) {
        showProgressDialog(msg, true);
    }

    public synchronized void showProgressDialog(String msg, boolean cancelable) {
        if (progressDialog == null) {
            progressDialog = MessageUtils.getProgressDialog(getActivity(), msg);
        }
        progressDialog.setCancelable(cancelable);
        progressDialog.setMessage(msg);
        if (!this.isVisible()) {
            progressDialog.show();
        }
    }

    public synchronized void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
