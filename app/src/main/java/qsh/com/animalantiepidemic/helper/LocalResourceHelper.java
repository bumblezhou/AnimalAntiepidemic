package qsh.com.animalantiepidemic.helper;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

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
