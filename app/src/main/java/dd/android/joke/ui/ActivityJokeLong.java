
package dd.android.joke.ui;

/**
 * Activity to authenticate the ABUser against an API (example API on Parse.com)
 */
public class ActivityJokeLong extends ActivityJokeList {
    static String _TYPE = "long";

    @Override
    protected String getType() {
        return _TYPE;
    }
}
