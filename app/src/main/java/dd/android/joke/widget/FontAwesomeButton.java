package dd.android.joke.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;
import dd.android.joke.utils.UiFont;

public class FontAwesomeButton extends Button {

    public FontAwesomeButton(Context context) {
        super(context);
        init();
    }

    public FontAwesomeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FontAwesomeButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            setTypeface(UiFont.FONTAWESOME_FONT);
        }
        setGravity(Gravity.CENTER);
    }
}