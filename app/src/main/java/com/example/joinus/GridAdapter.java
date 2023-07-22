package com.example.joinus;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
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
    String[] imgViewSQL;
    LayoutInflater inflater;
    File file;
    private int requestCode;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;

    JoinusDBHelper dbHelper;
    SQLiteDatabase sqlDB;

    public GridAdapter(Context context, int[] imgIds, String[] imgViewSQL) {
        this.context = context;
        this.imgIds = imgIds;
        //this.imgViewIds = imgViewIds;
        this.imgViewSQL = imgViewSQL;

        dbHelper = new JoinusDBHelper(context);
        //setImageViewsFromDatabase();
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

        // 데이터베이스에서 해당 위치에 대한 값 가져오기
        sqlDB = dbHelper.getReadableDatabase();
        String countQuery = "SELECT " + imgViewSQL[position] + " FROM " + TableInfo_user.TABLE_2_NAME;
        Cursor cursor = sqlDB.rawQuery(countQuery, null);
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            // 데이터베이스 값에 따라 이미지뷰 설정
            if (count == 1) {
                imgView.setImageResource(R.drawable.icon_check_circle);
            } else {
                imgView.setImageResource(R.drawable.icon_check_circle_outline);
            }
        }
        cursor.close();
        sqlDB.close();



        /* //todo : 만약에 sql data가 1일경우 이미지 바꾸기
        for (int i = 0; i < imgViewIds.length; i++) {
            sqlDB = dbHelper.getReadableDatabase();  // 읽기전용 데이터 베이스 가져오기
            String countQuery = "SELECT " + imgViewSQL[i] + " FROM " + TableInfo_user.TABLE_2_NAME + ";";
            Cursor cursor = sqlDB.rawQuery(countQuery, null); // 쿼리를 실행하고 커서로 가져옴
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();
            if (count == 1) {
                imgViewIds[i] = R.drawable.icon_check_circle;
            }
        } */

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

    /* private void setImageViewsFromDatabase() {
        sqlDB = dbHelper.getReadableDatabase();
        for (int i = 0; i < imgViewIds.length; i++) {
            String countQuery = "SELECT " + imgViewSQL[i] + " FROM " + TableInfo_user.TABLE_2_NAME + ";";
            Cursor cursor = sqlDB.rawQuery(countQuery, null);
            if (cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                cursor.close();
                if (count == 1) {
                    imgViewIds[i] = R.drawable.icon_check_circle;
                }
            } else {
                cursor.close();
            }
        }
        sqlDB.close();
    } */

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    private void handleImageButtonClick(int position, ImageView imgView) {

        imgView.setImageResource(R.drawable.icon_check_circle);
        // 데이터베이스에 변경된 값을 반영
        sqlDB = dbHelper.getWritableDatabase();
        String updateQuery = "UPDATE " + TableInfo_user.TABLE_2_NAME + " SET " + imgViewSQL[position] + " = 1;";
        sqlDB.execSQL(updateQuery);
        sqlDB.close();
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