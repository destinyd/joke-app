
package dd.android.joke.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.inject.Inject;
import com.mindpin.android.pinterestlistview.PinterestListView;
import com.mindpin.android.pinterestlistview.internal.PLA_AdapterView;
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
public class ActivityJokeShort extends
        ActivityBase {

    static String _TYPE = "short";
    List<Joke> jokes = new ArrayList<Joke>();

    @InjectView(R.id.list)
    private PinterestListView list;
    @Inject
    private ImageLoader avatars;
    AdapterJokes adapter = null;
    int page = 1, pass_page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        setContentView(R.layout.list);

        list.setOnItemClickListener(new PLA_AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(PLA_AdapterView<?> parent, View view, int position, long id) {
                onListItemClick(parent, view, position, id);
            }
        });

        list.set_on_refresh_listener(new PinterestListView.OnRefreshListener() {
            @Override
            public void on_refresh() {
                refreshPage();
            }
        });

        list.set_on_load_more_listener(
                new PinterestListView.OnLoadMoreListener() {
                    @Override
                    public void on_load_more() {
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

    private void refreshPage() {
        page = 1;
        getJokes();
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
                    list.set_on_load_more_listener(null);
                    Toast.makeText(ActivityJokeShort.this, "没有数据了。", Toast.LENGTH_LONG);
                } else
                    addJokes(get_commodities);
                return true;
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                e.printStackTrace();
                Toast.makeText(ActivityJokeShort.this, "获取信息失败", Toast.LENGTH_LONG);
            }

            @Override
            public void onSuccess(Boolean relationship) {
                commodities_to_list();
            }

            @Override
            protected void onFinally() throws RuntimeException {
                progressDialogDismiss();
                list.on_load_more_complete();
            }
        }.execute();
    }


    private void addJokes(List<Joke> get_commodities) {
        initProblems();
        jokes.addAll(get_commodities);
    }

    public void onListItemClick(PLA_AdapterView l, View v, int position, long id) {
        Joke joke = ((Joke) l.getItemAtPosition(position));
        if (joke.isVideo())
            startActivity(new Intent(this, ActiveVideo.class).putExtra(JOKE, joke));
        else if (joke.isImage()) {
//            if(joke.isGif())
//                startActivity(new Intent(this, ActiveGif.class).putExtra(JOKE,joke));
//            else
            startActivity(new Intent(this, ActiveImage.class).putExtra(JOKE, joke));
        }

    }

    private void commodities_to_list() {
        if (adapter == null) {
            adapter = new AdapterJokes(
                    getLayoutInflater(), jokes,
                    avatars);
            list.setAdapter(adapter);
        } else {
            adapter.setItems(jokes);
            adapter.notifyDataSetChanged();
        }
    }

}
