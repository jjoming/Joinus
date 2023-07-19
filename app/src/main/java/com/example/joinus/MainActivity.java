package com.example.joinus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.kakao.sdk.common.util.Utility;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.util.Log;
public class MainActivity extends AppCompatActivity {
    ImageButton myPage;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        <-- 키 해시 구하기 -->
        Log.d("getKeyHash", ""+getKeyHash(MainActivity.this));

        int[] imgBtnIds = {R.drawable.btn_ride_bike, R.drawable.btn_plastic_label, R.drawable.btn_transport,
                R.drawable.btn_power_off, R.drawable.btn_phone, R.drawable.btn_use_tumbler, R.drawable.btn_empty_mail,
                R.drawable.btn_use_bag, R.drawable.btn_laundry, R.drawable.btn_food};

        myPage = findViewById(R.id.myPage);
        myPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyPage_Activity.class);
                startActivity(intent);
            }
        });

        // gridView에 어댑터 설정
        gridView = findViewById(R.id.gridView);
        GridAdapter adapter = new GridAdapter(MainActivity.this, imgBtnIds);
        gridView.setAdapter(adapter);
    }

    public static String getKeyHash(final Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            if(packageInfo == null)
                return null;
            for (Signature signature : packageInfo.signatures) {
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    return android.util.Base64.encodeToString(md.digest(), android.util.Base64.NO_WRAP);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}