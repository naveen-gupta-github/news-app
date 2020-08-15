package com.example.retrofitfornewsapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class NewsDetailsActivity extends AppCompatActivity {

    private static final String TAG = "NewsDetailsActivity";
    private TextView titleTextView, authorTextView, contentTextView;
    private ImageView newsImageView;
    private Button  readFullNewsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        titleTextView = findViewById(R.id.newsTitle);
        authorTextView = findViewById(R.id.newsAuthor);
        contentTextView = findViewById(R.id.news_content);
        newsImageView = findViewById(R.id.news_image);
        readFullNewsButton = findViewById(R.id.fullNewsButton);

        final Intent intent = getIntent();

        titleTextView.setText(intent.getStringExtra("TITLE"));

        String time = TimeDateFormatter.DateToTimeFormat(intent.getStringExtra("Time"));

        String author  = intent.getStringExtra("Author");
        if(author == null)
        {
            authorTextView.setText(time);
        }else{

            authorTextView.setText(author);
        }

        if(intent.getStringExtra("Content") ==null)
        {
            contentTextView.setText(intent.getStringExtra("Description"));
        }else
        {
            String newsContent  = intent.getStringExtra("Content");
            newsContent = newsContent.replace(newsContent,
                           newsContent.substring(0, newsContent.length()-14))+"...";

            contentTextView.setText(newsContent);
        }

        Glide.with(getApplicationContext()).load(intent.getStringExtra("imageURL"))
                .into(newsImageView);

        readFullNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(intent.getStringExtra("URL"))));
            }
        });
    }

    //for the back button on Action Bar
    @Override
    public boolean onSupportNavigateUp()
    {
        finish();
        return true;
    }
}