package dd.android.joke.models;

/**
 * Created by dd on 14-8-26.
 */
public class Tab {
    String title = "";
    String tag = "";
    int res_id;
    boolean selected = false;

    public Tab(String title, String tag, boolean selected) {
        this.title = title;
        this.tag = tag;
        this.selected = selected;
    }

    public String get_title() {
        return title;
    }

    public void set_title(String title) {
        this.title = title;
    }

    public String get_tag() {
        return tag;
    }

    public void set_tag(String tag) {
        this.tag = tag;
    }

    public int get_res_id() {
        return res_id;
    }

    public void set_res_id(int res_id) {
        this.res_id = res_id;
    }

    public boolean is_selected() {
        return selected;
    }

    public void set_selected(boolean selected) {
        this.selected = selected;
    }
}
