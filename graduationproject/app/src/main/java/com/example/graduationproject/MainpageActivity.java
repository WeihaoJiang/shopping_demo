package com.example.graduationproject;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.graduationproject.Fragment.HomeFragment;
import com.example.graduationproject.Fragment.MeFragment;
import com.example.graduationproject.Fragment.PublishFragment;
import com.example.graduationproject.Fragment.ShoppingcartFragment;

import java.util.ArrayList;
import java.util.List;

public class MainpageActivity extends AppCompatActivity {
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private LayoutInflater mInflater;
    private FragmentTabHost mTabhost;
    private List<Tab>mTabs=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        initTab();


//        Intent intent=getIntent();
//        if (intent!=null){
//            String name=intent.getStringExtra("name");
//            String price=intent.getStringExtra("price");
//            String description=intent.getStringExtra("description");
//            String title=intent.getStringExtra("title");
//            Bundle bundle=new Bundle();
//            bundle.putString("name",name);
//            bundle.putString("price",price);
//            bundle.putString("description",description);
//            bundle.putString("title",title);
//             /* 获取manager */
//            manager = this.getSupportFragmentManager();
//        /* 创建事物 */
//            transaction = manager.beginTransaction();
//            ShoppingcartFragment shoppingcartFragment=new ShoppingcartFragment();
//             /* 把Fragment添加到对应的位置 */
//            transaction.add(R.id.id_elv_goods, shoppingcartFragment);
//        /* 提交事物 */
//            transaction.commit();
//        }
    }

    private void initTab() {
        Tab tab_home=new Tab(R.string.homefragment,R.drawable.home0_selector, HomeFragment.class);
        Tab tab_publish=new Tab(R.string.publishfragment,R.drawable.publish_selector, PublishFragment.class);
        Tab tab_shop=new Tab(R.string.shopingfragment,R.drawable.shopping_selector, ShoppingcartFragment.class);
        Tab tab_me=new Tab(R.string.mefragment,R.drawable.me_selector, MeFragment.class);
        mTabs.add(tab_home);
        mTabs.add(tab_publish);
        mTabs.add(tab_shop);
        mTabs.add(tab_me);
        mTabhost= (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabhost.setup(this,getSupportFragmentManager(),R.id.realtabcontent);
        mInflater=LayoutInflater.from(this);
        for (Tab tab:mTabs){
            TabHost.TabSpec tabspec=mTabhost.newTabSpec(getString(tab.getTitle()));
            tabspec.setIndicator(build(tab));
            mTabhost.addTab(tabspec,tab.getFragment(),null);
        }
        mTabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
    }

    private View build(Tab tab) {
        View view=mInflater.inflate(R.layout.tab_indicator,null);
        ImageView img= (ImageView) view.findViewById(R.id.icon_tab);
        TextView text= (TextView) view.findViewById(R.id.txt_indcator);
        img.setBackgroundResource(tab.getIcon());
        text.setText(tab.getTitle());
        return view;
    }
}
