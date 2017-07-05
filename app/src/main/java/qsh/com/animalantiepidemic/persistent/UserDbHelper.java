package qsh.com.animalantiepidemic.persistent;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import qsh.com.animalantiepidemic.R;
import qsh.com.animalantiepidemic.models.UserModel;

/**
 * Created by JackZhou on 06/07/2017.
 */

public class UserDbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "animal_antiepidemic.db";
    public static final String TABLE_NAME = "Users";

    public UserDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists " + TABLE_NAME + " (id integer primary key, name text, address text, fullName text, password text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public UserModel parseUser(Cursor cursor){
        UserModel userModel = new UserModel();
        userModel.setId(cursor.getInt(cursor.getColumnIndex("Id")));
        userModel.setAddress(cursor.getString(cursor.getColumnIndex("address")));
        userModel.setName(cursor.getString(cursor.getColumnIndex("name")));
        userModel.setFullName(cursor.getString(cursor.getColumnIndex("fullName")));
        userModel.setPassword(cursor.getString(cursor.getColumnIndex("password")));
        return userModel;
    }
}
