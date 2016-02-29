package esmond.home.tv24;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MyRequest {

    private static String responseData;
    private String url = "http://24tv.ua/rss/all.xml";
    private OkHttpClient client = new OkHttpClient();

    public void run() {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                //use cashed news here
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                responseData = response.body().string();

            }
        });
    }
}
