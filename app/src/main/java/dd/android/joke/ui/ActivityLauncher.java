
package dd.android.joke.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import cn.bidaround.youtui_template.YtTemplate;
import com.five.adwoad.AdwoAdView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import dd.android.joke.R;

public class ActivityLauncher extends
        Activity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.act_launcher);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //广告满横屏
        AdwoAdView.setBannerMatchScreenWidth(true);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCacheExtraOptions(400, 2048) // default = device screen dimensions
                .diskCacheExtraOptions(400, 2048, null)
                .threadPoolSize(5) // default
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .diskCacheSize(50 * 1024 * 1024)
                .build();

        ImageLoader.getInstance().init(config);

        startActivity(new Intent(this, ActivityDashboard.class));
        finish();
    }
}
