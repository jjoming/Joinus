package com.example.joinus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.sdk.user.UserApiClient;

public class Login_Activity extends AppCompatActivity {

    ImageButton btnKakao;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnKakao = findViewById(R.id.btn_kakao);
        btnKakao.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(Login_Activity.this)){
                    login();
                }
                else{
                    accountLogin();
                }
            }
        });
    }

    public void login(){
        String TAG = "login()";
        UserApiClient.getInstance().loginWithKakaoTalk(Login_Activity.this, (oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "카카오 로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "카카오 로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                getUserInfo();
            }
            return null;
        });
    }

    public void accountLogin(){
        String TAG = "accountLogin()";
        UserApiClient.getInstance().loginWithKakaoAccount(Login_Activity.this, (oAuthToken, error) -> {
            if (error != null) {
                Log.e(TAG, "카카오 로그인 실패", error);
            } else if (oAuthToken != null) {
                Log.i(TAG, "카카오 로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                getUserInfo();
            }
            return null;
        });
    }

    public void getUserInfo(){
        String TAG = "getUserInfo()";
        UserApiClient.getInstance().me((user, meError) -> {
            if (meError != null) {
                Log.e(TAG, "사용자 정보 요청 실패", meError);
            } else {
                Log.i(TAG, "로그인 완료");
                Log.i(TAG, "사용자 정보 요청 성공" +
                        "\n회원번호: " + user.getId() +
                        "\n이메일: " + user.getKakaoAccount().getEmail());
                // 로그인 성공 후 MainActivity로 전환하는 코드 추가
                Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                startActivity(intent);
                finish(); // 현재 액티비티를 종료하여 뒤로 가기 버튼으로 다시 돌아오지 않도록 합니다.
            }
            return null;
        });
    }

}
