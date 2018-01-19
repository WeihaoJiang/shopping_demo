package com.example.graduationproject.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.graduationproject.MeRecyclerAdapter;
import com.example.graduationproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {
    private RecyclerView mRecyclerView1;
    private List<AVObject> mList = new ArrayList<>();
    private MeRecyclerAdapter mRecyclerAdapter1;
    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_me, container, false);
        mRecyclerAdapter1 = new MeRecyclerAdapter(MeFragment.this.getActivity(), mList);
        Log.i("______jwh","jwhjwh");
        mRecyclerView1 = (RecyclerView)view.findViewById(R.id.list_main2);
        mRecyclerView1.setHasFixedSize(true);
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(MeFragment.this.getActivity()));
        Log.i("______jwh","jwhjwh2");



       // mRecyclerView1.setAdapter(mRecyclerAdapter1);
        Log.i("______jwh","jwhjwh3");
    mRecyclerView1.setAdapter(mRecyclerAdapter1);


        return view;
    }


    private void initData() {
        mList.clear();
        AVQuery<AVObject> avQuery = new AVQuery<>("_User");
//        avQuery.orderByDescending("createdAt");
//        avQuery.include("owner");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    mList.addAll(list);
                    //GoodsAdapter
                    //mExpandableListView.deferNotifyDataSetChanged();
                    mRecyclerAdapter1.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        AVAnalytics.onResume(this.getActivity());
        initData();
    }
    @Override
    public void onPause() {
        super.onPause();
        AVAnalytics.onPause(this.getActivity());
    }



}
