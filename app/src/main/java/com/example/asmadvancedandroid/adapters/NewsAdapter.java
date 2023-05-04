package com.example.asmadvancedandroid.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asmadvancedandroid.R;
import com.example.asmadvancedandroid.models.NewsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter {
    Context context;
    ArrayList<NewsModel> list;
    public NewsAdapter(Context context, ArrayList<NewsModel> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View _view, ViewGroup viewGroup) {

        View view = _view;
        if(view==null){
            view = View.inflate(viewGroup.getContext(), R.layout.newslayout,null);
            TextView txtTitle = view.findViewById(R.id.newsTitle);
            TextView txtDes = view.findViewById(R.id.newsDescription);
           ImageView newsImg = view.findViewById(R.id.imgNews);
            ViewHolder viewHolder = new ViewHolder(txtTitle,txtDes,newsImg);
            view.setTag(viewHolder);
        }
            NewsModel model = (NewsModel) getItem(i);
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            viewHolder.txtTitle.setText(model.getDisplay_name());
            viewHolder.txtDes.setText(model.getTitle());

      Picasso.get().load(model.getProfile_image()).into(viewHolder.imgNews);
        return view;
    }
    private static class ViewHolder{
        final TextView txtTitle, txtDes;
       final ImageView imgNews;


        private ViewHolder(TextView txtTitle, TextView txtDes,ImageView imgNews) {
            this.txtTitle = txtTitle;
            this.txtDes = txtDes;
          this.imgNews = imgNews;
        }
    }
}
