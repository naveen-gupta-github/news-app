package com.example.retrofitfornewsapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements HandleClicks
{
    public  static final String API_KEY = "d6f4bd6e78964bd58a1636955879fac4";
    private static final String TAG = "MainActivity";
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    private TextView titleView, noNewsTextView;
    private ImageView noNewsImageView;
    private RecyclerView recyclerView;
    private NewsRecyclerAdapter adapter;
    private List<Article> news = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        recyclerView = findViewById(R.id.recyclerView);
        titleView = findViewById(R.id.title);

        String country = intent.getStringExtra("COUNTRY");
        String countryName = intent.getStringExtra("CountryName");
        titleView.setText("Headlines For "+countryName);
        noNewsImageView = findViewById(R.id.noNewsImageView);
        noNewsTextView = findViewById(R.id.noNewsTextView);

        Call<News> call  = apiInterface.getNews(country,API_KEY );
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response)
            {

                if(response.isSuccessful() && response.body() !=null && response.body().getArticle() !=null)
                {
                    if(!(news.isEmpty()))
                    {
                        news.clear();
                    }

                    news  = response.body().getArticle();
                    if(news.isEmpty())
                    {
                        noNewsImageView.setVisibility(View.VISIBLE);
                        noNewsTextView.setVisibility(View.VISIBLE);

                    }else{
                        noNewsImageView.setVisibility(View.GONE);
                        noNewsTextView.setVisibility(View.GONE);
                        adapter = new NewsRecyclerAdapter(news, MainActivity.this,
                                MainActivity.this);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(MainActivity.this,
                                DividerItemDecoration.VERTICAL);
                        recyclerView.addItemDecoration(dividerItemDecoration);
                    }

                }else{

                    Toast.makeText(MainActivity.this, "No News For This Country",
                            Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<News> call, Throwable t)
            {
                Log.i(TAG, "onFailure: "+t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void handleNewsClick(int position)
    {
        Intent detailsIntent  = new Intent(this, NewsDetailsActivity.class);

        detailsIntent.putExtra("TITLE", news.get(position).getTitle());
        detailsIntent.putExtra("imageURL", news.get(position).getUrlToImage());
        detailsIntent.putExtra("URL", news.get(position).getUrl());
        detailsIntent.putExtra("Description", news.get(position).getDescription());
        detailsIntent.putExtra("Author", news.get(position).getAuthor());
        detailsIntent.putExtra("Content", news.get(position).getContent());
        detailsIntent.putExtra("Time", news.get(position).getPublishedAt());

        startActivity(detailsIntent);
    }
}