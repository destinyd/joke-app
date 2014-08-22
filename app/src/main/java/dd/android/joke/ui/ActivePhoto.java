package dd.android.joke.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (joke.isGif()) {
            final GifImageView giv = new GifImageView(this);
            setContentView(giv);

            ImageLoader.getInstance().loadImage(joke.getImgurl(), new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    giv.setImageDrawable(new BitmapDrawable(loadedImage));
                    avatars.bind_gif(giv, joke);
                }
            });
        } else {
            final PhotoView pv = new PhotoView(this);
            setContentView(pv);
            ImageLoader.getInstance().loadImage(joke.getImgurl(), new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    pv.setImageDrawable(new BitmapDrawable(loadedImage));
                }
            });
            Toaster.showLong(this, "双手操作可以移动、放大、缩小");
        }
    }
}