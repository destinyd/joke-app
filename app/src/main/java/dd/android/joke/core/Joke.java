package dd.android.joke.core;

import android.text.TextUtils;
import dd.android.common.PrettyDateFormat;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dd
 * Date: 13-3-20
 * Time: 上午10:18
 * To change this template use File | Settings | File Templates.
 */
public class Joke implements Serializable {
    private static final long serialVersionUID = 21021235433891457L;

    public String _id;
    String joke_id, name, text, imgurl, videourl, title;
    int forward;
    Date created_at;
    List<String> tags;

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String published_at(){
        if(getCreated_at() != null)
            return PrettyDateFormat.defaultFormat(getCreated_at());
        else
            return "";
    }

//    Picture picture;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getForward() {
        return forward;
    }

    public void setForward(int forward) {
        this.forward = forward;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getJoke_id() {
        return joke_id;
    }

    public void setJoke_id(String joke_id) {
        this.joke_id = joke_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //    public Picture getPicture() {
//        return picture;
//    }
//
//    public void setPicture(Picture picture) {
//        this.picture = picture;
//    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public Boolean isImage(){
        return getImgurl() != null && !getImgurl().equals("");
    }

    public Boolean isVideo(){
        return getVideourl() != null && !getVideourl().equals("");
    }

    public Boolean isGif(){
        return isImage() && "gif".endsWith(getImgurl().substring(getImgurl().length()-3));
    }

    public Boolean isLong(){
        return tags != null && tags.contains("长篇");
    }
}
