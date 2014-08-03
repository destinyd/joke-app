package dd.android.joke.ui;

import android.os.Bundle;
import com.google.inject.Inject;
import dd.android.joke.R;
import dd.android.joke.core.ImageLoader;
import dd.android.joke.core.Joke;
import pl.droidsonroids.gif.GifImageView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import static dd.android.joke.core.Constants.Extra.JOKE;

/**
 * Created with IntelliJ IDEA.
 * User: dd
 * Date: 13-11-18
 * Time: 下午7:29
 * To change this template use File | Settings | File Templates.
 */
public class ActiveImage extends ActivityBase {
    @InjectExtra(JOKE)
    protected Joke joke;


    @InjectView(R.id.image)
    GifImageView image;

    @Inject
    private ImageLoader avatars;
//    @InjectView(R.id.wv_show)
//    protected WebView wv_large_image;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.image);
        if(joke.isGif())
//        image.setImageURI(Uri.parse(joke.getImgurl()));
            avatars.bind_gif(image, joke);
        else
            avatars.bind(image, joke);
//        image.showAnimation();
//        setContentView(R.layout.dialog_web_entry);
//        wv_large_image.loadUrl(joke.getImgurl());
    }
}