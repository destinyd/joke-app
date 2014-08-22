
package dd.android.joke.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import cn.bidaround.youtui_template.YtTemplate;
import com.five.adwoad.AdwoAdView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import dd.android.joke.R;
import roboguice.activity.RoboTabActivity;

public class ActivityDashboard extends
        RoboTabActivity {
    static protected ActivityDashboard factory = null;
    TabHost tabHost;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.act_main);
        factory = this;
        YtTemplate.init(this);/*初始化友推*/
        tabHost = getTabHost();
        setTabs();
        setSliding();
    }

    private void setSliding() {
        // configure the SlidingMenu
//        SlidingMenu menu = new SlidingMenu(this);
//        menu.setMode(SlidingMenu.LEFT);
//        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
////        menu.setShadowWidthRes(R.dimen.shadow_width);
////        menu.setShadowDrawable(R.drawable.shadow);
////        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
////        menu.setFadeDegree(0.35f);
//        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
//        menu.setMenu(R.layout.menu);
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

    public static ActivityDashboard getFactory() {
        if (factory == null)
            new ActivityDashboard();
        return factory;
    }

    public void changeTab(int i) {
        tabHost.setCurrentTab(i);
    }

}
