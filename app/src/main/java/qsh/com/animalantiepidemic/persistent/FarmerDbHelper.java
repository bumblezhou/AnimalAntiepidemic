package qsh.com.animalantiepidemic.persistent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import qsh.com.animalantiepidemic.models.FarmerModel;

/**
 * Created by JackZhou on 06/07/2017.
 */

public class FarmerDbHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "animal_antiepidemic.db";

    public FarmerDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists " + FarmerModel.TABLE_NAME + " (id integer primary key, householder text, mobile text, address text, breed_type integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + FarmerModel.TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public static void syncFarmersToDatabase(Context context, List<FarmerModel> farmerModels){
        SQLiteDatabase db = new FarmerDbHelper(context).getReadableDatabase();
        //找一下库中有没有JSON文件中最大ID的记录
        Cursor queryCursor = db.query(FarmerModel.TABLE_NAME, FarmerModel.COLUMN_NAMES,
                "id = ?",
                new String[] { farmerModels.get(farmerModels.size() - 1).getId().toString() },
                null, null, null);
        Integer recordCount = queryCursor.getCount();
        db.close();

        //如果有，则别同步了
        if (recordCount > 0) {
            Log.i("database", "如果有，则别同步了");
            Log.i("database", "关闭数据库连接");
        } else {
            Log.i("database", "如果没有JSON文件中最大ID的记录");
            Log.i("database", "开始同步畜主数据到数据库中");
            db = new FarmerDbHelper(context).getWritableDatabase();
            try {
                for(FarmerModel farmer : farmerModels){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("id", farmer.getId());
                    contentValues.put("householder", farmer.getHouseholder());
                    contentValues.put("mobile", farmer.getMobile());
                    contentValues.put("address", farmer.getAddress());
                    contentValues.put("breed_type", farmer.getBreed_type());
                    db.insert(FarmerModel.TABLE_NAME, null, contentValues);
                }
                Log.i("database", "完成同步畜主数据到数据库，关闭数据库连接");
            } catch (Exception e) {
                Log.d("debug", "同步数据库失败，错误信息:" + e.getMessage());
            }
            db.close();
        }
    }

    public static List<FarmerModel> loadAllFarmersFromDatabase(Context context){
        List<FarmerModel> result = new ArrayList<>();
        SQLiteDatabase db = new FarmerDbHelper(context).getReadableDatabase();
        Cursor queryCursor = db.query(FarmerModel.TABLE_NAME, FarmerModel.COLUMN_NAMES,
                null,
                null,
                null, null, null);
        while (queryCursor.moveToNext()){
            FarmerModel tempModel = new FarmerModel(
                    queryCursor.getInt(queryCursor.getColumnIndex("id")),
                    queryCursor.getString(queryCursor.getColumnIndex("householder")),
                    queryCursor.getString(queryCursor.getColumnIndex("mobile")),
                    queryCursor.getString(queryCursor.getColumnIndex("address")),
                    queryCursor.getInt(queryCursor.getColumnIndex("breed_type")));
            result.add(tempModel);
        }
        db.close();
        return result;
    }
}
