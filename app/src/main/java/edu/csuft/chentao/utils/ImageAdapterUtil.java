package edu.csuft.chentao.utils;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by Chalmers on 2016-12-16 14:19.
 * email:qxinhai@yeah.net
 */

public class ImageAdapterUtil {

    @BindingAdapter({"imageUrl", "placehold"})
    public static void imageBinding(ImageView imageView, String imageUrl, int placehold) {

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

    /**
     * 将byte数组转成图片
     *
     * @param imageView ImageView对象
     * @param buf       图片转成的byte数组
     */
    @BindingAdapter("imageBytes")
    public static void bindImageData(ImageView imageView, byte[] buf) {
//        Bitmap bitmap = getRoundedCornerBitmap(bytes2Bitmap(buf), 1.5f);

        Glide.with(imageView.getContext())
                .load(buf)
                .into(imageView);
    }

    /**
     * 圆形头像
     */
    private static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, w, h);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * byte[] -> bitmap
     */
    private static Bitmap bytes2Bitmap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    private static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}