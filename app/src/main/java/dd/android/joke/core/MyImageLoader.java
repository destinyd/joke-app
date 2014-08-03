package dd.android.joke.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.inject.Inject;
import dd.android.common.SDCard;
import dd.android.joke.R;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import roboguice.util.RoboAsyncTask;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static android.graphics.Bitmap.Config.ARGB_8888;
import static android.view.View.VISIBLE;
import static dd.android.joke.core.Constants.Setting.SDCARD_PATH;

/**
 * Avatar utilities
 */
public class MyImageLoader {

    private static final String TAG = "ImageLoader";

    private static final float CORNER_RADIUS_IN_DIP = 3;

    private static final int CACHE_SIZE = 75;

    private static abstract class FetchAvatarTask extends
            RoboAsyncTask<BitmapDrawable> {

        private static final Executor EXECUTOR = Executors
                .newFixedThreadPool(1);

        private FetchAvatarTask(Context context) {
            super(context, EXECUTOR);
        }

        @Override
        protected void onException(Exception e) throws RuntimeException {
            Log.d(TAG, "Avatar load failed", e);
        }
    }

    private float cornerRadius;

    private final Map<Object, BitmapDrawable> loaded = new LinkedHashMap<Object, BitmapDrawable>(
            CACHE_SIZE, 1.0F) {

        private static final long serialVersionUID = -4191124232121976720L;

        @Override
        protected boolean removeEldestEntry(
                Map.Entry<Object, BitmapDrawable> eldest) {
            return size() >= CACHE_SIZE;
        }
    };

    private final Context context;

    private File picturesDir;

    private final Drawable loadingAvatar;

    private BitmapFactory.Options options;

    /**
     * Create avatar helper
     *
     * @param context
     */
    @Inject
    public MyImageLoader(final Context context) {
//        context.getCacheDir()
        this.context = context;

        loadingAvatar = context.getResources().getDrawable(R.drawable.gravatar_icon);

        if (SDCard.hasSdcard()) {
            File sd_path = Environment
                    .getExternalStorageDirectory();

//        picturesDir = new File(context.getCacheDir(), "pictures/" + context.getPackageName());
            picturesDir = new File(sd_path, SDCARD_PATH + "/pictures");
        } else {
            picturesDir = new File(context.getCacheDir(), SDCARD_PATH + "/pictures");
        }
        if (!picturesDir.isDirectory())
            picturesDir.mkdirs();

        float density = context.getResources().getDisplayMetrics().density;
        cornerRadius = CORNER_RADIUS_IN_DIP * density;

        options = new BitmapFactory.Options();
        options.inDither = false;
        options.inPreferredConfig = ARGB_8888;
    }

    protected BitmapDrawable getImage(final String pictureId) {
        File avatarFile = new File(picturesDir, pictureId);

        if (!avatarFile.exists() || avatarFile.length() == 0)
            return null;

        Bitmap bitmap = decode(avatarFile);
        if (bitmap != null)
            return new BitmapDrawable(context.getResources(), bitmap);
        else {
            avatarFile.delete();
            return null;
        }
    }

