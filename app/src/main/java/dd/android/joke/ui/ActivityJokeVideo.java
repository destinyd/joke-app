
package dd.android.joke.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import com.costum.android.widget.LoadMoreListView;
import com.github.kevinsawicki.wishlist.Toaster;
import com.google.inject.Inject;
import dd.android.joke.R;
import dd.android.joke.core.ImageLoader;
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
public class ActivityJokeVideo extends
        ActivityBase {

    static String _TYPE = "video";
    List<Joke> jokes = new ArrayList<Joke>();

    @InjectView(R.id.lv_list)
    private LoadMoreListView lv_list;
    @Inject
    private ImageLoader avatars;
    AdapterJokes adapter = null;
    int page = 1, pass_page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.act_commodities);

        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                onListItemClick((LoadMoreListView) parent, view, position, id);
            }
        });
        lv_list.setOnLoadMoreListener(
                new LoadMoreListView.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        getNextPage();
                    }
                }
        );
        getJokes();
    }

    private void initProblems() {
        if (jokes == null)
            jokes = new ArrayList<Joke>();
    }

    private void getNextPage() {
        page++;
        getJokes();
    }

    private void getJokes() {
        progressDialogShow(this);
        new RoboAsyncTask<Boolean>(this) {
            public Boolean call() throws Exception {
                List<Joke> get_commodities = ServiceYS.getJokes(_TYPE, page);
                if (page > 1 && get_commodities != null && get_commodities.size() == 0) {
                    page--;
                    lv_list.setOnLoadMoreListener(null);
                    Toaster.showLong(ActivityJokeVideo.this, "没有数据了。");
                } else
                    addJokes(get_commodities);
                return true;
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                e.printStackTrace();
                Toaster.showLong(ActivityJokeVideo.this, "获取信息失败");
            }

            @Override
            public void onSuccess(Boolean relationship) {
                commodities_to_list();
            }

            @Override
            protected void onFinally() throws RuntimeException {
                progressDialogDismiss();
                lv_list.onLoadMoreComplete();
            }
        }.execute();
    }


    private void addJokes(List<Joke> get_commodities) {
        initProblems();
        jokes.addAll(get_commodities);
    }

    public void onListItemClick(LoadMoreListView l, View v, int position, long id) {
        Joke joke = ((Joke) l.getItemAtPosition(position));
        if (joke.isVideo())
            startActivity(new Intent(this, ActiveVideo.class).putExtra(JOKE, joke));
        else if (joke.isImage())
            startActivity(new Intent(this, ActiveImage.class).putExtra(JOKE, joke));

    }

    private void commodities_to_list() {
        if (adapter == null) {
            adapter = new AdapterJokes(
                    getLayoutInflater(), jokes,
                    avatars);
            lv_list.setAdapter(adapter);
        } else {
            adapter.setItems(jokes);
            adapter.notifyDataSetChanged();
        }
    }

}
