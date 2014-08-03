package dd.android.joke.ui;

import android.graphics.Bitmap;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import dd.android.common.PrettyDateFormat;
import dd.android.joke.R;
import dd.android.joke.core.MyImageLoader;
import dd.android.joke.core.Joke;
import pl.droidsonroids.gif.GifImageView;

import java.util.List;

public class AdapterJokes extends AdapterAlternatingColorList<Joke> {
    private final MyImageLoader avatars;
    final static DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.gravatar_icon) // resource or drawable
            .showImageForEmptyUri(R.drawable.gravatar_icon) // resource or drawable
            .showImageOnFail(R.drawable.gravatar_icon) // resource or drawable
            .resetViewBeforeLoading(false)  // default
            .delayBeforeLoading(200)
            .cacheOnDisk(true)
            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
            .bitmapConfig(Bitmap.Config.ARGB_8888) // default
            .displayer(new SimpleBitmapDisplayer()) // default
            .handler(new Handler()) // default
            .build();

    public AdapterJokes(LayoutInflater inflater, List<Joke> items, MyImageLoader avatars) {
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
            ImageLoader.getInstance().displayImage(joke.getImgurl(), imageView(3), options);
            setGone(3, false);
//            ImageLoader.getInstance().loadImageSync(joke.getImgurl(), imageView(3), options);
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