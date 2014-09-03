
package dd.android.joke.core;

import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.github.kevinsawicki.http.HttpRequest;
import roboguice.util.Strings;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static dd.android.joke.core.Constants.Http.*;
import static com.github.kevinsawicki.http.HttpRequest.*;


public class ServiceYS {

    /**
     * Read and connect timeout in milliseconds
     */
    private static final int TIMEOUT = 30 * 1000;

    public static User getMe() throws IOException {
        try {
            String url = URL_ME + "?" + token();
            HttpRequest request = get(url)
                    .header(HEADER_PARSE_REST_API_KEY, PARSE_REST_API_KEY)
                    .header(HEADER_PARSE_APP_ID, PARSE_APP_ID);
            String body = request.body();
            User response = JSON.parseObject(body, User.class);
            return response;
        } catch (HttpRequest.HttpRequestException e) {
            throw e.getCause();
        }
    }

    public static List<Joke> getJokes(String type, int page) throws IOException, HttpRequest.HttpRequestException {
            String url = String.format(FORMAT_URL_JOKES, type, page);
            HttpRequest request = get(url);
            String body = request.body();
            List<Joke> response = JSON.parseArray(body, Joke.class);
            return response;
    }

    private static String token() {
        return HEADER_PARSE_ACCESS_TOKEN + "=" + Settings.getFactory().getAuthToken();
    }

}
