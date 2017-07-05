package qsh.com.animalantiepidemic.persistent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JackZhou on 06/07/2017.
 */

public class AnimalAntiepidemicDbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "animal_antiepidemic.db";
    public static final String TABLE_NAME = "AnimalAntiepidemics";

    public AnimalAntiepidemicDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists " + TABLE_NAME + " (id integer primary key, animal_type_id integer, animal_type_id integer, animal_amount integer, ought_amount integer, actual_amount integer, missing_amount integer, missing_reason text, missing_animal_antiepidemic_date text, out_cote_amount integer, in_cote_amount integer, antiepidemic_date text, farmer_id integer, user_id integer, vaccine text, vaccine_manufacture text, vaccine_patch_number text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}
