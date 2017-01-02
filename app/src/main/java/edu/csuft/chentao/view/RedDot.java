package edu.csuft.chentao.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import edu.csuft.chentao.R;

/**
 * Created by Chalmers on 2017-01-01 11:16.
 * email:qxinhai@yeah.net
 */

public class RedDot extends View {

    /**
     * 默认半径
     */
    private float DEFAULT_RADIUS = 40;
    /**
     * 默认字体大小
     */
    private float DEFAULT_TEXT_SIZE = 20f;
    /**
     * 默认字体颜色
     */
    private int DEFAULT_TEXT_COLOR = Color.WHITE;
    /**
     * 默认背景颜色
     */
    private int DEFAULT_CIRCLE_COLOR = Color.RED;

    /**
     * 半径
     */
    private int mRadius = (int) DEFAULT_RADIUS;
    /**
     * 字体大小
     */
    private int mTextSize = (int) DEFAULT_TEXT_SIZE;
    /**
     * 画笔，背景和字体
     */
    private Paint mPaintCircle = null;
    private Paint mPaintText = null;
    /**
     * 字体颜色
     */
    private int mTextColor = DEFAULT_TEXT_COLOR;
    /**
     * 背景颜色
     */
    private int mCircleColor = DEFAULT_CIRCLE_COLOR;

    /**
     * 宽
     */
    private int mWidth = 0;
    /**
     * 高
     */
    private int mHeight = 0;

    /**
     * 圆心X坐标和Y坐标
     */
    private int mX = 0;
    private int mY = 0;

    private String text = "0";

    public RedDot(Context context) {
        this(context, null);
    }

    public RedDot(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RedDot(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (attrs != null) {
            TypedArray typedArray =
                    context.obtainStyledAttributes(attrs, R.styleable.RedDot);
            mRadius = (int) typedArray.getDimension(R.styleable.RedDot_rdRadius, DEFAULT_RADIUS);
            mTextColor = typedArray.getColor(R.styleable.RedDot_rdTextColor, DEFAULT_TEXT_COLOR);
            mCircleColor = typedArray.getColor(R.styleable.RedDot_rdCircleColor, DEFAULT_CIRCLE_COLOR);
            text = String.valueOf(typedArray.getInteger(R.styleable.RedDot_rdTextNumber, 0));
            mTextSize = (int) typedArray.getDimension(R.styleable.RedDot_rdTextSize, DEFAULT_TEXT_SIZE);
            typedArray.recycle();
        }

        init();
    }

    private void init() {
        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setColor(mTextColor);
        mPaintText.setTextSize(mTextSize);

        mPaintCircle = new Paint();
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setColor(mCircleColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mX, mY, mRadius, mPaintCircle);
        canvas.drawText(text, mX - mPaintText.getTextSize() / 3, mY + mPaintText.getTextSize() / 2, mPaintText);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY) {
            mWidth = (int) (mPaintText.getTextSize() * 2.5);
        }
        if (MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY) {
            mHeight = (int) (mPaintText.getTextSize() * 2.5);
        }

        mX = mWidth / 2;
        mY = mHeight / 2;

        setMeasuredDimension(mWidth, mHeight);
    }

    public void setRdTextNumber(int rdTextNum) {
        text = String.valueOf(rdTextNum);
        invalidate();
    }
}
