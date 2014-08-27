package dd.android.joke.adapter;

import android.view.LayoutInflater;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import dd.android.joke.R;
import dd.android.joke.models.Tab;

import java.util.List;

/**
 * Created by dd on 14-8-26.
 */
public class AdapterTabs extends SingleTypeAdapter<Tab> {
    public AdapterTabs(final LayoutInflater inflater, final List<Tab> items) {
        this(inflater, items, 0);
    }

    /**
     * Create adapter with alternating row colors
     *
     * @param inflater
     * @param items
     * @param index_selected
     */
    public AdapterTabs(LayoutInflater inflater, final List<Tab> items, final int index_selected) {
        super(inflater, R.layout.tab_text);

        setItems(items);
//        setChecked(index_selected, true);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[] { R.id.title };
    }

    @Override
    protected void update(int position, Tab tab) {
        setText(0, tab.get_title());
    }
}
