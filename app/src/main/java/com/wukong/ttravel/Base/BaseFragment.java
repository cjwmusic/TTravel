package com.wukong.ttravel.Base;


import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.wukong.ttravel.Utils.MessageUtils;

/**
 * Created by wukong on 16/2/17.
 */
public class BaseFragment extends Fragment {

    private ProgressDialog progressDialog; //加载的进度条
    private SVProgressHUD svProgressHUD;


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

    //------------SVProgressHUD-------------//

    public synchronized void showInfo(String text){
        if (svProgressHUD == null) {
            svProgressHUD = new SVProgressHUD(getActivity());
        }

        svProgressHUD.showInfoWithStatus(text);
    }

    public synchronized void showError(String text){
        if (svProgressHUD == null) {
            svProgressHUD = new SVProgressHUD(getActivity());
        }

        svProgressHUD.showErrorWithStatus(text);
    }

    public synchronized void showSuccess(String text){
        if (svProgressHUD == null) {
            svProgressHUD = new SVProgressHUD(getActivity());
        }

        svProgressHUD.showSuccessWithStatus(text);
    }

    public synchronized void showLoading(String text) {
        if (svProgressHUD == null) {
            svProgressHUD = new SVProgressHUD(getActivity());
        }

        svProgressHUD.showWithStatus(text);
    }

    public synchronized void dismissSvHud(){
        if (svProgressHUD == null) {
            svProgressHUD = new SVProgressHUD(getActivity());
        }

        svProgressHUD.dismiss();
    }

}
