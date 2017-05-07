package edu.csuft.chentao.ui.view;

/**
 * Created by Chalmers on 2017-05-06 18:42.
 * email:qxinhai@yeah.net
 */

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import edu.csuft.chentao.R;
import edu.csuft.chentao.controller.presenter.ActivityEditorInfoPresenter;
import edu.csuft.chentao.databinding.DialogChangePasswordBinding;
import edu.csuft.chentao.utils.LoggerUtil;
import edu.csuft.chentao.utils.SharedPrefUserInfoUtil;

/**
 * 弹出修改密码的对话框
 */
public class ChangePasswordDialog {

    private AlertDialog mDialog;
    private final String TITLE = "修改密码";
    private Context mContext;
    private DialogChangePasswordBinding mBinding;
    private ActivityEditorInfoPresenter mPresenter;

    public ChangePasswordDialog(Context context, ActivityEditorInfoPresenter presenter) {
        this.mContext = context;
        this.mPresenter = presenter;
    }

    /**
     * 显示对话框
     */
    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_change_password, null);
        mBinding = DataBindingUtil.bind(view);
        mDialog = builder.setView(view)
                .setTitle(TITLE)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String oldPassword = mBinding.etChangePasswordOld.getEditText().getText().toString();
                        String newPassword = mBinding.etChangePasswordNew.getEditText().getText().toString();
                        String newPassword2 = mBinding.etChangePasswordNew2.getEditText().getText().toString();

                        if (!oldPassword.equals(SharedPrefUserInfoUtil.getPassword())) {

                            LoggerUtil.showToast(mContext, "密码输入错误");

                            return;
                        }

                        if (newPassword.length() < 6 || newPassword2.length() > 18 || TextUtils.isDigitsOnly(newPassword)
                                || !newPassword.equals(newPassword2)) {

                            LoggerUtil.showToast(mContext, "新密码设置失败，位数为6位到18位");

                            return;
                        }

                        mPresenter.doClickToChangePassword(oldPassword, newPassword, newPassword2);
                        dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .create();  //创建
        mDialog.show(); //显示
    }

    /**
     * 隐藏对话框
     */
    private void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    /**
     * 修改密码对话框的Presenter
     */
    public class ItemChangePasswordPresenter {
        /**
         * 原来密码输入框的输入监听事件
         */
        public void onTextChangedForOldPassword(CharSequence s, int start, int before, int count) {
            LoggerUtil.logger("TAG", "ChangePasswordDialog--->" + s);

            if (!s.equals(SharedPrefUserInfoUtil.getPassword())) {
                mBinding.etChangePasswordOld.setError("密码错误");
                mBinding.etChangePasswordOld.setEnabled(true);
            } else {
                mBinding.etChangePasswordOld.setError(null);
                mBinding.etChangePasswordOld.setEnabled(false);
            }
        }

        /**
         * 新密码的监听事件
         */
        public void onTextChangedForNewPassword(CharSequence s, int start, int before, int count) {
            if (s.length() < 6 || s.length() > 16) {
                mBinding.etChangePasswordNew.setError("密码要求6位到16位");
                mBinding.etChangePasswordNew.setEnabled(true);
            } else if (TextUtils.isDigitsOnly(s)) {
                mBinding.etChangePasswordNew.setError("密码不能为纯数字");
                mBinding.etChangePasswordNew.setEnabled(true);
            } else if (!s.equals(mBinding.etChangePasswordNew2.getEditText().getText().toString())) {
                mBinding.etChangePasswordNew.setError("密码不匹配");
                mBinding.etChangePasswordNew.setEnabled(true);
            } else if (s.equals(mBinding.etChangePasswordNew2.getEditText().getText().toString())) {
                hideNewPasswordHint();
                hideNewPassword2Hint();
            } else {
                hideNewPasswordHint();
            }
        }

        /**
         * 旧密码的监听事件
         */
        public void onTextChangedForNewPassword2(CharSequence s, int start, int before, int count) {
            if (s.length() < 6 || s.length() > 16) {
                mBinding.etChangePasswordNew2.setError("密码要求6位到16位");
                mBinding.etChangePasswordNew2.getEditText().setError("...密码要求6位到16位");
                mBinding.etChangePasswordNew2.setEnabled(true);
            } else if (TextUtils.isDigitsOnly(s)) {
                mBinding.etChangePasswordNew2.setError("密码不能为纯数字");
                mBinding.etChangePasswordNew2.setEnabled(true);
            } else if (!s.equals(mBinding.etChangePasswordNew.getEditText().getText().toString())) {
                mBinding.etChangePasswordNew2.setError("");
                mBinding.etChangePasswordNew2.setErrorEnabled(true);
            } else if (s.equals(mBinding.etChangePasswordNew.getEditText().getText().toString())) {
                hideNewPassword2Hint();
                hideNewPasswordHint();
            } else {
                hideNewPassword2Hint();
            }
        }

        /**
         * 隐藏password1的错误提示信息
         */
        private void hideNewPasswordHint() {
            mBinding.etChangePasswordNew.setError(null);
            mBinding.etChangePasswordNew.setEnabled(false);
        }

        /**
         * 隐藏password2的错误提示信息
         */
        private void hideNewPassword2Hint() {
            mBinding.etChangePasswordNew2.setError(null);
            mBinding.etChangePasswordNew2.setEnabled(false);
        }
    }

    /**
     * 接口，调用后，发送修改密码信息
     */
    public interface IOnClickToChangePassword {
        void doClickToChangePassword(String oldPassword, String newPassword, String newPassword2);
    }
}
