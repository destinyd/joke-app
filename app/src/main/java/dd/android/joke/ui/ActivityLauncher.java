
package dd.android.joke.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import dd.android.joke.R;
import dd.android.joke.core.PropertiesController;
import dd.android.joke.service.NotificationFayeService;
import roboguice.activity.RoboTabActivity;

public class ActivityLauncher extends
        RoboTabActivity {
    static protected ActivityLauncher factory = null;
    TabHost tabHost;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.act_main);
        factory = this;

        tabHost = getTabHost();
        setTabs();
        PropertiesController.readConfiguration();
        Intent intent = new Intent(this, NotificationFayeService.class);
        startService(intent);
    }
    private void setTabs()
    {
        addTab("最新",
//                R.drawable.tab_groups,
                ActivityJokeShort.class);
//        addTab("购物车", R.drawable.tab_search, ActivitySettings.class);
        addTab("有图",
//                R.drawable.tab_contact,
                ActivityJokeImage.class);
        addTab("视频",
//                R.drawable.tab_settings,
                ActivityJokeVideo.class);
//        addTab("长篇",
////                R.drawable.tab_home,
//                ActivityJokeLong.class);
    }
    private void addTab(String labelId,
                        Class<?> c)
    {
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

    public static ActivityLauncher getFactory(){
        if(factory == null)
            new ActivityLauncher();
        return factory;
    }
    public void changeTab(int i){
        tabHost.setCurrentTab(i);
    }

}
