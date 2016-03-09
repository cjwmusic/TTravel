package com.wukong.ttravel.Base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.wukong.ttravel.Utils.MessageUtils;

/**
 * Created by wukong on 2/21/16.
 */
public class BaseActivity extends Activity {

    private ProgressDialog progressDialog; //加载的进度条
    private KProgressHUD hud;
    private SVProgressHUD svProgressHUD;

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

    public void handleBack(View view) {
        onBackPressed();
    }


    //------------hud-------------//

    public synchronized void showHud() {
        showHud(null);
    }

    public synchronized void showHud(String title) {
        showHud(title,null);
    }

    public synchronized void showHud(String title,String subTitle) {

        if (hud == null) {
           hud = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel(title)
                    .setDetailsLabel(subTitle)
                    .setCancellable(true)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();
        }


    }

    public synchronized void dismissHud() {
        if (hud != null && hud.isShowing()) {
            hud.dismiss();
        }
    }

    //------------SVProgressHUD-------------//

    public synchronized void showInfo(String text){
        if (svProgressHUD == null) {
            svProgressHUD = new SVProgressHUD(this);
        }

        svProgressHUD.showInfoWithStatus(text);
    }

    public synchronized void showError(String text){
        if (svProgressHUD == null) {
            svProgressHUD = new SVProgressHUD(this);
        }

        svProgressHUD.showErrorWithStatus(text);
    }

    public synchronized void showSuccess(String text){
        if (svProgressHUD == null) {
            svProgressHUD = new SVProgressHUD(this);
        }

        svProgressHUD.showSuccessWithStatus(text);
    }

    public synchronized void showLoading(String text) {
        if (svProgressHUD == null) {
            svProgressHUD = new SVProgressHUD(this);
        }

        svProgressHUD.showWithStatus(text);
    }

    public synchronized void dismissSvHud(){
        if (svProgressHUD == null) {
            svProgressHUD = new SVProgressHUD(this);
        }

        svProgressHUD.dismiss();
    }
}
