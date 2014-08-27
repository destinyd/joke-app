
package dd.android.joke.ui;

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
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import dd.android.joke.R;
import dd.android.joke.adapter.AdapterTabs;
import dd.android.joke.models.Tab;
import it.sephiroth.android.library.widget.HListView;

import java.util.ArrayList;
import java.util.List;

public class ActivityDashboard extends
//        RoboTabActivity {
//    static protected ActivityDashboard factory = null;
//    TabHost tabHost;
//    private CharSequence mDrawerTitle;
//    private CharSequence mTitle;
//    private DrawerLayout mDrawerLayout;
//    private ActionBarDrawerToggle mDrawerToggle;
//
//    @Override
//    public void onCreate(Bundle bundle) {
//        super.onCreate(bundle);
//        setContentView(R.layout.act_main);
//        factory = this;
//        YtTemplate.init(this);/*初始化友推*/
//        tabHost = getTabHost();
//        setTabs();
//        setDrawer();
//    }
//
//    private void setDrawer() {
//        mTitle = mDrawerTitle = getTitle();
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
//                R.drawable.ic_action_refresh, R.string.drawer_open, R.string.drawer_close) {
//
//            /** Called when a drawer has settled in a completely closed state. */
//            public void onDrawerClosed(View view) {
//                super.onDrawerClosed(view);
////                getActionBar().setTitle(mTitle);
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//
//            /** Called when a drawer has settled in a completely open state. */
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
////                getActionBar().setTitle(mDrawerTitle);
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//        };
//
//        // Set the drawer toggle as the DrawerListener
//        mDrawerLayout.setDrawerListener(mDrawerToggle);
//    }
//
//    private void setTabs() {
//        addTab("最新",
////                R.drawable.tab_groups,
//                ActivityJokeShort.class);
////        addTab("购物车", R.drawable.tab_search, ActivitySettings.class);
//        addTab("有图",
////                R.drawable.tab_contact,
//                ActivityJokeImage.class);
////                ActivityJokeShort.class);
//        addTab("视频",
////                R.drawable.tab_settings,
//                ActivityJokeVideo.class);
////                ActivityJokeShort.class);
//        addTab("长篇",
////                R.drawable.tab_home,
//                ActivityJokeLong.class);
//    }
//
//    private void addTab(String labelId,
//                        Class<?> c) {
//        Intent intent = new Intent(this, c);
//        TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);
//
//        View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_text, getTabWidget(), false);
//        TextView title = (TextView) tabIndicator.findViewById(R.id.title);
//        title.setText(labelId);
//        spec.setIndicator(tabIndicator);
//        spec.setContent(intent);
//        tabHost.addTab(spec);
//    }
//public void changeTab(int i) {
//    tabHost.setCurrentTab(i);
//}
        SherlockFragmentActivity {
    static protected ActivityDashboard factory = null;

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
    String[] title;
    String[] subtitle;
    int[] icon;
    Fragment fragment1 = new FragmentList();
    Fragment fragment2 = new FragmentList();
    Fragment fragment3 = new FragmentList();
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    HListView hlv_tabs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from drawer_main.xml
        setContentView(R.layout.act_main);
        find_views();

        init_drawer();

//        init_tabs();


        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    private void find_views() {
//        hlv_tabs = (HListView)findViewById(R.id.tabs);
    }

//    List<Tab> tabs = new ArrayList<Tab>();

//    private void init_tabs() {
//        tabs.add(new Tab("short", "short", false));
//        tabs.add(new Tab("image", "image", true));
//        tabs.add(new Tab("video", "video", false));
//        tabs.add(new Tab("long", "long", false));
//
//        hlv_tabs.setAdapter(
//                new AdapterTabs(getLayoutInflater(), tabs));
//    }

    private void init_drawer() {
        // Get the Title
        mTitle = mDrawerTitle = getTitle();

        // Generate title
        title = new String[] { "Title Fragment 1", "Title Fragment 2",
                "Title Fragment 3" };

        // Generate subtitle
        subtitle = new String[] { "Subtitle Fragment 1", "Subtitle Fragment 2",
                "Subtitle Fragment 3" };

        // Generate icon
        icon = new int[] { R.drawable.ic_action_refresh, R.drawable.ic_action_refresh,
                R.drawable.ic_action_refresh };

        // Locate DrawerLayout in drawer_main.xml
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Locate ListView in drawer_main.xml
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set a custom shadow that overlays the main content when the drawer
        // opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        // Pass string arrays to MenuListAdapter
        mMenuAdapter = new MenuListAdapter(ActivityDashboard.this, title, subtitle,
                icon);

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
//                getSupportActionBar().setTitle(mDrawerTitle);// old title
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
                ft.replace(R.id.content_frame, fragment1);
                break;
            case 1:
                ft.replace(R.id.content_frame, fragment2);
                break;
            case 2:
                ft.replace(R.id.content_frame, fragment3);
                break;
        }
        ft.commit();
        mDrawerList.setItemChecked(position, true);

        // Get the title followed by the position
        setTitle(title[position]);
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
