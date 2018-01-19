package com.example.graduationproject.adaper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.graduationproject.R;
import com.example.graduationproject.RoundedTransformation;
import com.example.graduationproject.bean.ShoppingCartBean;
import com.example.graduationproject.bean.ShoppingCartBiz;
import com.example.graduationproject.bean.StoreInfo;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 姜维昊 on 2017/4/5.
 */
public class GoodsAdapter extends BaseExpandableListAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private Context mcontext;
    //父标题list
    private List<StoreInfo> shopInfos;
    private Map<Integer, List<ShoppingCartBean>> mDatass= new HashMap<>();
    private List<AVObject> mList = new ArrayList<>();
    String mPrice,mProduct,mName,mDescription;
    RequestCreator drawable;
    Intent intent=new Intent();
    String shopgood=intent.getStringExtra("shoppingObjectId");
    AVObject avObject = AVObject.createWithoutData("shoppingcar", shopgood);
    public GoodsAdapter(Context context, Map Datass, List<AVObject> List ) {
        this.mcontext = context;
        this.mDatass=Datass;
        this.mList=List;
        this.shopInfos = shopInfos;
        mInflater=LayoutInflater.from(mcontext);
//        avObject.fetchInBackground("owner", new GetCallback<AVObject>() {
//            @Override
//            public void done(AVObject avObject, AVException e) {
//
//                mPrice=avObject.getString("price");
//                mName=avObject.getString("owner");
//                mDescription=avObject.getString("description");
//                mProduct=avObject.getString("title");
//                //Picasso.with(DetailActivity.this).load(avObject.getAVFile("image") == null ? "www" : avObject.getAVFile("image").getUrl()).into(mImage);
//
//            }
//        });

    }


//获取父标题的组数据的数量
    @Override
    public int getGroupCount() {

        return mList.size();
    }
    //指定子标题的组数据的数量
    @Override
    public int getChildrenCount(int i) {
        Integer[] keys = new Integer[mDatass.size()];
        mDatass.keySet().toArray(keys);
        int key= keys[i];
        int size=mDatass.get(key).size();
        return size;
    }

    @Override
    public Object getGroup(int i) {
        return shopInfos.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        mProduct= (String) mList.get(i1).get("title");
        mPrice=(mList.get(i1).get("price") == null ? "￥" : "￥ " + mList.get(i1).get("price"));
        mName=mList.get(i1).getAVUser("owner") == null ? "" : mList.get(i1).getAVUser("owner").getUsername();
        drawable=Picasso.with(mcontext).load(mList.get(i1).getAVFile("image") == null ? "www" : mList.get(i1).getAVFile("image").getUrl()).transform(new RoundedTransformation(9, 0));
//        StoreInfo shopInfo= shopInfos.get(i);
//        List<ShoppingCartBean> ShoppingCartBean = mDatass.get(shopInfo.getShopId());
//        if(ShoppingCartBean == null)
//            return null;
        return mList.get(i1);
    }

    @Override
    public long getGroupId(int i) {

        return i;
    }

    @Override
    public long getChildId(int i, int i1) {

        return i1;
    }

    @Override
    public boolean hasStableIds() {

        return true;
    }
