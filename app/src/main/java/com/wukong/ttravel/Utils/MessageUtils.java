package com.wukong.ttravel.Utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.wukong.ttravel.Base.TTApplication;
import com.wukong.ttravel.R;


/**
 * Created by ywp on 5/8/15.
 */
public class MessageUtils {

    public static final int TOAST_DURATION = 3000;

    private static Toast mToast = null;

    public static void showToast(int resId) {
        showToast(null, TTApplication.getInstance().getString(resId));
    }

    public static void showToast(String tips) {
        showToast(null, (null == tips || TextUtils.isEmpty(tips.trim())) ? "unknow" : tips);
    }

    public static void showToast(int resId, int time) {
        String toastString = TTApplication.getInstance().getString(resId);
        if (mToast == null) {
            mToast = Toast.makeText(TTApplication.getInstance(), "", time);
        }
        mToast.setText(toastString);
        mToast.show();
    }

    public static void showToast(String str, int time) {
//        String toastString = SHZApplication.getInstance().getString(resId);
        if (mToast == null) {
            mToast = Toast.makeText(TTApplication.getInstance(), "", time);
        }
        mToast.setText(str);
        mToast.show();
    }

    public static void showToast(Context context, String toastString) {
        if (mToast == null) {
            mToast = Toast.makeText(TTApplication.getInstance(), "", Toast.LENGTH_LONG);
        }
        mToast.setText(toastString);
        mToast.show();
    }

    public static void showToastCenter(String toastString) {
        if (mToast == null) {
            mToast = Toast.makeText(TTApplication.getInstance(), "", Toast.LENGTH_LONG);
        }
        mToast.setText(toastString);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    public static void showDialog(Context context, String title, String message,
                                  DialogInterface.OnClickListener okListener,
                                  DialogInterface.OnClickListener cancelListener) {
        createDialog(context, title, message, R.string.confirm, okListener, R.string.cancel,
                cancelListener).show();
    }

    public static void showDialog(Context context, String strTitle, String strText,
                                  DialogInterface.OnClickListener listener, boolean showNegative) {
        DialogInterface.OnClickListener onNegativeListener = null;
        if (showNegative) {
            onNegativeListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };
        }
        createDialog(context, strTitle, strText, R.string.confirm, listener, 0, onNegativeListener)
                .show();
    }

    public static AlertDialog createDialog(Context context, String title, String msg,
                                           int positiveBtnTx, DialogInterface.OnClickListener okListener,
                                           int negativeBtnTxt, DialogInterface.OnClickListener cancelListener) {
        AlertDialog.Builder dialog = createHoloBuilder(context);
        if (null != cancelListener) {
            negativeBtnTxt = negativeBtnTxt > 0 ? negativeBtnTxt : R.string.cancel;
            dialog.setNegativeButton(negativeBtnTxt, cancelListener);
        }
        positiveBtnTx = positiveBtnTx > 0 ? positiveBtnTx : R.string.confirm;
        dialog.setPositiveButton(positiveBtnTx, okListener);
        if (!TextUtils.isEmpty(title)) {
            dialog.setTitle(title);
        }
        dialog.setMessage(msg);
        return dialog.create();
    }

    public static ProgressDialog getProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        return progressDialog;
    }

    public static ProgressDialog getProgressDialog(Context context, int stringId) {
        ProgressDialog progressDialog = getProgressDialog(context);
        progressDialog.setMessage(context.getString(stringId));
        return progressDialog;
    }

    public static ProgressDialog getProgressDialog(Context context, String msg) {
        ProgressDialog progressDialog = getProgressDialog(context);
        progressDialog.setMessage(msg);
        return progressDialog;
    }

    public static AlertDialog.Builder createHoloBuilder(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder;
    }

    public static void singleChoiceDialog(Context context, String title, String[] items,
                                          int checkedItem,
                                          final SingleChoiceDialogInterface listener) {
        final Integer[] realTimeCheckedItem = new Integer[]{checkedItem};
        AlertDialog.Builder dialog = createHoloBuilder(context);
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                realTimeCheckedItem[0] = which;
            }
        };
        DialogInterface.OnClickListener buttonOnClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        listener.onPositive(dialog, realTimeCheckedItem[0]);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        listener.onNegative(dialog);
                        break;
                    default:
                        break;
                }
            }
        };

        dialog.setTitle(title);
        dialog.setPositiveButton(R.string.confirm, buttonOnClickListener);
        dialog.setNegativeButton(R.string.cancel, buttonOnClickListener);
        dialog.setSingleChoiceItems(items, checkedItem, onClickListener);
        dialog.setCancelable(false);
        dialog.create().show();
    }

    public interface SingleChoiceDialogInterface {

        public void onPositive(DialogInterface dialog, int checkedItem);

        public void onNegative(DialogInterface dialog);
    }
}