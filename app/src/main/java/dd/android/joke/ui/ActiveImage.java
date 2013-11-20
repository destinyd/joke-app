package dd.android.joke.ui;

import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import dd.android.joke.R;
import dd.android.joke.core.Joke;
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

    @InjectView(R.id.wv_show)
    protected WebView wv_large_image;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_web_entry);
        wv_large_image.loadUrl(joke.getImgurl());
    }
}