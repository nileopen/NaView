package com.na.naview.demo.adpater;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.na.naview.demo.ProgrossBarActivity;
import com.na.naview.demo.R;
import com.na.naview.demo.TextViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @actor:taotao
 * @DATE: 17/2/4
 */
public class MainAdapter extends RecyclerView.Adapter {

    private List mList = new ArrayList();

    public MainAdapter() {
        initData();
    }

    private void initData() {
        MainItem item = new MainItem("NaProgressBar", ProgrossBarActivity.class);
        mList.add(item);
        for (int i = 0; i < 30; ++i) {
            item = new MainItem("NaTextView", TextViewActivity.class);
            mList.add(item);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        MainHolder holder = new MainHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MainHolder mainHolder = (MainHolder) holder;
        MainItem item = (MainItem) mList.get(position);
        mainHolder.setData(item);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    private class MainHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private TextView tvTitle;

        public MainHolder(View view) {
            super(view);
            itemView = view;
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        }

        public void setData(final MainItem item) {
            if (item != null){
                tvTitle.setText(item.getTitle());
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (item != null){
                            Context context = itemView.getContext();
                            context.startActivity(new Intent(context, item.activityClass));
                        }
                    }
                });
            }
        }
    }

    private class MainItem {
        private String title;
        private Class activityClass;

        public MainItem(String title, Class activityClass) {
            this.title = title;
            this.activityClass = activityClass;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Class getActivityClass() {
            return activityClass;
        }

        public void setActivityClass(Class activityClass) {
            this.activityClass = activityClass;
        }
    }
}
