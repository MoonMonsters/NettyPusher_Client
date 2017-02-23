package edu.csuft.chentao.view;

import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import edu.csuft.chentao.BR;
import edu.csuft.chentao.R;
import edu.csuft.chentao.databinding.DialogInvitePersonBinding;
import edu.csuft.chentao.utils.Constant;

/**
 * Created by Chalmers on 2017-02-12 23:22.
 * email:qxinhai@yeah.net
 */

/**
 * 填写id的对话框
 */
public class InvitePersonDialog {
    private AlertDialog mDialog;

    public InvitePersonDialog(final Context context, final IDialogClickListener clickListener, int type) {

        final DialogInvitePersonBinding binding;

        String title = null;
        String hintData = null;
        if (type == Constant.TYPE_INVITE_TITLE_AND_HINT_USER) {
            hintData = "输入用户id";
            title = "邀请用户";
        } else if (type == Constant.TYPE_INVITE_TITLE_AND_HINT_GROUP) {
            hintData = "输入群id";
            title = "加入群";
        }

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_invite_person, null, false);
        binding = DataBindingUtil.bind(view);
        binding.setVariable(BR.hintData, hintData);
        binding.setVariable(BR.type, type);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        mDialog = builder.setView(view)
                .setTitle(title)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String content = binding.acetInvitePersonInput.getText().toString();
                        if (!TextUtils.isEmpty(content) && TextUtils.isDigitsOnly(content)) {
                            int userId = Integer.parseInt(content);
                            clickListener.onClickToInvitePerson(userId);

                            dismiss();
                        } else {
                            Toast.makeText(context, "输入错误", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                })
                .create();
    }

    /**
     * 显示对话框
     */
    public void show() {
        mDialog.show();
    }

    /**
     * 隐藏对话框
     */
    public void dismiss() {
        mDialog.dismiss();
    }

    /**
     * 接口，点击更新按钮时，传递消息
     */
    public interface IDialogClickListener {
        void onClickToInvitePerson(int id);
    }
}
