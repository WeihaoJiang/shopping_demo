package com.example.graduationproject.SHoppingCart;

import com.avos.avoscloud.AVObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AYD on 2016/11/22.
 * <p>
 * 购物车
 */
public class ShoppingCartBean {

    private int id;
    //private String imageUrl;
    private String shoppingName;
    private String avdescription;
    private double price;
    public boolean isChoosed;
    public String owner;
    public byte[] bytes;
    public boolean isCheck = false;
    private List<AVObject> mList = new ArrayList<>();
    int i=mList.size();
    public String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ShoppingCartBean() {
//        mList.clear();
//        AVQuery<AVObject> avQuery = new AVQuery<>("shoppingcar");
////        avQuery.orderByDescending("createdAt");
////        avQuery.include("owner");
//        avQuery.findInBackground(new FindCallback<AVObject>() {
//            @Override
//            public void done(List<AVObject> list, AVException e) {
//                if (e == null) {
//                    mList.addAll(list);
//                    //GoodsAdapter
//                    //mExpandableListView.deferNotifyDataSetChanged();
//                    //mListAdapter.notifyDataSetChanged();
//                } else {
//                    e.printStackTrace();
//                }
//            }
//
//        });
//        int i=mList.size();
//        shoppingName= (String) mList.get(i).get("title");
//        avdescription= (String) mList.get(i).get("description");
//        owner= (String) mList.get(i).get("owner");
//        //bytes= mList.get(i).getAVFile("image");
//        price = Integer.parseInt((String) mList.get(i).get("price"));
    }

    public ShoppingCartBean(byte[] bytes, int id, String shoppingName, String avdescription, double price, boolean isChoosed, String owner, boolean isCheck, List<AVObject> mList) {
        this.bytes = bytes;
        this.id = id;
        this.shoppingName = shoppingName;
        this.avdescription = avdescription;
        this.price = price;
        this.isChoosed = isChoosed;
        this.owner = owner;
        this.isCheck = isCheck;
        this.mList = mList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShoppingName() {
        return shoppingName;
    }

    public void setShoppingName(String shoppingName) {
        this.shoppingName = shoppingName;
    }

    public String getAvdescription() {
        return avdescription;
    }

    public void setAvdescription(String avdescription) {
        this.avdescription = avdescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public List<AVObject> getmList() {
        return mList;
    }

    public void setmList(List<AVObject> mList) {
        this.mList = mList;
    }
}
