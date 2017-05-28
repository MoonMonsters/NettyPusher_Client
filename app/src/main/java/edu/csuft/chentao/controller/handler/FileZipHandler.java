package edu.csuft.chentao.controller.handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import edu.csuft.chentao.pojo.req.FileZip;
import edu.csuft.chentao.utils.Constant;
import edu.csuft.chentao.utils.OperationUtil;

/**
 * Created by Chalmers on 2017-05-01 15:27.
 * email:qxinhai@yeah.net
 */

/**
 * 文件处理类，包括获取文件数据，以及下载文件
 */
public class FileZipHandler implements Handler {

    @Override
    public void handle(Object object) {
        FileZip fileZip = (FileZip) object;

        //如果文件内容为空，即只是显示数据
        if (fileZip.getZip() == null) {
            OperationUtil.sendEBToObjectPresenter(Constant.TAG_FILE_PRESENTER, fileZip);
        } else {  //下载文件内容
            byte[] buf = fileZip.getZip();
            String fileName = fileZip.getFileName();
            //构建目录路径，上层目录为群id
            String path = Constant.PATH_FILE + File.separator + fileZip.getGroupId();
            File file = new File(path, fileName);
            int index = 1;
            while (file.exists()) { //如果文件名重复，则添加后缀
                file = new File(path, fileName.substring(0, fileName.indexOf(".")) + "_repeat_" + index + fileName.substring(fileName.indexOf(".")));
                index++;
            }
            OperationUtil.createFile(path, file.getName());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                //写入
                fos.write(buf);

                fileZip.setZip(null);
                //下载完成，刷新界面
                OperationUtil.sendEBToObjectPresenter(Constant.TAG_FILE_PRESENTER_REFRESH, fileZip);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}