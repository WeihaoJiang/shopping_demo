package com.example.graduationproject.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.graduationproject.MainRecyclerAdapter;
import com.example.graduationproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private SearchView sv;
    //private ListView lv;
    private String[] mStrings = {  };
    private RecyclerView mRecyclerView;
    private List<AVObject> mList = new ArrayList<>();
    private MainRecyclerAdapter mRecyclerAdapter;

    private SliderLayout mSliderlayout;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);

//        for (int i=0;i<mList.size();i++){
//            mStrings[i]= String.valueOf(mList.get(i));
//        }

        find(view);

        //initData();
        initSlider();

        return view;
    }
public void find(View v){

    //lv = (ListView)v.findViewById(R.id.lv);
    mSliderlayout= (SliderLayout)v.findViewById(R.id.slider);
    mRecyclerView = (RecyclerView)v.findViewById(R.id.list_main);
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(HomeFragment.this.getActivity()));

    mRecyclerAdapter = new MainRecyclerAdapter(mList, HomeFragment.this.getActivity());
    mRecyclerView.setAdapter(mRecyclerAdapter);
    // 设置ListView启用过滤
   // lv.setTextFilterEnabled(true);
    sv = (SearchView)v.findViewById(R.id.sv);
    // 设置该SearchView默认是否自动缩小为图标
    sv.setIconifiedByDefault(false);
    // 设置该SearchView显示搜索按钮
    sv.setSubmitButtonEnabled(true);
    // 设置该SearchView内默认显示的提示文本
    sv.setQueryHint("请输入查找内容");


//    FloatingActionButton fab = (FloatingActionButton)v.findViewById(R.id.fab);
//    fab.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            startActivity(new Intent(HomeFragment.this.getActivity(), PublishFragment.class));
//        }
//    });
   // lv.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, mStrings));
    sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            // 实际应用中应该在该方法内执行实际查询
            // 此处仅使用Toast显示用户输入的查询内容
            Toast.makeText(HomeFragment.this.getActivity(), "您的选择是:" + s
                    , Toast.LENGTH_SHORT).show();
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {

            return false;
        }
    });
}
    private void initSlider(){


        TextSliderView textSliderView = new TextSliderView(this.getActivity());
        TextSliderView textSliderView1 = new TextSliderView(this.getActivity());
        TextSliderView textSliderView2 = new TextSliderView(this.getActivity());

        textSliderView
                .description("GXG")
                .image("https://img.alicdn.com/simba/img/TB1MfU4OVXXXXbcXVXXSutbFXXX.jpg");
        textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Toast.makeText(HomeFragment.this.getActivity(),"畅销品牌",Toast.LENGTH_LONG).show();
            }
        });
        textSliderView1.description("榴莲饼")
                .image("https://gw.alicdn.com/tps/TB153.MOVXXXXXMaVXXXXXXXXXX-520-280.jpg_.webp");
        textSliderView1.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Toast.makeText(HomeFragment.this.getActivity(),"美食",Toast.LENGTH_LONG).show();
            }
        });
        textSliderView2.description("JackJones")
                .image("https://img.alicdn.com/simba/img/TB1yso7OVXXXXb_XVXXSutbFXXX.jpg");
        textSliderView2.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {
                Toast.makeText(HomeFragment.this.getActivity(),"优质品牌",Toast.LENGTH_LONG).show();
            }
        });
        mSliderlayout.addSlider(textSliderView);
        mSliderlayout.addSlider(textSliderView1);
        mSliderlayout.addSlider(textSliderView2);

        mSliderlayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderlayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        mSliderlayout.setDuration(3000);

        mSliderlayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void initData() {
        mList.clear();
        AVQuery<AVObject> avQuery = new AVQuery<>("Product");
        avQuery.orderByDescending("createdAt");
        avQuery.include("owner");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    mList.addAll(list);
                    mRecyclerAdapter.notifyDataSetChanged();
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


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_logout) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
