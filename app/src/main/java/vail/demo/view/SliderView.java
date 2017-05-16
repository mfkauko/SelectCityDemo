package vail.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import vail.demo.R;
import vail.demo.util.DisplayUtil;


/**
 * Created by VailWei on 2016/3/17.
 */
public class SliderView extends View {
    private OnItemClickListener mOnItemClickListener;

    private int padding = 0;

    String[] alphas = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
            "Y", "Z" };
    int choose = -1;
    Paint paint = new Paint();
    boolean showBkg = false;
    private PopupWindow mPopupWindow;
    private TextView mPopupText;
    private int mCharHeight;

//    private int mSingleAlphaHeight;

    public SliderView(Context context) {
        this(context, null);
    }

    public SliderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SliderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mCharHeight = DisplayUtil.dpToPx(context, 14);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setTextSize(mCharHeight);
        paint.setFakeBoldText(true);
        paint.setAntiAlias(true);

        padding = DisplayUtil.dpToPx(context, 5);
//        Paint.FontMetrics fm = paint.getFontMetrics();
//        mSingleAlphaHeight = (int)(fm.bottom - fm.top) + padding;
    }

    public void setAlphas(String[] alphas) {
        if(alphas == null) {
            return;
        }
        this.alphas = alphas;
//        int size = alphas.length;
//        ViewGroup.LayoutParams lp = getLayoutParams();
//        lp.height = size * mSingleAlphaHeight;
//        setLayoutParams(lp);
    }

    public void setAlphasWithHeader(String[] header) {
        if(header == null) {
            return;
        }
        int size = header.length;
        int newSize = size + alphas.length;
        String[] newAlphas = new String[newSize];
        for(int i=0;i<newSize;i++) {
            if(i < size) {
                newAlphas[i] = header[i];
            }else {
                newAlphas[i] = alphas[i - size];
            }
        }
        this.alphas = newAlphas;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (showBkg) {
            setBackgroundResource(R.drawable.bg_letter_pop);
        } else {
            setBackgroundColor(getResources().getColor(R.color.transparent));
            canvas.drawColor(Color.parseColor("#00000000"));
        }

        int height = getHeight();
        int width = getWidth();
        int singleAlphaHeight = (height - padding) / alphas.length;
        for (int i = 0; i < alphas.length; i++) {
            paint.setColor(Color.parseColor("#76b4ff"));
            if (i == choose) {
                paint.setColor(Color.parseColor("#ffa66b"));
            }
            float xPos = width / 2 - paint.measureText(alphas[i]) / 2;
            float yPos = singleAlphaHeight * i + singleAlphaHeight;
            canvas.drawText(alphas[i], xPos, yPos, paint);
        }

    }

    @Override
    protected Parcelable onSaveInstanceState() {
        dismissPopup();
        return super.onSaveInstanceState();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final int c = (int) (y * alphas.length / getHeight());

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                showBkg = true;
                if (oldChoose != c) {
                    if (c >= 0 && c < alphas.length) {
                        performItemClicked(c);
                        choose = c;
                        invalidate();
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (oldChoose != c) {
                    if (c >= 0 && c < alphas.length) {
                        performItemClicked(c);
                        choose = c;
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                showBkg = false;
                choose = -1;
                dismissPopup();
                invalidate();
                break;
        }
        return true;
    }

    private void showPopup(String alpha) {
        if (mPopupWindow == null) {
            mPopupText = new TextView(getContext());
            mPopupText.setBackgroundResource(R.drawable.bg_letter_pop);
            mPopupText.setTextColor(Color.parseColor("#84acdd"));
            mPopupText.setTextSize(50);
            mPopupText.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            mPopupWindow = new PopupWindow(mPopupText,
                    WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setWidth(DisplayUtil.dpToPx(getContext(), 90));
            mPopupWindow.setHeight(DisplayUtil.dpToPx(getContext(), 90));
        }

        mPopupText.setText(alpha);
        if (mPopupWindow.isShowing()) {
            mPopupWindow.update();
        } else {
            mPopupWindow.showAtLocation(getRootView(),
                    Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        }
    }

    private void dismissPopup() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    private void performItemClicked(int item) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(alphas[item]);
            showPopup(alphas[item]);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String s);
    }
}
