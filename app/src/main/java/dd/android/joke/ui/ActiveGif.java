package dd.android.joke.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import com.ant.liao.GifView;
import dd.android.joke.R;
import dd.android.joke.core.ImageLoader;
import dd.android.joke.core.Joke;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import java.io.*;

import static dd.android.joke.core.Constants.Extra.JOKE;

/**
 * Created with IntelliJ IDEA.
 * User: dd
 * Date: 13-11-18
 * Time: 下午7:29
 * To change this template use File | Settings | File Templates.
 */
public class ActiveGif extends ActivityBase {
    @InjectExtra(JOKE)
    protected Joke joke;

    @InjectView(R.id.gv_show)
    protected GifView gv_show;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_gif_entry);
        ImageLoader il = new ImageLoader(this);
        String path = il.getFileFullPath(joke.getImgurl());
        Uri uri = Uri.fromFile(new File(path));
        gv_show.setImageURI(uri);
        if (joke.isGif())
        {
            recycleBitmap();
            try {
                InputStream is = new FileInputStream(path);
                gv_show.setGifImageType(GifView.GifImageType.COVER);
                gv_show.setGifImage(is);
            } catch (FileNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (OutOfMemoryError e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    private void recycleBitmap() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) gv_show.getDrawable();
        if (bitmapDrawable!= null && bitmapDrawable.getBitmap() != null && bitmapDrawable.getBitmap().isRecycled())
        {
            bitmapDrawable.getBitmap().recycle();
        }
    }

    @Override
    protected void onDestroy() {
        recycleBitmap();
        System.gc();
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }
}