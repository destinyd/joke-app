package dd.android.joke.utils;

import android.graphics.Typeface;
import dd.android.joke.JokeApplication;

/**
 * Created by fushang318 on 2014/8/20.
 */
public class UiFont {
    final static public Typeface FONTAWESOME_FONT = Typeface.createFromAsset(
            JokeApplication.get_context().getAssets(),
            "fonts/fontawesome-webfont.ttf");
}
