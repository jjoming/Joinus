package com.example.joinus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageButton myPage;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}