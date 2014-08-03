package dd.android.joke.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.github.kevinsawicki.wishlist.Toaster;
import com.google.inject.Inject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import dd.android.joke.R;
import dd.android.joke.core.Joke;
import dd.android.joke.core.MyImageLoader;
import pl.droidsonroids.gif.GifImageView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;
import uk.co.senab.photoview.PhotoView;

import static dd.android.joke.core.Constants.Extra.JOKE;


public class ActivePhoto extends ActivityBase {
    @InjectExtra(JOKE)
    protected Joke joke;

    @Inject
    private MyImageLoader avatars;

    final static DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.gravatar_icon) // resource or drawable
            .showImageForEmptyUri(R.drawable.gravatar_icon) // resource or drawable
            .showImageOnFail(R.drawable.gravatar_icon) // resource or drawable
            .resetViewBeforeLoading(false)  // default
            .delayBeforeLoading(0)
            .cacheOnDisk(true) // default
            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
            .bitmapConfig(Bitmap.Config.ARGB_8888) // default
            .displayer(new SimpleBitmapDisplayer()) // default
            .handler(new Handler()) // default
            .build();


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (joke.isGif()) {
            GifImageView giv = new GifImageView(this);
            setContentView(giv);
            avatars.bind_gif(giv, joke);
        } else {
            PhotoView pv = new PhotoView(this);
            setContentView(pv);
            ImageLoader.getInstance().displayImage(joke.getImgurl(), pv, options);
            Toaster.showLong(this, "双手操作可以移动、放大、缩小");
        }
    }
}