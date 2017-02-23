package edu.csuft.chentao.view;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import edu.csuft.chentao.BR;
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

    private AlertDialog mDialog;
    private Context mContext;
    private DialogUpdateInfoBinding mBinding;
    private String mHint;
    private IDialogClickListener mListener;

    public UpdateInfoDialog(Context context, IDialogClickListener listener, String hint) {
        this.mContext = context;
        this.mListener = listener;
        this.mHint = hint;
    }

    public void show() {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.dialog_update_info, null);
        mBinding = DataBindingUtil.bind(view);
        mBinding.setVariable(BR.hintData, mHint);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        mDialog = builder.setView(view)
                .setTitle("更新信息")
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String info = mBinding.etDialogUpdateInfoInput.getText().toString();
                        mListener.onClickToUpdateInfo(info);
                        dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .create();
//        mDialog.setContentView(view);
//        mDialog.setTitle("更新信息");
//        mBinding.etDialogUpdateInfoInput.setHint(mHint);
//        mBinding.btnDialogUpdateInfoOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String info = mBinding.etDialogUpdateInfoInput.getText().toString();
//                mListener.onClickToUpdateInfo(info);
//                dismiss();
//            }
//        });
//
//        mBinding.btnDialogUpdateInfoCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });

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
