package qsh.com.animalantiepidemic;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.net.PortUnreachableException;

import qsh.com.animalantiepidemic.models.AnimalTypeModel;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("qsh.com.animalantiepidemic", appContext.getPackageName());
    }

    public String loadResourceFileContent(int fileId) {
        String result = null;
        try {
            Context appContext = InstrumentationRegistry.getTargetContext();
            InputStream is = appContext.getResources().openRawResource(fileId);
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

    @Test
    public void load_json_from_raw_file_and_parse_to_json(){
        String fileContent = loadResourceFileContent(R.raw.animal_types);
        Gson gson = new Gson();
        AnimalTypeModel[] animalTypeModels = gson.fromJson(fileContent, AnimalTypeModel[].class);
        System.out.print("共获取" + animalTypeModels.length + "条记录。");
    }
}
