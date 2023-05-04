package com.example.asmadvancedandroid.Services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.asmadvancedandroid.models.NewsModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class NewsService extends IntentService {
    private AsyncHttpClient client = new SyncHttpClient();
    private final String url = "https://api.stackexchange.com/2.3/questions?page=3&pagesize=5&order=desc&sort=activity&site=stackoverflow";


    public NewsService() {
        super("NewsService");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
                client.get(this,url,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            JSONArray array = response.getJSONArray("items");
//                            JSONArray array1 = response.getJSONArray("owner");
                            ArrayList<NewsModel> list = new ArrayList<>();
                            for (int i =0; i<array.length();i++){
                                JSONObject object = (JSONObject) array.get(i);
                                JSONObject object1 = object.getJSONObject("owner");
                                String title = object.getString("title");
                                String profile_image = object1.getString("profile_image");
                                String link = object.getString("link");
                                String display_name = object1.getString("display_name");
                                NewsModel model = new NewsModel(title,link,profile_image,display_name);
                                list.add(model);
                            }
                            //dungf local
                            Log.d(">>>>>>TAG","onSuccess"+list.toString());
                            Intent outIntent = new Intent("getAPI");
                            outIntent.putExtra("result",list);
                            LocalBroadcastManager.getInstance(NewsService.this).sendBroadcast(outIntent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }







//                        try {
//                            JSONArray array = response.getJSONArray("articles");
//                            ArrayList<NewsModel> list = new ArrayList<>();
//                            for (int i =0; i<list.size();i++){
//                                JSONObject object = (JSONObject) array.get(i);
//                                String title = object.getString("title");
//                                String des = object.getString("description");
//                                String url = object.getString("url");
//                                String urltoImage = object.getString("urlToImage");
//                                NewsModel model = new NewsModel(title,des,url,urltoImage);
//                                list.add(model);
//                            }
//                            //dungf local
//                            Log.d(">>>>>>>>onhandleintent", list.toString());
//                            Intent outIntent = new Intent("getAPI");
//                            outIntent.putExtra("result",list);
//                            LocalBroadcastManager.getInstance(NewsService.this).sendBroadcast(outIntent);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                    }
                });
            }
        }
    }


