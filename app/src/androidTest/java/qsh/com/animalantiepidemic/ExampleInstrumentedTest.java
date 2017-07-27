package qsh.com.animalantiepidemic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.dtr.zbar.build.ZBarDecoder;
import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.PortUnreachableException;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.util.EntityUtils;
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

    @Test
    public void test_zbar_decoder(){
        Bitmap bmp = BitmapFactory.decodeResource(InstrumentationRegistry.getTargetContext().getResources(), R.raw.qrcode_test_1);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] data = bos.toByteArray();

        if(data == null){
            Log.d("decode test", "加载图片资源失败！");
        }

        ZBarDecoder zBarDecoder = new ZBarDecoder();
        String result = zBarDecoder.decodeCrop(data, 790, 719, 0, 0, 790, 719);
        if(result != null){
            Log.d("decode test", "扫描结果：" + result);
        } else {
            Log.d("decode test", "无扫描结果！");
        }
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

    @Test
    public void load_animal_type_data_from_remote_server_and_store_as_local_json_file() {
        final String url = "http://www.klmyqsh.com/wx/api/AnimalType/LoadAllAnimalTypes";

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost(url);
            StringEntity params = new StringEntity("");
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");

            com.google.gson.Gson gson = new com.google.gson.Gson();
            AnimalTypeModel[] animalTypeModels = gson.fromJson(json, AnimalTypeModel[].class);

            System.out.println("共获取" + animalTypeModels.length + "条记录。");
            System.out.println("准备存入文件animal_types.json");

            //write converted json data to a file named "animal_types.json"
            FileWriter writer = new FileWriter("animal_types.json");
            writer.write(json);
            writer.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
