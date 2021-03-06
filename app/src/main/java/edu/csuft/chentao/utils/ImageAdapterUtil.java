package edu.csuft.chentao.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

/**
 * Created by Chalmers on 2016-12-16 14:19.
 * email:qxinhai@yeah.net
 */

/**
 * 加载图片的工具类，使用的是DataBinding的图片加载方法
 */
public class ImageAdapterUtil {

    @BindingAdapter({"imageUrl", "placehold"})
    public static void imageBinding(ImageView imageView, String imageUrl, int placehold) {

        /*
        如果该文件夹不存在，则创建
         */
        File directory = new File(Constant.PATH_IMAGE);
        if (!directory.exists()) {
            directory.mkdir();
        }

        //从文件中读取出来
        File file = new File(Constant.PATH_IMAGE, imageUrl);
        if (file.exists()) {
            Glide.with(imageView.getContext())
                    .load(file)
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext())
                    .load(placehold)
                    .into(imageView);
        }
    }

    /**
     * 将byte数组转成图片
     *
     * @param imageView ImageView对象
     * @param buf       图片转成的byte数组
     */
    @BindingAdapter("imageBytes")
    public static void bindImageData(ImageView imageView, byte[] buf) {

        Glide.with(imageView.getContext())
                .load(buf)
                .into(imageView);
    }
}