    /**
     * Decode file to bitmap
     *
     * @param file
     * @return bitmap
     */
    protected Bitmap decode(final File file) {
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    /**
     * Fetch avatar from URL
     *
     * @param joke
     * @return bitmap
     */
//    protected BitmapDrawable fetchAvatar(final String url, final String pictureId) {
    protected BitmapDrawable fetchAvatar(final Joke joke) {
//        Uri uri = Uri.parse(joke.getImgurl());
//        uri.getPath();
//        URL myURL = new URL(url);
        String filename = getFileName(joke.getImgurl());
        String str_tmp_path = picturesDir + "/" + filename + "-raw";
        File srcFile = new File(str_tmp_path);
        if (!srcFile.getParentFile().isDirectory())
            srcFile.getParentFile().mkdirs();
        HttpRequest request = HttpRequest.get(joke.getImgurl());
        if (request.ok())
            request.receive(srcFile);

        if (!srcFile.exists() || srcFile.length() == 0)
            return null;

//        Bitmap bitmap = decode(srcFile);

//        bitmap = ImageUtils.roundCorners(bitmap, cornerRadius);
//        if (bitmap == null) {
//            srcFile.delete();
//            return null;
//        }

        String str_output_path = picturesDir + "/" + filename;
        copyFile(str_tmp_path, str_output_path);
//        Bitmap bitmap = decodeSampledBitmapFromFilepath(str_tmp_path, 400, 2048);
        Bitmap bitmap = decodeSampledBitmapFromFilepath(str_output_path, 400, 2048);
//        File file_output = new File(str_output_path);
//        if(!file_output.getParentFile().isDirectory())
//            file_output.getParentFile().mkdirs();
//        FileOutputStream output = null;
//            Thumbnails.of(srcFile)
//                    .size(600, 4096);
//        srcFile.delete();
//        return new BitmapDrawable(context.getResources(), bitmap);
//        try {
//            output = new FileOutputStream(file_output);
//            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, output))
        return new BitmapDrawable(context.getResources(), bitmap);
//            else
//                return null;
//        } catch (IOException e) {
//            Log.d(TAG, "Exception writing rounded avatar", e);
//            return null;
//        } finally {
//            if (output != null)
//                try {
//                    output.close();
//                } catch (IOException e) {
//                    // Ignored
//                }
//            srcFile.delete();
//        }

//        return new BitmapDrawable(context.getResources(), bitmap);
    }


    private MyImageLoader setImage(final Drawable image, final ImageView view) {
        return setImage(image, view, null);
    }

    private MyImageLoader setImage(final Drawable image, final ImageView view,
                                   Object tag) {
        view.setImageDrawable(image);
        view.setTag(R.id.iv_image, tag);
        view.setVisibility(VISIBLE);
        return this;
    }

    public MyImageLoader bind(final ImageView view, final Joke joke) {//final String imgurl, final String pictureId) {
        final String imgurl = joke.getImgurl();
        if (!(imgurl != null && !imgurl.equals("")))
            return setImage(loadingAvatar, view);

        final String filename = getFileName(joke.getImgurl());

        BitmapDrawable loadedImage = loaded.get(filename);
        if (loadedImage != null)
            return setImage(loadedImage, view);

        setImage(loadingAvatar, view, filename);

        final String loadUrl = imgurl;
        new FetchAvatarTask(context) {

            @Override
            public BitmapDrawable call() throws Exception {
                if (!filename.equals(view.getTag(R.id.iv_image)))
                    return null;

                final BitmapDrawable image = getImage(filename);
                if (image != null)
                    return image;
                else
                    return fetchAvatar(joke);
            }

            @Override
            protected void onSuccess(final BitmapDrawable image)
                    throws Exception {
                if (image == null)
                    return;
                loaded.put(filename, image);
                if (filename.equals(view.getTag(R.id.iv_image)))
                    setImage(image, view);
            }

        }.execute();

        return this;
    }

    private void copyFile(String srFile, String dtFile) {
        try {
            File f1 = new File(srFile);
            File f2 = new File(dtFile);
            InputStream in = new FileInputStream(f1);
            OutputStream out = new FileOutputStream(f2);

            byte[] buf = new byte[8192];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            f1.delete();
        } catch (FileNotFoundException ex) {
            String x = ex.getMessage();
            Log.d("copyFile FileNotFoundException", x);
        } catch (IOException ex) {
            String x = ex.getMessage();
            Log.d("copyFile IOException", x);
        }
    }

    public String getFileFullPath(String url) {
        return picturesDir + "/" + getFileName(url);
    }

    public static String getFileName(String url) {
        String filename = "";
        String result;
        int location = url.lastIndexOf("/");
        if (location >= 0) {
            result = url.substring(location
                    + 1);
            filename = result
                    .substring(result.indexOf("=") + 1);
        }
        if (filename == null || "".equals(filename)) {
            filename = url.substring(url.lastIndexOf("/") + 1);
        }
        return filename;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromFilepath(String filepath,
                                                         int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filepath);//.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filepath, options);
    }

    public MyImageLoader bind_gif(final View v, final Joke joke) {//final String imgurl, final String pictureId) {
        final GifImageView view = (GifImageView)v;
        final String imgurl = joke.getImgurl();
        if (!(imgurl != null && !imgurl.equals("")))
            return setImage(loadingAvatar, view);

        final String filename = getFileName(joke.getImgurl());

        BitmapDrawable loadedImage = loaded.get(filename);
        if (loadedImage != null) {
            return setImage(loadedImage, view);
        }

        setImage(loadingAvatar, view, filename);

        final String loadUrl = imgurl;
        new FetchAvatarTask(context) {

            @Override
            public BitmapDrawable call() throws Exception {
                final BitmapDrawable image = getImage(filename);
                if (image != null)
                    return image;
                else
                    return fetchAvatar(joke);
            }

            @Override
            protected void onSuccess(final BitmapDrawable image)
                    throws Exception {
                if (image == null)
                    return;
                loaded.put(filename, image);
                setGif(filename, view);
            }

        }.execute();

        return this;
    }

    private MyImageLoader setGif(final String image_url, final GifImageView view) {
        GifDrawable gifFromPath = null;
        String filename = picturesDir + "/" + image_url;
        try {
            gifFromPath = new GifDrawable(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        view.setImageDrawable(gifFromPath);
        view.setVisibility(VISIBLE);
        gifFromPath.start();
        return this;
    }
}

