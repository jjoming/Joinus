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

public class MainActivity extends AppCompatActivity {
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    ImageButton myPage;
    GridView gridView;
    TextView user_name;
    JoinusDBHelper dbHelper;
    SQLiteDatabase sqlDB;
    String userN;

    ProgressBar pbar;
    GridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        int[] imgBtnIds = {R.drawable.btn_ride_bike, R.drawable.btn_plastic_label, R.drawable.btn_transport,
                R.drawable.btn_power_off, R.drawable.btn_phone, R.drawable.btn_use_tumbler, R.drawable.btn_empty_mail,
                R.drawable.btn_use_bag, R.drawable.btn_laundry, R.drawable.btn_food};
        int[] imgViewIds = {R.drawable.icon_check_circle_outline, R.drawable.icon_check_circle_outline, R.drawable.icon_check_circle_outline,
                R.drawable.icon_check_circle_outline, R.drawable.icon_check_circle_outline, R.drawable.icon_check_circle_outline,
                R.drawable.icon_check_circle_outline, R.drawable.icon_check_circle_outline, R.drawable.icon_check_circle_outline,
                R.drawable.icon_check_circle_outline};

        ProgressBar pbar;
        pbar = findViewById(R.id.progressbar);
        pbar.setProgress(0);

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
        sqlDB.close();

        // gridView에 어댑터 설정
        gridView = findViewById(R.id.gridView);
        adapter = new GridAdapter(MainActivity.this, imgBtnIds, imgViewIds);
        gridView.setAdapter(adapter);
        int requestCode = 101;
        adapter.setRequestCode(requestCode);
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