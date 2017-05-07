package edu.csuft.chentao.ui.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.ViewDebug;

/**
 * 能够实现跑马灯效果的TextView
 */
public class MarqueeText extends AppCompatTextView {
    public MarqueeText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setMovementMethod(new ScrollingMovementMethod());
        this.setMaxLines(1);
    }

    public MarqueeText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeText(Context context) {
        this(context, null);
    }

    @Override
    @ViewDebug.ExportedProperty(category = "focus")
    public boolean isFocused() {
        return true;
    }
}