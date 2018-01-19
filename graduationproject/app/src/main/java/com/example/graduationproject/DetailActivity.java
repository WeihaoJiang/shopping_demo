package com.example.graduationproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private TextView mName;
    private TextView mDescription;
    private ImageView mImage;
    private TextView mPrice;
    private TextView mProduct;
    private Button add;
    private Context mContext;
    private List<AVObject> mList;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    Bundle args=new Bundle();

    byte[] mImageBytes = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
//         /* 获取manager */
//        manager = this.getSupportFragmentManager();
//        /* 创建事物 */
//        transaction = manager.beginTransaction();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.detail));

        mPrice= (TextView) findViewById(R.id.product_price);
        mName = (TextView) findViewById(R.id.name_detail);
        mDescription = (TextView) findViewById(R.id.description_detail);
        mImage = (ImageView) findViewById(R.id.image_detail);
        mProduct= (TextView) findViewById(R.id.productname);
        add= (Button) findViewById(R.id.add);

        final String goodsObjectId = getIntent().getStringExtra("goodsObjectId");
        final AVObject avObject = AVObject.createWithoutData("Product", goodsObjectId);
        avObject.fetchInBackground("owner", new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {

                mPrice.setText(avObject.getString("price"));
                mName.setText(avObject.getString("owner"));
                mDescription.setText(avObject.getString("description"));
                mProduct.setText(avObject.getString("title"));
                Picasso.with(DetailActivity.this).load(avObject.getAVFile("image") == null ? "www" : avObject.getAVFile("image").getUrl()).into(mImage);

            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte[] bytes = null;
                Drawable a = mImage.getDrawable();
                if(a != null){
                    Bitmap bitmap = drawableToBitmap(a);

                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    try {
                        out.flush();
                        out.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    bytes = out.toByteArray();

                    Log.i("---JWH---",bytes.toString());
                }
                AVObject shoppingcar = new AVObject("shoppingcar");
                shoppingcar.put("title", mProduct.getText().toString());
                shoppingcar.put("description", mDescription.getText().toString());
                shoppingcar.put("price", mPrice.getText().toString());
                shoppingcar.put("image", new AVFile("productPic", bytes));
                shoppingcar.put("owner",mName.getText().toString());
                shoppingcar.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            //mProgerss.setVisibility(View.GONE);
                            DetailActivity.this.finish();
                        } else {
                            //mProgerss.setVisibility(View.GONE);
                            Toast.makeText(DetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Toast.makeText(DetailActivity.this, "添加成功", Toast.LENGTH_SHORT).show();

                AVQuery.doCloudQueryInBackground("delete from Product where objectId="+"'"+goodsObjectId+"'", new CloudQueryCallback<AVCloudQueryResult>() {
                    @Override
                    public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                        // 如果 e 为空，说明保存成功
                    }
                });
                Intent i=new Intent(DetailActivity.this, MainpageActivity.class);
                i.putExtra("shoppingObjectId",shoppingcar.getObjectId());
                startActivity(i);


            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        AVAnalytics.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AVAnalytics.onResume(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 42 ) {
//            try {
//                //mImage.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData()));
//                mImageBytes = getBytes(this.getContentResolver().openInputStream(data.getData()));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        return byteArrayOutputStream.toByteArray();
    }


    public static Bitmap drawableToBitmap(Drawable drawable){

        int width = drawable.getIntrinsicWidth();

        int height = drawable.getIntrinsicHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height,

                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888

                        : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);

        drawable.setBounds(0,0,width,height);

        drawable.draw(canvas);

        return bitmap;



    }
}
