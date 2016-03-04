package esmond.home.tv24;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class NewsListFragment extends Fragment {
    public RecyclerView.LayoutManager layoutManager;
    public List<Item> items;
    public RecyclerView recyclerView;
    public NewsListAdapter adapter;
    public static Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, null);

        layoutManager = new LinearLayoutManager(getActivity());
        context = view.getContext();
        items = MainActivity.getItems();
        adapter = new NewsListAdapter(items);

        recyclerView = (RecyclerView)view.findViewById(R.id.list_news);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        Log.d("NewsListFragment", "onCreateView()");
        Log.d("NewsListFragment", "context: "+context.toString());
        Log.d("NewsListFragment", "items: "+items.size());
        Log.d("NewsListFragment", "adapter: "+adapter.toString());
        Log.d("NewsListFragment", "recyclerView: "+recyclerView.toString());
        Log.d("NewsListFragment", "layoutManager: "+layoutManager.toString());
        return view;
    }
}
