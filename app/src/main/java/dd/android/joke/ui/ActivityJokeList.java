package dd.android.joke.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.inject.Inject;
import com.mindpin.android.pinterestlistview.PinterestListView;
import com.mindpin.android.pinterestlistview.internal.PLA_AdapterView;
import dd.android.joke.R;
import dd.android.joke.core.Joke;
import dd.android.joke.core.MyImageLoader;
import dd.android.joke.core.ServiceYS;
import roboguice.inject.InjectView;
import roboguice.util.RoboAsyncTask;

import java.util.ArrayList;
import java.util.List;

import static dd.android.joke.core.Constants.Extra.JOKE;

/**
 * Created by dd on 14-8-3.
 */
public abstract class ActivityJokeList extends
        ActivityBase {
    List<Joke> jokes = new ArrayList<Joke>();

    @InjectView(R.id.list)
    private PinterestListView list;
    @Inject
    private MyImageLoader avatars;
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
                List<Joke> get_commodities = ServiceYS.getJokes(getType(), page);
                if (page > 1 && get_commodities != null && get_commodities.size() == 0) {
                    page--;
                    list.set_on_load_more_listener(null);
                    Toast.makeText(ActivityJokeList.this, "没有数据了。", Toast.LENGTH_LONG);
                } else
                    addJokes(get_commodities);
                return true;
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                e.printStackTrace();
                Toast.makeText(ActivityJokeList.this, "获取信息失败", Toast.LENGTH_LONG);
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


    private void addJokes(List<Joke> get_jokes) {
        initProblems();
        jokes.addAll(get_jokes);
    }

    public void onListItemClick(PLA_AdapterView l, View v, int position, long id) {
        Joke joke = ((Joke) l.getItemAtPosition(position));
        if (joke.isVideo() || joke.isLong())
            startActivity(new Intent(this, ActiveWeb.class).putExtra(JOKE, joke));
        else if (joke.isImage()) {
            startActivity(new Intent(this, ActivePhoto.class).putExtra(JOKE, joke));
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

    protected abstract String getType();
}