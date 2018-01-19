package com.example.graduationproject;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 731775721 on 2017/3/5.
 */

public class clActivity extends ListActivity {
    /**
     * 卖家先选择商品种类
     *
     * @param savedInstanceState
     */
    TextView cla;//分类所显示的信息
    String[] arr = {"car", "people", "man", "woman"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, arr);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Toast.makeText(this, arr[position], Toast.LENGTH_SHORT).show();
    }
}
