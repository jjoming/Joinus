package com.example.joinus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.sdk.user.UserApiClient;

public class MyPage_Activity extends AppCompatActivity {

    ImageButton back;
    TextView logout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        logout = findViewById(R.id.textLogout2);
        //TODO 툴바 뒤로가기 버튼
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPage_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApiClient.getInstance().logout(error -> {
                    if (error != null) {
                        // 로그아웃 실패 처리
                        Toast.makeText(getApplicationContext(), "로그아웃 실패", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(MyPage_Activity.this, Login_Activity.class);
                        startActivity(intent);
                    }
                    return null;
                });
            }
        });
    }
}
