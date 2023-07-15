package com.example.joinus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

public class GridAdapter extends BaseAdapter {

    Context context;
    int[] imgIds; // 이미지 버튼에 들어갈 그림의 아이디
    //int[] imgViewIds; // 이미지뷰에 들어갈 그림의 아이디
    LayoutInflater inflater;

    public GridAdapter(Context context, int[] imgIds) {
        this.context = context;
        this.imgIds = imgIds;
        //this.imgViewIds = imgViewIds;
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
        //ImageView imgView = convertView.findViewById(R.id.grid_img);

        imgBtn.setImageResource(imgIds[position]);
        //imgView.setImageResource(imgViewIds[position]);

        return convertView;
    }
}