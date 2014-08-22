package dd.android.joke;

import android.app.Application;
import android.content.Context;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by dd on 14-8-22.
 */
public class JokeApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCacheExtraOptions(400, 2048) // default = device screen dimensions
                .diskCacheExtraOptions(400, 2048, null)
                .threadPoolSize(5) // default
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .diskCacheSize(50 * 1024 * 1024)
                .build();
        ImageLoader.getInstance().init(config);

        context = this.getApplicationContext();
    }

    public static Context get_context(){
        return context;
    }
}
