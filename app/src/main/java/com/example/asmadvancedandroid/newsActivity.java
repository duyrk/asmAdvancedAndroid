package com.example.asmadvancedandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.asmadvancedandroid.Services.NewsService;
import com.example.asmadvancedandroid.adapters.NewsAdapter;
import com.example.asmadvancedandroid.models.NewsModel;

import java.util.ArrayList;

public class newsActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<NewsModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        listView = findViewById(R.id.lv_News);
    }
    public void onGetNews(View view){
        Intent intent = new Intent(newsActivity.this, NewsService.class);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onGetNews(null);
        IntentFilter intentFilter = new IntentFilter("getAPI");
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,intentFilter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            list  = (ArrayList<NewsModel>) intent.getSerializableExtra("result");
            NewsAdapter newsAdapter = new NewsAdapter(newsActivity.this,list);
            listView.setAdapter(newsAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(i).getLink()));
                    startActivity(browserIntent);
                }
            });
        }
    };
}