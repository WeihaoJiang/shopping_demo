package com.example.graduationproject.Fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.example.graduationproject.MainpageActivity;
import com.example.graduationproject.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class PublishFragment extends Fragment {
   // Context mBase;
    EditText mtitle,mprice,description;
    ImageView mImageViewSelect;
    TextView classify, user_message;
    EditText tele, e_mail, user_name;
    TextView user_mess;


    byte[] mImageBytes = null;
    Handler mHandler = new Handler();
   ProgressBar mProgerss;
   // ContentResolver cr = getContentResolver();

//    private ContentResolver getContentResolver() {
//        return mBase.getContentResolver();
//    }


    public PublishFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_publish, container, false);
        //classify = (TextView) view.findViewById(R.id.classify);
        mtitle = (EditText) view.findViewById(R.id.etpoduct_name);
        mprice = (EditText) view.findViewById(R.id.price);
        description = (EditText) view.findViewById(R.id.describe);
        user_message = (TextView) view.findViewById(R.id.fill);
        mImageViewSelect = (ImageView) view.findViewById(R.id.imageview_select_publish);
        mProgerss = (ProgressBar) view.findViewById(R.id.mProgess);
       // classify.setOnTouchListener(clistener);
        user_message.setOnTouchListener(ulistener);
        user_mess= (TextView) view.findViewById(R.id.user_mess);


        Button mButtonSelect = (Button) view.findViewById(R.id.button_select_publish);
        mButtonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 42);
            }
        });
        view.findViewById(R.id.button_submit_publish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(mtitle.getText().toString())) {
                    Toast.makeText(PublishFragment.this.getActivity(), "请输入标题", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("".equals(description.getText().toString())) {
                    Toast.makeText(PublishFragment.this.getActivity(), "请输入商品描述", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("".equals(mprice.getText().toString())) {
                    Toast.makeText(PublishFragment.this.getActivity(), "请输入金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mImageBytes == null) {
                    Toast.makeText(PublishFragment.this.getActivity(), "请选择一张照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                mProgerss.setVisibility(View.VISIBLE);

                AVObject product = new AVObject("Product");
                product.put("title", mtitle.getText().toString());
                product.put("description", description.getText().toString());
                product.put("price", mprice.getText().toString());
                product.put("image", new AVFile("productPic", mImageBytes));
                product.put("owner",user_mess.getText().toString());
                product.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            mProgerss.setVisibility(View.GONE);
                            PublishFragment.this.getActivity().finish();
                        } else {
                            mProgerss.setVisibility(View.GONE);
                            Toast.makeText(PublishFragment.this.getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Toast.makeText(PublishFragment.this.getActivity(), "发布成功", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(PublishFragment.this.getActivity(), MainpageActivity.class);
                startActivity(i);

            }

        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 42 && resultCode == -1) {
            try {
                mImageViewSelect.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), data.getData()));
                mImageBytes = getBytes(this.getActivity().getContentResolver().openInputStream(data.getData()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            onBackPressed();
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onPause() {
        super.onPause();
        AVAnalytics.onPause(this.getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        AVAnalytics.onResume(this.getActivity());
    }
//    View.OnTouchListener clistener = new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//            Intent intent = new Intent(getActivity(), clActivity.class);
//            startActivity(intent);
//
//            return true;
//        }
//    };

    View.OnTouchListener ulistener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            View fill = getLayoutInflater(null).inflate(R.layout.fill_layout, null);
            tele = (EditText) fill.findViewById(R.id.telephoneNumber);
            user_name = (EditText) fill.findViewById(R.id.etusername);
            e_mail = (EditText) fill.findViewById(R.id.e_mail);

            new AlertDialog.Builder(getActivity())
                    .setView(fill).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    tele.getText().toString();
                    user_mess.setText(tele.getText().toString() + "-" + e_mail.getText().toString() + "-" + user_name.getText().toString());

                }
            })
                    .setNegativeButton("取消", null)
                    .show();
            return true;
        }
    };

}