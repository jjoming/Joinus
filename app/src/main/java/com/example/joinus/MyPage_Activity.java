package com.example.joinus;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MyPage_Activity extends AppCompatActivity {

    ImageButton back;
    ImageView[] imgViews;
    int[] imgID = {R.id.img1, R.id.img2, R.id.img3, R.id.img4, R.id.img5, R.id.img6, R.id.img7, R.id.img8};

    JoinusDBHelper dbHelper;
    SQLiteDatabase sqlDB;
    int stampNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        imgViews = new ImageView[imgID.length]; // 배열 초기화
        dbHelper = new JoinusDBHelper(this);

        // 인플레이팅
        for (int i = 0; i < imgID.length; i++) {
            imgViews[i] = findViewById(imgID[i]);
        }

        // 데이터베이스에서 스탬프 개수 불러와서 UI 업데이트
        sqlDB = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT " + TableInfo_user.TABLE_3_COLUMN_NAME_STAMP + " FROM " + TableInfo_user.TABLE_3_NAME,null);
        while (cursor.moveToNext()) {
            stampNum = cursor.getInt(0);
        }

        for (int i = 0; i < stampNum; i++) {
            imgViews[i].setImageResource(R.drawable.stamp_true);
        }

        //TODO 툴바 뒤로가기 버튼
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // 뒤로 가기 버튼 처리
            }
        });

    }

}
