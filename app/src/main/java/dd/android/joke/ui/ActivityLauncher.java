
package dd.android.joke.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import cn.bidaround.youtui_template.YtTemplate;
import com.five.adwoad.AdwoAdView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import dd.android.joke.R;
import dd.android.joke.activity.ActivityDashboard;

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

        startActivity(new Intent(this, ActivityDashboard.class));
        finish();
    }
}
