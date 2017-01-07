package edu.csuft.chentao.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;

import edu.csuft.chentao.R;
import edu.csuft.chentao.databinding.DialogUpdateInfoBinding;

/**
 * Created by Chalmers on 2017-01-06 19:22.
 * email:qxinhai@yeah.net
 */

/**
 * 更新消息的弹出的对话框
 */
public class UpdateInfoDialog {

    private Dialog mDialog;
    private Context mContext;
    private DialogUpdateInfoBinding mBinding;
    private String mHint;
    private IDialogClickListener mListener;

    public UpdateInfoDialog(Context context, IDialogClickListener listener, String hint) {
        this.mContext = context;
        mDialog = new Dialog(context);
        this.mListener = listener;
        this.mHint = hint;
    }

    public void show() {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.dialog_update_info, null);
        mBinding = DataBindingUtil.bind(view);
        mDialog.setContentView(view);
        mDialog.setTitle("更新信息");
        mBinding.etDialogUpdateInfoInput.setHint(mHint);
        mBinding.btnDialogUpdateInfoOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = mBinding.etDialogUpdateInfoInput.getText().toString();
                mListener.onClickToUpdateInfo(info);
                dismiss();
            }
        });

        mBinding.btnDialogUpdateInfoCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mDialog.show();
    }

    private void dismiss() {
        mDialog.dismiss();
    }

    /**
     * 接口，点击更新按钮时，传递消息
     */
    public interface IDialogClickListener {
        void onClickToUpdateInfo(String updateInfo);
    }

}
