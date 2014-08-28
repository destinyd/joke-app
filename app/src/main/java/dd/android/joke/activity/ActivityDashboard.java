
package dd.android.joke.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import cn.bidaround.youtui_template.YtTemplate;
import com.actionbarsherlock.view.MenuItem;
import dd.android.joke.R;
import dd.android.joke.activity.base.BaseFragmentActivity;
import dd.android.joke.fragment.FragmentList;
import dd.android.joke.ui.MenuListAdapter;
import dd.android.joke.widget.SelectableLinearLayout;

import java.util.ArrayList;

public class ActivityDashboard extends BaseFragmentActivity implements View.OnClickListener {
    private static final String TAG = "ActivityDashboard";
    static protected ActivityDashboard factory = null;
    private ArrayList<SelectableLinearLayout> tabs;

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


    // Declare Variables
    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    MenuListAdapter mMenuAdapter;
    String[] drawer_titles;
    String[] titles;
    int[] icon;
    Fragment fragment_short = new FragmentList("short");
    Fragment fragment_image = new FragmentList("image");
    Fragment fragment_video = new FragmentList("video");
    Fragment fragment_long = new FragmentList("long");
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    SelectableLinearLayout sll_short, sll_image, sll_video, sll_long;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        factory = this;
        YtTemplate.init(this);/*初始化友推*/

        find_views();

        init_tabs();

        init_drawer();

        if (savedInstanceState == null) {
            selectTab(1);
        }
    }

    private void init_tabs() {
        // Generate title
        titles = new String[]{"最新", "有图",
                "视频", "长篇"};
        sll_image.select();
        tabs = new ArrayList<SelectableLinearLayout>();
        tabs.add(sll_short);
        tabs.add(sll_image);
        tabs.add(sll_video);
        tabs.add(sll_long);
        sll_short.setOnClickListener(this);
        sll_image.setOnClickListener(this);
        sll_video.setOnClickListener(this);
        sll_long.setOnClickListener(this);
    }

    private void find_views() {
        sll_short = (SelectableLinearLayout) findViewById(R.id.sll_short);
        sll_image = (SelectableLinearLayout) findViewById(R.id.sll_image);
        sll_video = (SelectableLinearLayout) findViewById(R.id.sll_video);
        sll_long = (SelectableLinearLayout) findViewById(R.id.sll_long);
    }

    private void init_drawer() {
        // Generate title
        drawer_titles = new String[]{"分享给好友", "用户反馈"
        };
        //,"精彩推荐"};

        // Generate icon
        icon = new int[]{R.drawable.ic_action_refresh, R.drawable.ic_action_refresh,
                R.drawable.ic_action_refresh,
                R.drawable.ic_action_refresh};

        // Locate DrawerLayout in drawer_main.xml
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Locate ListView in drawer_main.xml
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set a custom shadow that overlays the main content when the drawer
        // opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        // Pass string arrays to MenuListAdapter
        mMenuAdapter = new MenuListAdapter(ActivityDashboard.this, drawer_titles, icon);

        // Set the MenuListAdapter to the ListView
        mDrawerList.setAdapter(mMenuAdapter);

        // Capture listview menu item click
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // Enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);// old title
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                // Set the title on the action when drawer open
                getSupportActionBar().setTitle("设置");
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                mDrawerLayout.closeDrawer(mDrawerList);
            } else {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        SelectableLinearLayout view_clicked = (SelectableLinearLayout) view;
        int index_select = 0;
        if (!view_clicked.isSelected()) {
            for (int i = 0; i < tabs.size(); i++) {
                SelectableLinearLayout tab = tabs.get(i);
                tab.unselect();
                if (tab == view) {
                    index_select = i;
                }
            }
            selectTab(index_select);
        }
    }

    private void selectTab(int position) {
        tabs.get(position).select();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Locate Position
        switch (position) {
            case 0:
                ft.replace(R.id.content_frame, fragment_short);
                break;
            case 1:
                ft.replace(R.id.content_frame, fragment_image);
                break;
            case 2:
                ft.replace(R.id.content_frame, fragment_video);
                break;
            case 3:
                ft.replace(R.id.content_frame, fragment_long);
                break;
        }
        ft.commit();
//        mDrawerList.setItemChecked(position, true);

        // Get the title followed by the position
        setTitle(titles[position]);
//        // Close drawer
//        mDrawerLayout.closeDrawer(mDrawerList);
    }

    // ListView click listener in the navigation drawer
    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Locate Position
        switch (position) {
            case 0:
                ft.replace(R.id.content_frame, fragment_short);
                break;
            case 1:
                ft.replace(R.id.content_frame, fragment_image);
                break;
            case 2:
                ft.replace(R.id.content_frame, fragment_video);
                break;
            case 3:
                ft.replace(R.id.content_frame, fragment_long);
                break;
        }
        ft.commit();
        mDrawerList.setItemChecked(position, true);

        // Get the title followed by the position
        setTitle(drawer_titles[position]);
        // Close drawer
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
}
