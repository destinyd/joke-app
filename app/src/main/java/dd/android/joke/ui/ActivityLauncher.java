
package dd.android.joke.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import dd.android.joke.R;
import dd.android.joke.core.Constants;
import dd.android.joke.core.PropertiesController;
import roboguice.activity.RoboTabActivity;

import java.io.File;

public class ActivityLauncher extends
        RoboTabActivity {
    static protected ActivityLauncher factory = null;
    TabHost tabHost;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.act_main);
        factory = this;

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCacheExtraOptions(400, 2048) // default = device screen dimensions
                .diskCacheExtraOptions(400, 2048, null)
//                .taskExecutor(...)
//        .taskExecutorForCachedImages(...)
//                .threadPoolSize(3) // default
//                .threadPriority(Thread.NORM_PRIORITY - 1) // default
//                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
//                .denyCacheImageMultipleSizesInMemory()
//                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
//                .memoryCacheSize(2 * 1024 * 1024)
//                .memoryCacheSizePercentage(13) // default
////                .diskCache(new UnlimitedDiscCache(new File(Constants.Setting.SDCARD_PATH + "/cache"))) // default
//                .diskCacheSize(50 * 1024 * 1024)
//                .diskCacheFileCount(100)
//                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
//                .imageDownloader(new BaseImageDownloader(getApplicationContext())) // default
////                .imageDecoder(new BaseImageDecoder()) // default
//                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
//                .writeDebugLogs()

                .build();

        ImageLoader.getInstance().init(config);


        tabHost = getTabHost();
        setTabs();
    }

    private void setTabs() {
        addTab("最新",
//                R.drawable.tab_groups,
                ActivityJokeShort.class);
//        addTab("购物车", R.drawable.tab_search, ActivitySettings.class);
        addTab("有图",
//                R.drawable.tab_contact,
                ActivityJokeImage.class);
//                ActivityJokeShort.class);
        addTab("视频",
//                R.drawable.tab_settings,
                ActivityJokeVideo.class);
//                ActivityJokeShort.class);
        addTab("长篇",
//                R.drawable.tab_home,
                ActivityJokeLong.class);
    }

    private void addTab(String labelId,
                        Class<?> c) {
        Intent intent = new Intent(this, c);
        TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);

        View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_text, getTabWidget(), false);
        TextView title = (TextView) tabIndicator.findViewById(R.id.title);
        title.setText(labelId);
        spec.setIndicator(tabIndicator);
        spec.setContent(intent);
        tabHost.addTab(spec);
    }

    private void exit() {
        System.exit(0);
    }

    @Override
    protected void onDestroy() {
        factory = null;
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public static ActivityLauncher getFactory() {
        if (factory == null)
            new ActivityLauncher();
        return factory;
    }

    public void changeTab(int i) {
        tabHost.setCurrentTab(i);
    }

}
