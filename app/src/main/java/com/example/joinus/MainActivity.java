package com.example.joinus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnButtonClickListener {
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    ImageButton myPage;
    GridView gridView;
    TextView user_name;
    JoinusDBHelper dbHelper;
    SQLiteDatabase sqlDB;
    String userN, per = "0";

    GridAdapter adapter;
    TextView percent;
    ProgressBar progressbar;
    int[] imgViewIds;
    int goal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new JoinusDBHelper(this);
        int[] imgBtnIds = {R.drawable.btn_ride_bike, R.drawable.btn_plastic_label, R.drawable.btn_transport,
                R.drawable.btn_power_off, R.drawable.btn_phone, R.drawable.btn_use_tumbler, R.drawable.btn_empty_mail,
                R.drawable.btn_use_bag, R.drawable.btn_laundry, R.drawable.btn_food};
        int[] imgViewIds = {R.drawable.icon_check_circle_outline, R.drawable.icon_check_circle_outline, R.drawable.icon_check_circle_outline,
                R.drawable.icon_check_circle_outline, R.drawable.icon_check_circle_outline, R.drawable.icon_check_circle_outline,
                R.drawable.icon_check_circle_outline, R.drawable.icon_check_circle_outline, R.drawable.icon_check_circle_outline,
                R.drawable.icon_check_circle_outline};
        String[] imgViewSQL = {TableInfo_user.TABLE_2_COLUMN_NAME_BICYCLE,
                TableInfo_user.TABLE_2_COLUMN_NAME_LABEL,
                TableInfo_user.TABLE_2_COLUMN_NAME_BUS,
                TableInfo_user.TABLE_2_COLUMN_NAME_OFF,
                TableInfo_user.TABLE_2_COLUMN_NAME_SMARTPHONE,
                TableInfo_user.TABLE_2_COLUMN_NAME_TUMBLER,
                TableInfo_user.TABLE_2_COLUMN_NAME_MAIL,
                TableInfo_user.TABLE_2_COLUMN_NAME_BASKET,
                TableInfo_user.TABLE_2_COLUMN_NAME_LAUNDRY,
                TableInfo_user.TABLE_2_COLUMN_NAME_FOOD};

        myPage = findViewById(R.id.myPage);
        myPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyPage_Activity.class);
                startActivity(intent);
            }
        });

        user_name = findViewById(R.id.user_name);
        Intent inIntent = getIntent();
        //이름 데이터베이스에서 꺼내 표시
        dbHelper = new JoinusDBHelper(this);

        sqlDB = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT " + TableInfo_user.TABLE_1_COLUMN_NAME_NAME + " FROM " + TableInfo_user.TABLE_1_NAME,null);
        while (cursor.moveToNext()) {
            userN = cursor.getString(0);
        }
        user_name.setText(userN + "님");

        cursor.close();

        percent = findViewById(R.id.percent);
        progressbar = findViewById(R.id.progressbar);
        cursor = sqlDB.rawQuery("SELECT " + TableInfo_user.TABLE_2_COLUMN_NAME_GOAL + " FROM " + TableInfo_user.TABLE_2_NAME,null);
        while (cursor.moveToNext()) {
            per = cursor.getString(0);
        }
        percent.setText(per + "%");
        goal = Integer.parseInt(per);
        sqlDB.close();

        // gridView에 어댑터 설정
        gridView = findViewById(R.id.gridView);
        adapter = new GridAdapter(MainActivity.this, imgBtnIds, imgViewIds, imgViewSQL);
        adapter.setOnButtonClickListener(this); // 인터페이스 리스너 설정
        gridView.setAdapter(adapter);
        int requestCode = 101;
        adapter.setRequestCode(requestCode);
    }

    // 인터페이스 메서드 구현
    @Override
    public void onButtonClick(int position) {
        adapter.handleImageButtonClick(position, R.drawable.icon_check_circle);
        updatePercentage(); // 퍼센테이지 업데이트 메서드 호출
    }

    // 퍼센테이지 업데이트 메서드
    private void updatePercentage() {
        goal += 10;
        if (goal == 100) {
            percent.setText(goal + "%");
            progressbar.setProgress(goal);
            sqlDB = dbHelper.getWritableDatabase();
            sqlDB.execSQL("INSERT INTO " + TableInfo_user.TABLE_3_NAME + " (" + TableInfo_user.TABLE_3_COLUMN_NAME_STAMP + ") VALUES (1);");
            sqlDB.close();
        }
        else if (goal > 100) {

        }
        else {
            percent.setText(goal + "%");
            progressbar.setProgress(goal);
        }
    }

    // 이미지 캡처 결과를 받아오는 메서드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            // 캡처된 이미지 정보를 GridAdapter로 전달
            adapter.setCapturedImage(data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                adapter.capture();
            } else {
                Toast.makeText(this, "카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}