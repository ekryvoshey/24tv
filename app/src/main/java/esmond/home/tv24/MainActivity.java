package esmond.home.tv24;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {
    private String myFileName;
    private File responseFile;
    private Rss rss;
    private static List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myFileName = getResources().getString(R.string.response_file_name);
        responseFile = new File(getApplicationContext().getFilesDir(), myFileName);

        run();
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
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                String responseData = response.body().string();
                System.out.println(responseData.length());

                FileOutputStream outputStream;
                Serializer serializer = new Persister();

                try {
                    outputStream = openFileOutput(myFileName, getApplicationContext().MODE_PRIVATE);
                    outputStream.write(responseData.getBytes());
                    outputStream.close();
                    rss = serializer.read(Rss.class, responseFile);
                    items = rss.getItems();
                    System.out.println(items.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //save responseData to xml
                //parse xml
                //don't worry, be happy :)
            }
        });
    }
}

