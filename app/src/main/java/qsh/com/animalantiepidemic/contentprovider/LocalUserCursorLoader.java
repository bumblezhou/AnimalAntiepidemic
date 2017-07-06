package qsh.com.animalantiepidemic.contentprovider;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import qsh.com.animalantiepidemic.R;
import qsh.com.animalantiepidemic.helper.LocalResourceHelper;
import qsh.com.animalantiepidemic.models.UserModel;

/**
 * Created by JackZhou on 06/07/2017.
 */

/*
    自定义的CurlorLoader,实现从本地文件中加载用户数据
     */
public class LocalUserCursorLoader extends CursorLoader {
    private final ForceLoadContentObserver mObserver = new ForceLoadContentObserver();

    public LocalUserCursorLoader(Context context) {
        super(context);
    }



    @Override
    public Cursor loadInBackground() {

        //本地文件存储转入本地数据库存储中
        Log.i("database", "从本地文件中获取用户数据");
        String fileContent = LocalResourceHelper.loadResourceFileContent(getContext(), R.raw.users);
        Gson gson = new Gson();
        UserModel[] userModels = gson.fromJson(fileContent, UserModel[].class);
        Log.i("database", "共获取用户数据条数:" + userModels.length);
        MatrixCursor cursor = new MatrixCursor(new String[] {"id", "name", "fullName", "address", "password"}); // properties from the JSONObjects
        for (UserModel user : userModels) {
            cursor.addRow(new Object[]{ user.getId(), user.getName(), user.getFullName(), user.getAddress(), user.getPassword()});
        }
        // Ensure the cursor window is filled
        cursor.getCount();
        cursor.registerContentObserver(mObserver);

        return cursor;
    }
}
