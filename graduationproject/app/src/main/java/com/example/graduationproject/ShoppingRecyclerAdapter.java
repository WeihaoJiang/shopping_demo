package com.example.graduationproject;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 姜维昊 on 2017/4/15.
 */
public class ShoppingRecyclerAdapter extends RecyclerView.Adapter<ShoppingRecyclerAdapter.MainViewHolder2> {
    public Context mContext;
    public List<AVObject> mList;
    public List<Integer> sum = new ArrayList<>();
    public List<String> ID = new ArrayList<>();
    public int price;
    public RadioButton item_chect;
    public String avid;

    public ShoppingRecyclerAdapter(Context mContext, List<AVObject> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    @Override
    public MainViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder2(LayoutInflater.from(mContext).inflate(R.layout.item_shopping_main, parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder2 holder, final int position) {
        holder.mTitle.setText((CharSequence) mList.get(position).get("title"));
        holder.mPrice.setText((CharSequence) mList.get(position).get("price"));
        holder.mName.setText((CharSequence) mList.get(position).get("owner"));
        Picasso.with(mContext).load(mList.get(position).getAVFile("image") == null ? "www" : mList.get(position).getAVFile("image").getUrl()).transform(new RoundedTransformation(9, 0)).into(holder.mPicture);
        avid = mList.get(position).getObjectId();
        price = Integer.parseInt(holder.mPrice.getText().toString());
        holder.mItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_chect = (RadioButton) view.findViewById(R.id.id_img_check);
                item_chect.setChecked(!item_chect.isChecked());
                if (item_chect.isClickable()) {
                    sum.add(price);
                    ID.add(avid);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    class MainViewHolder2 extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mPrice;
        private TextView mTitle;
        private CardView mItem;
        private ImageView mPicture;
        public TextView mPrice_tv;//总价
        public Button mPay_btn;//结算

        public MainViewHolder2(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name_item_main);
            mTitle = (TextView) itemView.findViewById(R.id.title_item_main);
            mPrice = (TextView) itemView.findViewById(R.id.price_item_main);
            mPicture = (ImageView) itemView.findViewById(R.id.picture_item_main);
            mItem = (CardView) itemView.findViewById(R.id.item_main);
            item_chect = (RadioButton) itemView.findViewById(R.id.id_img_check);
            mPrice_tv = (TextView) itemView.findViewById(R.id.id_tv_price);
        }
    }
}
