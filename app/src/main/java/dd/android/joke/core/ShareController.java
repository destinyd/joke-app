package dd.android.joke.core;

import android.app.Activity;
import cn.bidaround.youtui_template.YouTuiViewType;
import cn.bidaround.youtui_template.YtTemplate;
import cn.bidaround.ytcore.data.ShareData;

import static dd.android.joke.core.Constants.Http.FORMAT_URL_JOKE;

/**
 * Created by dd on 14-8-7.
 */
public class ShareController {
    final static String DEFAULT_LOGO = Constants.Http.URL_BASE + "/assets/logo.png";
    public static void showShare(Activity activity, Joke joke) {
        ShareData shareData = new ShareData();
        shareData.isAppShare = false;//设置为true则分享的信息从友推后台填写的应用信息中读取，可动态更新，后面的值不用设置。
        shareData.setTitle(joke.getTitle());
        shareData.setDescription(joke.getText());
        shareData.setText(joke.getText());
        shareData.setTarget_url(String.format(FORMAT_URL_JOKE, joke.get_id()));
        if(joke.isImage())
            shareData.setImageUrl(joke.getImgurl());
        else
            shareData.setImageUrl(DEFAULT_LOGO);
        YtTemplate blackTemp = new YtTemplate(activity, YouTuiViewType.WHITE_GRID, false);  //白色网格样式不需要积分活动
        blackTemp.setShareData(shareData);//设置默认的分享数据;shareData 设置参看4.6
        blackTemp.show();
    }
}
