package esmond.home.tv24;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private File responseFile;
    private String fileName;
    private Rss rss;
    private static List<Item> items;
    private SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);
        Log.d("MainActivity", "onCreate()");

        items = new ArrayList<>();
        Log.d("MainActivity", "items(): " + items.size());
        fileName = getResources().getString(R.string.response_file_name);
        Log.d("MainActivity", "fileName(): " + fileName);
        responseFile = new File(getApplicationContext().getFilesDir(), fileName);
        Log.d("MainActivity", "responseFile(): " + responseFile.toString());
        run();

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        Toast.makeText(this, R.string.refresh_started, Toast.LENGTH_SHORT).show();
        swipeLayout.setRefreshing(true);
        swipeLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(false);
            }
        }, 3000);
        run();
        Toast.makeText(this, R.string.refresh_finished, Toast.LENGTH_SHORT).show();
    }

    public void run() {
        String url = getResources().getString(R.string.url_news_xml);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("MainActivity", "onFailure()");
                e.printStackTrace();
                parseResponse(responseFile);
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                Log.d("MainActivity", "onResponse()");
                String responseData = response.body().string();
                System.out.println(responseData.length());
                saveResponse(responseData);
                parseResponse(responseFile);
            }
        });
    }

    public void saveResponse(String response){
        FileOutputStream outputStream;
        String mFileName = getResources().getString(R.string.response_file_name);
        try {
            Log.d("MainActivity", "saveResponse()");
            outputStream = openFileOutput(mFileName, getApplicationContext().MODE_PRIVATE);
            outputStream.write(response.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseResponse(File file){
        Serializer serializer = new Persister();
        try {
            Log.d("MainActivity", "parseResponse()");
            rss = serializer.read(Rss.class, file);
            setItems(rss.getItems());
            loadFriendsList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onEvent(MyEvent event){
        loadWebActivity(event.getLink());
    }

    public void loadFriendsList() {
        FragmentManager fManager = getFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();
        fTransaction.add(R.id.container, new NewsListFragment(), "NewsListFragment");
        fTransaction.addToBackStack("NewsListFragment");
        fTransaction.commit();
        Log.d("MainActivity", "loadFriendsList()");
    }

    public void loadWebActivity(String link){
        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
        intent.putExtra("link", link);
        startActivity(intent);
    }

    public static List<Item> getItems() {
        Log.d("MainActivity", "getItems()");
        return items;
    }

    public static void setItems(List<Item> items) {
        Log.d("MainActivity", "setItems()");
        MainActivity.items = items;
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}

