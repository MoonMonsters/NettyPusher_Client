package edu.csuft.chentao.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

/**
 * Created by Chalmers on 2016-12-16 14:19.
 * email:qxinhai@yeah.net
 */

public class ImageAdapterUtil {

    @BindingAdapter({"imageUrl","placehold"})
    public static void imageBinding(ImageView imageView, String imageUrl,int placehold) {

        /*
        如果该文件夹不存在，则创建
         */
        File directory = new File(Constant.PATH);
        if (!directory.exists()) {
            directory.mkdir();
        }

        //从文件中读取出来
        File file = new File(Constant.PATH, imageUrl);
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
}