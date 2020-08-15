package com.example.retrofitfornewsapi;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder>
{
    private static final String TAG = "NewsRecyclerAdapter";

    List<Article> news;
    Context context;
    HandleClicks handleClicks;

    public NewsRecyclerAdapter(List<Article> news, Context context, HandleClicks handleClicks)
    {
        this.news = news;
        this.context = context;
        this.handleClicks =handleClicks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.news_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Article newsItem = news.get(position);
        holder.headlineTextView.setText(newsItem.getTitle());

        if(newsItem.getAuthor() == null)
        {
            holder.authorTextView.setVisibility(View.GONE);
        }else
        {
            holder.authorTextView.setVisibility(View.VISIBLE);
            holder.authorTextView.setText(newsItem.getAuthor());
        }

        if(newsItem.getUrlToImage()==null)
        {
            Glide.with(context)
                    .load(R.mipmap.default_img)
                    .into(holder.newsImageView);

        }else{

            Glide.with(context)
                    .load(newsItem.getUrlToImage())
                    .into(holder.newsImageView);
        }

        holder.publishedAtTextView.setText(TimeDateFormatter.DateToTimeFormat(newsItem.getPublishedAt()));



    }

    @Override
    public int getItemCount()
    {
        return   news.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView newsImageView;
        TextView headlineTextView, authorTextView, publishedAtTextView;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            newsImageView = itemView.findViewById(R.id.newsImage);
            headlineTextView = itemView.findViewById(R.id.headline);
            authorTextView = itemView.findViewById(R.id.author);
            publishedAtTextView = itemView.findViewById(R.id.publishedAt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   handleClicks.handleNewsClick(getAdapterPosition());
                }
            });
        }
    }

}
