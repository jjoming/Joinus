package com.example.joinus;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class JoinusDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME = "JoinUsUser.db";

    public static final String SQL_CREATE_TABLE_1 =
            "CREATE TABLE " + TableInfo_user.TABLE_1_NAME + " (" +
                    TableInfo_user.TABLE_1_COLUMN_NAME_NAME + " TEXT)";
    //todo : 2 생성 쿼리 테이블 수정
    public static final String SQL_CREATE_TABLE_2 =
            "CREATE TABLE " + TableInfo_user.TABLE_2_NAME + " (" +
                    TableInfo_user.TABLE_2_COLUMN_NAME_GOAL + " INTEGER DEFAULT 0, " +
                    TableInfo_user.TABLE_2_COLUMN_NAME_BICYCLE + " INTEGER DEFAULT 0, " +
                    TableInfo_user.TABLE_2_COLUMN_NAME_LABEL + " INTEGER DEFAULT 0, " +
                    TableInfo_user.TABLE_2_COLUMN_NAME_BUS + " INTEGER DEFAULT 0, " +
                    TableInfo_user.TABLE_2_COLUMN_NAME_OFF + " INTEGER DEFAULT 0, " +
                    TableInfo_user.TABLE_2_COLUMN_NAME_SMARTPHONE + " INTEGER DEFAULT 0, " +
                    TableInfo_user.TABLE_2_COLUMN_NAME_TUMBLER + " INTEGER DEFAULT 0, " +
                    TableInfo_user.TABLE_2_COLUMN_NAME_MAIL + " INTEGER DEFAULT 0, " +
                    TableInfo_user.TABLE_2_COLUMN_NAME_BASKET + " INTEGER DEFAULT 0, " +
                    TableInfo_user.TABLE_2_COLUMN_NAME_LAUNDRY + " INTEGER DEFAULT 0, " +
                    TableInfo_user.TABLE_2_COLUMN_NAME_FOOD + " INTEGER DEFAULT 0" +
                    ")";
    public static final String SQL_CREATE_TABLE_3 = "CREATE TABLE " + TableInfo_user.TABLE_3_NAME + " (" + TableInfo_user.TABLE_3_COLUMN_NAME_STAMP + " INTEGER)";

    private static final String SQL_DELETE_TABLE_1 = "DROP TABLE IF EXISTS " + TableInfo_user.TABLE_1_NAME;
    private static final String SQL_DELETE_TABLE_2 = "DROP TABLE IF EXISTS " + TableInfo_user.TABLE_2_NAME;
    private static final String SQL_DELETE_TABLE_3 = "DROP TABLE IF EXISTS " + TableInfo_user.TABLE_3_NAME;

    public JoinusDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public boolean hasData() {
        SQLiteDatabase db = getReadableDatabase();  // 읽기전용 데이터 베이스 가져오기
        String countQuery = "SELECT COUNT(*) FROM " + TableInfo_user.TABLE_1_NAME; //테이블 행 수를 센다
        Cursor cursor = db.rawQuery(countQuery, null); // 쿼리를 실행하고 커서로 가져옴
        cursor.moveToFirst(); // 커서를 첫번째 행으로 이동
        int count = cursor.getInt(0);   // count 값을 가져옴
        cursor.close(); // 커서 닫기
        return count > 0;   // 데이터가 있으면 true를 없으면 false를 반환
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_1);
        db.execSQL(SQL_CREATE_TABLE_2);
        db.execSQL(SQL_CREATE_TABLE_3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_TABLE_1);
        db.execSQL(SQL_DELETE_TABLE_2);
        db.execSQL(SQL_DELETE_TABLE_3);
        onCreate(db);
    }
}
