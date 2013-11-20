package dd.android.joke;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: dd
 * Date: 13-3-19
 * Time: 下午2:24
 * To change this template use File | Settings | File Templates.
 */
public class FCApplication extends Application {

    //for baidu map
    private static FCApplication mInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static FCApplication getInstance() {
        return mInstance;
    }


}