//父组选项的外观
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ChildViewHolder viewHolder=null;

        if(convertView==null){
            viewHolder=new ChildViewHolder();
            convertView=mInflater.inflate(R.layout.item_goods_parent,null);
            convertView.setTag(viewHolder);
        }else{
            //viewHolder= (ChildViewHolder) convertView.getTag();
        }

        TextView tv_storename= (TextView) convertView.findViewById(R.id.id_tv_storename);
        //viewHolder.img_check.setOnClickListener(listener);
        ImageView img_check= (ImageView) convertView.findViewById(R.id.id_img_storercheck);
        StoreInfo shopInfo= shopInfos.get(groupPosition);
        //List<ShoppingCartBean> beanList=mDatass.get(shopInfo.getShopId());
        tv_storename.setText(mName);

       // boolean childen= ShoppingCartBiz.checkChilden(shopInfo,mDatass);

        if(shopInfo.isStoreCheckStatus()){
            ShoppingCartBiz.checkItem(true,img_check);
        }else
            ShoppingCartBiz.checkItem(false,img_check);

        img_check.setTag(shopInfo);
        img_check.setOnClickListener(listener);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        StoreInfo shopInfo= shopInfos.get(groupPosition);
        int key=shopInfo.getShopId();
        ChildViewHolder viewHolder=null;
        List<ShoppingCartBean> goods=mDatass.get(key);
        if(convertView==null){
            viewHolder=new ChildViewHolder();
            convertView=mInflater.inflate(R.layout.item_goods_children,null);
            ButterKnife.inject(viewHolder,convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ChildViewHolder) convertView.getTag();
        }
        ShoppingCartBean good = goods.get(childPosition);
       // if(good != null) {
            //设置图片等信息
            drawable.into(viewHolder.goodsImg);
            viewHolder.goodsName.setText(mProduct);
            viewHolder.goodsSomeInfo.setText(mDescription);
            //viewHolder.goodsAmount.setText("X" + good.getAmount());
            viewHolder.goodsPrices.setText(mPrice);
            //viewHolder.goodsHuibi.setText("" + good.getHuiBi());
            //viewHolder.goodsnum_btn.setText("" + good.getAmount());
            ShoppingCartBiz.checkItem(good.isGoodsCheckStatus(), viewHolder.goodsCheck);
            RelativeLayout editLayout = (RelativeLayout) convertView.findViewById(R.id.id_rl_editgoods);
            LinearLayout goodsLayout = (LinearLayout) convertView.findViewById(R.id.id_lv_goodsinfo);
            ShoppingCartBiz.checkEdit(good.isGoodsEditShow(), editLayout, goodsLayout);
            viewHolder.minus_img.setOnClickListener(listener);
            viewHolder.add_img.setOnClickListener(listener);
            viewHolder.add_img.setTag(good);
            viewHolder.goodsCheck.setOnClickListener(listener);
            viewHolder.goodsCheck.setTag(good);
            viewHolder.minus_img.setTag(good);
            // viewHolder.goodsCheck.setTag(shopInfo);


       // }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //全选点击事件
                case R.id.id_img_allselect:
                    ImageView imageView= (ImageView) v.findViewById(R.id.id_img_allselect);
                    //selectAll(imageView);
                    ShoppingCartBiz.selectAll(mDatass,shopInfos,imageView);
                    notifyDataSetChanged();
                    break;
                //编辑点击事件
                case R.id.id_title_right:
                    TextView editText= (TextView) v.findViewById(R.id.id_title_right);
                    ShoppingCartBiz.showEdit(mDatass,editText);
                    notifyDataSetChanged();
                    break;
                //减商品
                case R.id.id_img_minus:
                    ShoppingCartBean goodm= (ShoppingCartBean) v.getTag();
                    ImageView minus_img= (ImageView) v.findViewById(R.id.id_img_minus);
                    ShoppingCartBiz.editGoodsInfo(false,goodm,minus_img);
                    notifyDataSetChanged();
                    break;
                //加商品
                case R.id.id_img_add:
                    ShoppingCartBean goods= (ShoppingCartBean) v.getTag();
                    ImageView add_img= (ImageView) v.findViewById(R.id.id_img_add);
                    ShoppingCartBiz.editGoodsInfo(true,goods,add_img);
                    notifyDataSetChanged();
                    break;
                //商品单选
                case R.id.id_img_check:
                    //ShopInfo shopInfo= (ShopInfo) v.getTag(2);
                    ShoppingCartBean cartBean= (ShoppingCartBean) v.getTag();
                    ShoppingCartBiz.checkOne(cartBean,shopInfos);
                    notifyDataSetChanged();
                    break;
                //商店选择点击事件
                case R.id.id_img_storercheck:
                    StoreInfo info= (StoreInfo) v.getTag();
                    ShoppingCartBiz.checkStore(mDatass,info);
                    notifyDataSetChanged();
                    break;
                case R.id.id_btn_pay:

            }

        }


    };
    /**
     * 更新商品选中状态
     * @param good
     * @param type
     */
    private void updataGoodsCheckStatus(ShoppingCartBean good,boolean type){

    }

    /**
     * 全选点击事件
     */
    private void selectAll(ImageView view) {
        Log.i("smile","执行了");
        ShoppingCartBiz.selectAll(mDatass,shopInfos,view);

    }
    private void initData() {
        mList.clear();
        AVQuery<AVObject> avQuery = new AVQuery<>("shoppingcar");
        avQuery.orderByDescending("createdAt");
        avQuery.include("owner");
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    mList.addAll(list);
                    //mExpandableListView.deferNotifyDataSetChanged();
                    //mRecyclerAdapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
    public View.OnClickListener getAdapterListener() {
        return listener;
    }







    class ChildViewHolder {

        @InjectView(R.id.id_img_goodsimg)
        ImageView goodsImg;
        @InjectView(R.id.id_tv_goods_name)
        TextView goodsName;
        @InjectView(R.id.id_tv_someinfo)
        TextView goodsSomeInfo;
        @InjectView(R.id.id_tv_amount)
        TextView goodsAmount;
        @InjectView(R.id.id_tv_goods_price)
        TextView goodsPrices;
        @InjectView(R.id.id_tv_goods_huibi)
        TextView goodsHuibi;
        @InjectView(R.id.id_img_check)
        ImageView goodsCheck;
        //编辑视图
        @InjectView(R.id.id_img_minus)
        ImageView minus_img;//减
        @InjectView(R.id.id_img_add)
        ImageView add_img;//加
        @InjectView(R.id.id__btn_goodsnum)
        Button goodsnum_btn;//总数量

    }
}
