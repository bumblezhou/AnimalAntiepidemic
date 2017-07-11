package qsh.com.animalantiepidemic.helper;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import qsh.com.animalantiepidemic.R;
import qsh.com.animalantiepidemic.models.FarmerModel;

/**
 * Created by JackZhou on 07/07/2017.
 */

public class LocalResourceHelper {

    public static String loadResourceFileContent(Context appContext, int resourceId) {
        Log.d("localresource", "resource id :" + resourceId);
        String result = null;
        try {
            InputStream is = appContext.getResources().openRawResource(resourceId);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            result = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return result;
    }
}
