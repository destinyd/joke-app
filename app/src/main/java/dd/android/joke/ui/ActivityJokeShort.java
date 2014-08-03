
package dd.android.joke.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.inject.Inject;
import com.mindpin.android.pinterestlistview.PinterestListView;
import com.mindpin.android.pinterestlistview.internal.PLA_AdapterView;
import dd.android.joke.R;
import dd.android.joke.core.MyImageLoader;
import dd.android.joke.core.Joke;
import dd.android.joke.core.ServiceYS;
import roboguice.inject.InjectView;
import roboguice.util.RoboAsyncTask;

import java.util.ArrayList;
import java.util.List;

import static dd.android.joke.core.Constants.Extra.JOKE;

/**
 * Activity to authenticate the ABUser against an API (example API on Parse.com)
 */
public class ActivityJokeShort extends ActivityJokeList {
    static String _TYPE = "short";

    @Override
    protected String getType() {
        return _TYPE;
    }
}
