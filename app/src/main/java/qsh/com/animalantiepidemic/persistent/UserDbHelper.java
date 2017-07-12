package qsh.com.animalantiepidemic.persistent;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import qsh.com.animalantiepidemic.R;
import qsh.com.animalantiepidemic.models.FarmerModel;
import qsh.com.animalantiepidemic.models.UserModel;

/**
 * Created by JackZhou on 06/07/2017.
 */

public class UserDbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 4;
    private static final String DB_NAME = "animal_antiepidemic.db";

    public UserDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists " + UserModel.TABLE_NAME + " (id integer primary key, name text, address text, fullName text, password text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + UserModel.TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}
