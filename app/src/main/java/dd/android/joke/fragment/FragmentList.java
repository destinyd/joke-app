package dd.android.joke.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.mindpin.android.pinterestlistview.PinterestListView;
import com.mindpin.android.pinterestlistview.internal.PLA_AdapterView;
import dd.android.joke.R;
import dd.android.joke.core.Joke;
import dd.android.joke.core.ServiceYS;
import dd.android.joke.ui.ActivePhoto;
import dd.android.joke.ui.ActiveWeb;
import dd.android.joke.adapter.AdapterJokes;
import dd.android.joke.fragment.base.FragmentBase;
import roboguice.util.RoboAsyncTask;

import java.util.ArrayList;
import java.util.List;

import static dd.android.joke.core.Constants.Extra.JOKE;

/**
 * Created by dd on 14-8-27.
 */
public class FragmentList extends FragmentBase {
    private static final String TAG = "FragmentList";
    private View current_view;
    LayoutInflater inflater;
    private String type;

    List<Joke> jokes = new ArrayList<Joke>();

    private PinterestListView list;
    AdapterJokes adapter = null;
    int page = 1;

    public FragmentList(String type) {
        this.type = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        init(inflater);

        return current_view;
    }

    private void init(LayoutInflater inflater) {
        this.inflater = inflater;
        current_view = inflater.inflate(R.layout.list, null);
        list = (PinterestListView) current_view.findViewById(R.id.list);
        init_refresh_texts();

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
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.e(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        if(adapter == null)
            refreshPage();
        else
            list.set_adapter(adapter);
    }

    private void init_refresh_texts() {
        list.set_text_refreshing("正在刷新");
        list.set_text_pull_to_refresh("下拉刷新");
        list.set_text_release_to_refresh("放开刷新");
    }


    private void initJokesIfNull() {
        if (jokes == null)
            initJokes();
    }

    private void initJokes() {
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
        progressDialogShow(activity);
        new RoboAsyncTask<Boolean>(activity) {
            public Boolean call() throws Exception {
                List<Joke> get_jokes = ServiceYS.getJokes(getType(), page);
                if (page > 1 && get_jokes != null && get_jokes.size() == 0) {
                    page--;
                    list.set_on_load_more_listener(null);
                    Toast.makeText(activity, "没有数据了。", Toast.LENGTH_LONG);
                    Log.e(TAG, "no more");
                } else if (page == 1) {
                    refreshJokes(get_jokes);
                    Log.e(TAG, "refreshJokes");
                } else {
                    addJokes(get_jokes);
                    Log.e(TAG, "addJokes");
                }
                return true;
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                e.printStackTrace();
                Toast.makeText(activity, "获取信息失败", Toast.LENGTH_LONG);
                Log.e(TAG, "onException");
            }

            @Override
            public void onSuccess(Boolean relationship) {
                Log.e(TAG, "onSuccess");
                jokes_to_list();
            }

            @Override
            protected void onFinally() throws RuntimeException {
                progressDialogDismiss();
                list.on_load_more_complete();
                list.on_refresh_complete();
            }
        }.execute();
    }

    private void refreshJokes(List<Joke> get_jokes) {
        initJokes();
        jokes.addAll(get_jokes);
    }


    private void addJokes(List<Joke> get_jokes) {
        initJokesIfNull();
        jokes.addAll(get_jokes);
    }

    public void onListItemClick(PLA_AdapterView l, View v, int position, long id) {
        Log.d(TAG, "l:" + l.getClass().getName());
        Log.d(TAG, "v:" + v.getClass().getName());
        Log.d(TAG, "position:" + position);
        Log.d(TAG, "id:" + id);
        Joke joke = ((Joke) l.getItemAtPosition(position));
        if (joke.isVideo() || joke.isLong())
            startActivity(new Intent(activity, ActiveWeb.class).putExtra(JOKE, joke));
        else if (joke.isImage()) {
            startActivity(new Intent(activity, ActivePhoto.class).putExtra(JOKE, joke));
        }

    }

    private void jokes_to_list() {
        Log.e(TAG, "jokes_to_list");
        if (adapter == null) {
            adapter = new AdapterJokes(inflater, jokes);
            list.setAdapter(adapter);
        } else {
            adapter.setItems(jokes);
            adapter.notifyDataSetChanged();
        }
    }

    protected String getType(){
        return type;
    };
}
