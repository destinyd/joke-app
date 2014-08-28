package dd.android.joke.activity.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockFragmentActivity;
import dd.android.joke.R;
import roboguice.activity.RoboFragmentActivity;

/**
 * Created by dd on 14-8-27.
 */
public class BaseFragmentActivity extends RoboSherlockFragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected  void change_fragment(Fragment fragment){
        Bundle args = new Bundle();
        change_fragment(fragment, args);
    }

    protected void change_fragment(Fragment fragment, Bundle args){
        FragmentManager fragment_manager = getSupportFragmentManager();
        FragmentTransaction transaction = fragment_manager.beginTransaction();
        fragment.setArguments(args);
        transaction.replace(R.id.content_frame, fragment, "topic");
        transaction.addToBackStack("topic").commit();
    }
}