package com.example.graduationproject;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 姜维昊 on 2017/4/21.
 */
public class ShoppingListAdapter extends BaseAdapter {
    public boolean flag = false;
    public Context mContext;
    public List<AVObject> mList;
    public List<Integer> sum = new ArrayList<>();
    public List<String> ID = new ArrayList<>();
    public List<String> Title = new ArrayList<>();
    public int price;
    public int totalprice = 0;
    public String avid,title0;
    public CheckBox item_chect;

    public ShoppingListAdapter(Context mContext, List<AVObject> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_shopping_main, null);

            //初始化全部控件
            holder.mTitle = (TextView) view.findViewById(R.id.title_item_main);
            holder.mName = (TextView) view.findViewById(R.id.name_item_main);
            holder.mTitle = (TextView) view.findViewById(R.id.title_item_main);
            holder.mPrice = (TextView) view.findViewById(R.id.price_item_main);
            holder.mPicture = (ImageView) view.findViewById(R.id.picture_item_main);
            holder.mItem = (CardView) view.findViewById(R.id.item_main);
            item_chect = (CheckBox) view.findViewById(R.id.id_img_check);
            holder.mPrice_tv = (TextView) view.findViewById(R.id.id_tv_price);
            //radioButton初始默认为false
            item_chect.setChecked(false);


            holder.mTitle.setText((CharSequence) mList.get(i).get("title"));
            holder.mPrice.setText((CharSequence) mList.get(i).get("price"));
            holder.mName.setText((CharSequence) mList.get(i).get("owner"));
            Picasso.with(mContext)
                    .load(mList.get(i).getAVFile("image") == null ? "www" : mList.get(i).getAVFile("image").getUrl())
                    .transform(new RoundedTransformation(9, 0))
                    .into(holder.mPicture);
            avid = mList.get(i).getObjectId();
            price = Integer.parseInt((String) mList.get(i).get("price"));
            title0= mList.get(i).get("title").toString();

            item_chect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    if (item_chect.isClickable()) {
                        //totalprice += price;
                        sum.add(price);
                        ID.add(avid);
                        Title.add(title0);
                    } else {
                        //totalprice -= price;
                        sum.remove(price);
                        ID.remove(avid);
                        Title.remove(title0);
                    }
               //  item_chect.setChecked(!item_chect.isChecked());
                }
            });
        }


        return view;
    }

    class ViewHolder {
        private TextView mName;
        private TextView mPrice;
        private TextView mTitle;
        private CardView mItem;
        private ImageView mPicture;
        public TextView mPrice_tv;//总价
        public Button mPay_btn;//结算

//        public ViewHolder(View itemView) {
//            mName = (TextView) itemView.findViewById(R.id.name_item_main);
//            mTitle = (TextView) itemView.findViewById(R.id.title_item_main);
//            mPrice = (TextView) itemView.findViewById(R.id.price_item_main);
//            mPicture = (ImageView) itemView.findViewById(R.id.picture_item_main);
//            mItem = (CardView) itemView.findViewById(R.id.item_main);
//            item_chect = (RadioButton) itemView.findViewById(R.id.id_img_check);
//            mPrice_tv = (TextView) itemView.findViewById(R.id.id_tv_price);
////        }
    }

}
