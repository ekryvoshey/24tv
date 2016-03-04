package esmond.home.tv24;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {
    private static String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        link = getIntent().getStringExtra("link");

        FragmentManager fmOnCreate = getFragmentManager();
        FragmentTransaction ftOnCreate = fmOnCreate.beginTransaction();
        ftOnCreate.add(R.id.logInContainer, new WebViewFragment(), "WebViewFragment");
        ftOnCreate.addToBackStack("WebViewFragment");
        ftOnCreate.commit();
    }
    public static class WebViewFragment extends Fragment{
        private WebView webView;

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_webview, container, false);

            webView = (WebView)view.findViewById(R.id.myWebView);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(WebViewActivity.getLink());
            return view;
        }
    }
    public static String getLink() {
        return link;
    }
}
