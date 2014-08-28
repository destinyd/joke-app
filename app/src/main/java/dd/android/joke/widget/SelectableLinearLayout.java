package dd.android.joke.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import dd.android.joke.R;

/**
 * Created by dd on 14-8-21.
 */
public class SelectableLinearLayout extends LinearLayout {
    private static final String TAG = "SelectableLinearLayout";
    boolean selected = false;
    int res_selected = R.drawable.tab_selected;
    int res_unselected = R.drawable.tab_indicator;

    public SelectableLinearLayout(Context context) {
        this(context, null);
    }

    public SelectableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public SelectableLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void select(){
        selected = true;
        refresh_background();
    }

    public void unselect() {
        selected = false;
        refresh_background();
    }

    public int get_res_selected() {
        return res_selected;
    }

    public void set_res_selected(int res_selected) {
        this.res_selected = res_selected;
    }

    public int get_res_unselected() {
        return res_unselected;
    }

    public void set_res_unselected(int res_unselected) {
        this.res_unselected = res_unselected;
    }

    void refresh_background() {
        setBackgroundDrawable(getContext().getResources().getDrawable(selected ? res_selected : res_unselected));
        refreshDrawableState();
    }

    public boolean isSelected() {
        return selected;
    }
}