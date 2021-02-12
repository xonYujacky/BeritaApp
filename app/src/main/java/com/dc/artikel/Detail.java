package com.dc.artikel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Detail extends AppCompatActivity {

    TextView title_tv,desc_tv,time_tv,source_tv;
    ImageView imageView;
    WebView webView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        title_tv = findViewById(R.id.title_tv);
        desc_tv = findViewById(R.id.desc_tv);
        time_tv = findViewById(R.id.time_tv);
        source_tv = findViewById(R.id.source_tv);
        imageView = findViewById(R.id.imageView);
        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressbar_webView);
        progressBar.setVisibility(View.VISIBLE);


        Intent intent  = getIntent();
        String title = intent.getStringExtra("title");
        String source = intent.getStringExtra("source");
        String time = intent.getStringExtra("time");
        String desc = intent.getStringExtra("desc");
        String imageUrl = intent.getStringExtra("imageUrl");
        String url = intent.getStringExtra("url");

        title_tv.setText(title);
        source_tv.setText(source);
        time_tv.setText(time);
        desc_tv.setText(desc);

        Picasso.with(Detail.this).load(imageUrl).into(imageView);

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient() );
        webView.loadUrl(url);
        if (webView.isShown()){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
