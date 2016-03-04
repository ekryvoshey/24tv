package esmond.home.tv24;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>{
    public List<Item> items;
    public Context context = NewsListFragment.context;

    public NewsListAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.news_card_view, viewGroup, false);
        NewsViewHolder holder = new NewsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int i) {
        final int n = i;
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MyEvent(items.get(n).getLink()));
                System.out.println("card clicked");
            }
        });
        holder.newsTitle.setText(items.get(i).getTitle());
        String[] newDate = (items.get(i).getDate().split("T"));
        String newTime = newDate[1].substring(0, 5);
        holder.newsDate.setText("Опубліковано: "+newTime+"/"+(newDate[0]));
        Glide.with(context).load(items.get(i).getUrl()).into(holder.newsImage);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        if (items == null) return 0;
        return items.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView newsImage;
        TextView newsTitle;
        TextView newsDate;

        public NewsViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.news_card_view);
            newsImage = (ImageView)itemView.findViewById(R.id.news_image);
            newsTitle = (TextView)itemView.findViewById(R.id.title_text);
            newsDate = (TextView)itemView.findViewById(R.id.news_date);

            newsTitle.setTextColor(Color.WHITE);
            newsDate.setTextColor(Color.WHITE);
        }
    }

    public void setData(List<Item> items) {
        this.items = items;
    }
}
