package com.example.joinus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class GridAdapter extends BaseAdapter {

    Context context;
    int[] imgIds; // 이미지 버튼에 들어갈 그림의 아이디
    int[] imgViewIds; // 이미지뷰에 들어갈 그림의 아이디
    LayoutInflater inflater;

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

        imgBtn.setImageResource(imgIds[position]);
        //imgView.setImageResource(R.drawable.your_image_resource); // 이미지뷰에 원하는 이미지 설정
        imgView.setImageResource(imgViewIds[position]);

        // ImageButton에 OnClickListener 설정
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // todo : 카메라로 이동 후 확인 버튼 클릭시 체크표시로

                handleImageButtonClick(position, imgView);
            }
        });


        return convertView;
    }

    private void handleImageButtonClick(int position, ImageView imgView) {
        //
        imgView.setImageResource(R.drawable.icon_check_circle);
        // todo : 오늘의 목표 퍼센테이지 높이기
    }
}