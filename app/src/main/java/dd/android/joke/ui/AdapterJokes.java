package dd.android.joke.ui;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import cn.bidaround.youtui_template.YouTuiViewType;
import cn.bidaround.youtui_template.YtTemplate;
import cn.bidaround.ytcore.data.ShareData;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import dd.android.common.PrettyDateFormat;
import dd.android.joke.R;
import dd.android.joke.core.Joke;
import dd.android.joke.core.MyImageLoader;
import dd.android.joke.core.ShareController;

import java.util.List;

import static dd.android.joke.core.Constants.Http.*;

public class AdapterJokes extends AdapterAlternatingColorList<Joke> {
    private static final String TAG = "AdapterJokes";
    private final MyImageLoader avatars;
    private final LayoutInflater layoutInflater;
    final static DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.loading) // resource or drawable
            .cacheInMemory()
            .cacheOnDisk(true)
            .build();

    public AdapterJokes(LayoutInflater inflater, List<Joke> items, MyImageLoader avatars) {
        super(R.layout.item_joke,inflater,items);
        this.avatars = avatars;
        layoutInflater = inflater;
    }

    /**
     * @param inflater
     * @param items
     */
    public AdapterJokes(LayoutInflater inflater, List<Joke> items) {
        this(inflater, items, null);

    }
    @Override
    public long getItemId(final int position) {
        final String id = getItem(position)._id;
        return !TextUtils.isEmpty(id) ? id.hashCode() : super
                .getItemId(position);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[] { R.id.tv_name, R.id.tv_published_at, R.id.tv_text, R.id.iv_image, R.id.iv_gif, R.id.iv_play, R.id.ll_share };
    }

    @Override
    protected void update(int position, final Joke joke) {
        super.update(position, joke);

        setText(0, joke.getName());
        setText(1, PrettyDateFormat.defaultFormat(joke.getCreated_at()));
        setText(2, joke.getText());

        setGone(3, true);
        setGone(4, true);
        setGone(5, true);

        if(joke.isVideo() || joke.isImage()){
            ImageLoader.getInstance().displayImage(joke.getImgurl(), imageView(3), options);
            setGone(3, false);
//            ImageLoader.getInstance().loadImageSync(joke.getImgurl(), imageView(3), options);
        }

        if(joke.isVideo())
        {
            setGone(5, false);
        }
        else{
            setGone(5, true);
            if(joke.isGif())
                setGone(4, false);
        }
        view(6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareController.showShare(ActivityDashboard.getFactory(), joke);
//                FrontiaSocialShareContent fssc = new FrontiaSocialShareContent();
//                fssc.setWXMediaObjectType(FrontiaSocialShareContent.FrontiaIMediaObject.TYPE_URL);
//                fssc.setQQRequestType(FrontiaSocialShareContent.FrontiaIQQReqestType.TYPE_DEFAULT);
//                fssc.setTitle(joke.getTitle());
//                fssc.setLinkUrl(String.format(FORMAT_URL_JOKE, joke.get_id()));
//                fssc.setImageUri(Uri.parse(joke.getImgurl()));
//                FrontiaSocialShare socialshare = Frontia.getSocialShare();
//                socialshare.setClientName(FrontiaAuthorization.MediaType.QQFRIEND.toString(), "笑料百出");
//                Activity activity = (Activity)view.getContext();
//                Intent intent = activity.getIntent();
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                Log.d(TAG, "OnClickListener view:" + view.getClass().getName());
//                Log.d(TAG, "view.getContext().class:" + view.getContext().getClass().getName());
//                Log.d(TAG, "intent:" + intent.getClass().getName());
//                socialshare.show(activity.getWindow().getDecorView(), fssc, FrontiaSocialShare.FrontiaTheme.LIGHT, new FrontiaSocialShareListener() {
//                    @Override
//                    public void onSuccess() {
//
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s) {
//
//                    }
//
//                    @Override
//                    public void onCancel() {
//
//                    }
//                });
            }
        });
    }
}