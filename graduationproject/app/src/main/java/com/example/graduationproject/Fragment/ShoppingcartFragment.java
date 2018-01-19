package com.example.graduationproject.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.graduationproject.R;
import com.example.graduationproject.SHoppingCart.Adapter;
import com.example.graduationproject.SHoppingCart.ShoppingCartBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingcartFragment extends Fragment implements View.OnClickListener, Adapter.CheckInterface, Adapter.ModifyCountInterface {

    public TextView tv_title, tv_settlement, tv_show_price;
    private CheckBox ck_all;
    private ListView list_shopping_cart;
    private Adapter shoppingCartAdapter;
    private TextView tv_edit;
    private ImageView iv_show_pic;
    private boolean flag = false;
    private List<ShoppingCartBean> shoppingCartBeanList = new ArrayList<>();
    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量

    private List<AVObject> mList = new ArrayList<>();
    //    AVQuery<AVObject> avQuery = new AVQuery<>("shoppingcar");
//    AVQuery<AVObject> avQuery1 = new AVQuery<>("Product");
    private String avid;

    public ShoppingcartFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_shopping_cart_activity, container, false);
        initView(view);
        initData();
        shoppingCartAdapter.notifyDataSetChanged();
        return view;
    }

    void initDatabegin() {

        mList.clear();
        AVQuery<AVObject> avQuery = new AVQuery<>("shoppingcar");
        avQuery.orderByDescending("createdAt");
        avQuery.include("owner");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    mList.addAll(list);
                    shoppingCartAdapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initData() {
//        initDatabegin();
        Log.i("-----------", mList.size() + "=====================");
        for (int i = 0; i < mList.size(); i++) {
            ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
            shoppingCartBean.setShoppingName((String) mList.get(i).get("title"));
            shoppingCartBean.setPrice(Double.valueOf(String.valueOf(mList.get(i).get("price"))));

            shoppingCartBean.setAvdescription(String.valueOf(mList.get(i).get("description")));
            String owner = (String) mList.get(i).get("owner");
            String[] owner_ = owner.split("-");
            Log.i("-----------jwh", owner);
            shoppingCartBean.setOwner(owner_[0]);
            Log.i("--gtb--", mList.get(i).getAVFile("image").getUrl());
//            Picasso.with(getContext())
//                    .load(mList.get(i).getAVFile("image") == null ? "www" : mList.get(i).getAVFile("image").getUrl())
//                    .transform(new RoundedTransformation(9, 0))
//
//                    .into(iv_show_pic);

            shoppingCartBean.setUrl(mList.get(i).getAVFile("image").getUrl());

            avid = mList.get(i).getObjectId();

            shoppingCartBeanList.add(shoppingCartBean);
        }
    }

    private void initView(View view) {
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText("购物车");
        iv_show_pic = (ImageView) view.findViewById(R.id.iv_show_pic);
        list_shopping_cart = (ListView) view.findViewById(R.id.list_shopping_cart);
        ck_all = (CheckBox) view.findViewById(R.id.ck_all);
        ck_all.setOnClickListener(this);
        tv_show_price = (TextView) view.findViewById(R.id.tv_show_price);
        tv_settlement = (TextView) view.findViewById(R.id.tv_settlement);
        tv_settlement.setOnClickListener(this);
        tv_edit = (TextView) view.findViewById(R.id.tv_edit);
        tv_edit.setOnClickListener(this);
        shoppingCartAdapter = new Adapter(ShoppingcartFragment.this.getActivity(), mList);
        shoppingCartAdapter.setCheckInterface(this);
        shoppingCartAdapter.setModifyCountInterface(this);
        list_shopping_cart.setAdapter(shoppingCartAdapter);
        shoppingCartAdapter.setShoppingCartBeanList(shoppingCartBeanList);
    }


    @Override
    public void onResume() {
        super.onResume();
        AVAnalytics.onResume(this.getActivity());
        initDatabegin();
    }

    @Override
    public void onPause() {
        super.onPause();
        AVAnalytics.onPause(this.getActivity());
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            //全选按钮
            case R.id.ck_all:
                if (shoppingCartBeanList.size() != 0) {
                    if (ck_all.isChecked()) {
                        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
                            shoppingCartBeanList.get(i).setChoosed(true);
                        }
                        shoppingCartAdapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
                            shoppingCartBeanList.get(i).setChoosed(false);
                        }
                        shoppingCartAdapter.notifyDataSetChanged();
                    }
                }
                statistics();
                break;
            case R.id.tv_edit:
                flag = !flag;
                if (flag) {
                    tv_edit.setText("完成");
                    shoppingCartAdapter.isShow(false);
                } else {
                    tv_edit.setText("编辑");
                    shoppingCartAdapter.isShow(true);
                }
                break;
            case R.id.tv_settlement:
                Toast.makeText(this.getActivity(), "结算", Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * 统计操作
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作
     * 3.给底部的textView进行数据填充
     */
    public void statistics() {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
            ShoppingCartBean shoppingCartBean = shoppingCartBeanList.get(i);
            if (shoppingCartBean.isChoosed()) {
                totalPrice += shoppingCartBean.getPrice();
            }
        }
        tv_show_price.setText("合计:" + totalPrice);
        tv_settlement.setText("结算(" + totalCount + ")");
    }

    /**
     * 遍历list集合
     *
     * @return
     */
    private boolean isAllCheck() {

        for (ShoppingCartBean group : shoppingCartBeanList) {
            if (!group.isChoosed())
                return false;
        }
        return true;
    }


    @Override
    public void checkGroup(int position, boolean isChecked) {
        shoppingCartBeanList.get(position).setChoosed(isChecked);

        if (isAllCheck())
            ck_all.setChecked(true);
        else
            ck_all.setChecked(false);

        shoppingCartAdapter.notifyDataSetChanged();
        statistics();
    }

    @Override
    public void childDelete(int position) {
        shoppingCartBeanList.remove(position);
        shoppingCartAdapter.notifyDataSetChanged();
        statistics();
    }
}
