package edu.csuft.chentao.ui.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by csuft.chentao on 2017/3/25.
 */

/**
 * 自定义对话框
 */
public class CustomerAlertDialog {
    private AlertDialog mDialog;
    /**
     * 标题
     */
    private String mTitle;
    /**
     * 显示内容
     */
    private String mContent;
    /**
     * 右边按钮显示文字
     */
    private String mPositiveButtonText;
    /**
     * 左边按钮显示文字
     */
    private String mNegativeButtonText;
    /**
     * 按钮点击事件
     */
    private IAlertDialogClickListener mDialogClickListener;

    private Context mContext;

    public CustomerAlertDialog(Context context) {
        this.mContext = context;
    }

    public CustomerAlertDialog(Context context, IAlertDialogClickListener dialogClickListener,
                               String title, String content,
                               String positiveButtonText, String negativeButtonText) {
        this.mContext = context;
        this.mTitle = title;
        this.mContent = content;
        this.mDialogClickListener = dialogClickListener;
        this.mPositiveButtonText = positiveButtonText;
        this.mNegativeButtonText = negativeButtonText;
    }

    /**
     * 同时设置标题和内容
     *
     * @param title   标题
     * @param content 显示内容
     */
    public void setTitleAndContent(String title, String content) {
        this.mContent = content;
        this.mTitle = title;
    }

    /**
     * 设置点击事件对象
     */
    public void setDialogClickListener(IAlertDialogClickListener dialogClickListener) {
        this.mDialogClickListener = dialogClickListener;
    }

    /**
     * 设置右边（确定）按钮显示文字
     *
     * @param positiveButtonText 需要显示的文字
     */
    public void setPositiveButtonText(String positiveButtonText) {
        this.mPositiveButtonText = positiveButtonText;
    }

    /**
     * 设置左边（取消）按钮显示文字
     *
     * @param negativeButtonText 需要显示的文字
     */
    public void setNegativeButtonText(String negativeButtonText) {
        this.mNegativeButtonText = negativeButtonText;
    }

    /**
     * 显示对话框
     */
    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        mDialog = builder.setTitle(mTitle)
                .setMessage(mContent)
                .setPositiveButton(mPositiveButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDialogClickListener.doClickAlertDialogToOk();
                        dismiss();
                    }
                })
                .setNegativeButton(mNegativeButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .create();
        mDialog.show();
    }

    /**
     * 隐藏对话框
     */
    public void dismiss() {
        mDialog.dismiss();
    }

    /**
     * AlertDialog的事件接口
     */
    public interface IAlertDialogClickListener {
        /**
         * 点击ok按钮
         */
        void doClickAlertDialogToOk();
    }
}
