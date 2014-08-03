package dd.android.joke.ui;

import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import dd.android.common.PrettyDateFormat;
import dd.android.joke.R;
import dd.android.joke.core.ImageLoader;
import dd.android.joke.core.Joke;
import pl.droidsonroids.gif.GifImageView;

import java.util.List;

public class AdapterJokes extends AdapterAlternatingColorList<Joke> {
    private final ImageLoader avatars;

    public AdapterJokes(LayoutInflater inflater, List<Joke> items, ImageLoader avatars) {
        super(R.layout.item_joke,inflater,items);
        this.avatars = avatars;
    }

    /**
     * @param inflater
     * @param items
     */
    public AdapterJokes(LayoutInflater inflater, List<Joke> items) {
        this(inflater, items, null);

    }
    @Override
    public long getItemId(final int position) {
        final String id = getItem(position)._id;
        return !TextUtils.isEmpty(id) ? id.hashCode() : super
                .getItemId(position);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[] { R.id.tv_name, R.id.tv_published_at, R.id.tv_text, R.id.iv_image, R.id.iv_gif, R.id.iv_play };
    }

    @Override
    protected void update(int position, Joke joke) {
        super.update(position, joke);

        setText(0, joke.getName());
        setText(1, PrettyDateFormat.defaultFormat(joke.getCreated_at()));
        setText(2, Html.fromHtml(joke.getText()));

        setGone(3, true);
        setGone(4, true);
        setGone(5, true);

        if(joke.isVideo() || joke.isImage()){
//            if(joke.isGif())
//                avatars.bind_gif((GifImageView) imageView(3), joke);
//            else
                avatars.bind(imageView(3), joke);
        }

        if(joke.isVideo())
        {
            setGone(5, false);
        }
        else{
            setGone(5, true);
            if(joke.isGif())
                setGone(4, false);
        }
    }


}