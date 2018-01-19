package com.example.graduationproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;

import java.util.List;

/**
 * Created by 姜维昊 on 2017/4/18.
 */
public class MeRecyclerAdapter extends RecyclerView.Adapter<MeRecyclerAdapter.MainViewHolder3> {
    public Context mContext;
    public List<AVObject> mList;


    public MeRecyclerAdapter(Context mContext, List<AVObject> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public MainViewHolder3 onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("______jwh","测试一");
        return new MainViewHolder3(LayoutInflater.from(mContext).inflate(R.layout.item_me_main,parent,false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder3 holder, int position) {
        holder.myname.setText((CharSequence) mList.get(position).get("username"));
        holder.mydate.setText(mList.get(position).get("createdAt").toString());
        Log.i("______jwh", mList.get(position).get("username").toString());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MainViewHolder3 extends RecyclerView.ViewHolder {
        public TextView myname;
        public TextView mydate;
        public MainViewHolder3(View itemView) {
            super(itemView);
            myname= (TextView) itemView.findViewById(R.id.my_name);
            mydate= (TextView) itemView.findViewById(R.id.my_date);
        }
    }
}
