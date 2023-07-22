package com.example.joinus;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

public class GridAdapter extends BaseAdapter {

    Context context;
    int[] imgIds; // 이미지 버튼에 들어갈 그림의 아이디
    int[] imgViewIds; // 이미지뷰에 들어갈 그림의 아이디
    LayoutInflater inflater;
    File file;
    private int requestCode;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;

    public GridAdapter(Context context, int[] imgIds, int[] imgViewIds) {
        this.context = context;
        this.imgIds = imgIds;
        this.imgViewIds = imgViewIds;
    }

    @Override
    public int getCount() {
        return imgIds.length;
    }

    @Override
    public Object getItem(int position) {
        return imgIds[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item, null);
        }

        ImageButton imgBtn = convertView.findViewById(R.id.grid_btn);
        ImageView imgView = convertView.findViewById(R.id.imgView); // 이미지뷰 인플레이트

        File sdcard = Environment.getExternalStorageDirectory();
        file = new File(sdcard, "capture.jpg");

        imgBtn.setImageResource(imgIds[position]);
        //imgView.setImageResource(R.drawable.your_image_resource); // 이미지뷰에 원하는 이미지 설정
        imgView.setImageResource(imgViewIds[position]);

        // ImageButton에 OnClickListener 설정
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // todo : 카메라로 이동 후 확인 버튼 클릭시 체크표시로
                capture();
                handleImageButtonClick(position, imgView);
            }
        });

        return convertView;
    }


    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    private void handleImageButtonClick(int position, ImageView imgView) {

        imgView.setImageResource(R.drawable.icon_check_circle);
        // todo : 오늘의 목표 퍼센테이지 높이기

    }

    public void capture() {
        // 카메라 권한이 허용되었는지 확인합니다.
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // 카메라 앱을 실행합니다.
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (context instanceof Activity) {
                ((Activity) context).startActivityForResult(intent, requestCode);
            }
        } else {
            // 카메라 권한이 허용되지 않은 경우 권한을 요청합니다.
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    public void setCapturedImage(Intent data) {
        if (data != null && data.getExtras() != null) {
            //handleImageButtonClick(imgView);
        }
    }
}