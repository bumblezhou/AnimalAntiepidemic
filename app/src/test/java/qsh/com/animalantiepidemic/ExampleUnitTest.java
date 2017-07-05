package qsh.com.animalantiepidemic;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import qsh.com.animalantiepidemic.models.AnimalTypeModel;
import qsh.com.animalantiepidemic.models.EarNumberModel;
import qsh.com.animalantiepidemic.models.FarmerModel;
import qsh.com.animalantiepidemic.models.UserModel;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void load_user_data_from_remote_server_and_store_as_local_json_file() {
        final String url = "http://www.klmyqsh.com/wx/api/PlatformUser/LoadAllUsers";

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost(url);
            StringEntity params = new StringEntity("");
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");

            com.google.gson.Gson gson = new com.google.gson.Gson();
            UserModel[] userModels = gson.fromJson(json, UserModel[].class);

            System.out.println("共获取" + userModels.length + "条记录。");
            System.out.println("准备存入文件users.json");

            //write converted json data to a file named "users.json"
            FileWriter writer = new FileWriter("users.json");
            writer.write(json);
            writer.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void load_farmer_data_from_remote_server_and_store_as_local_json_file() {
        final String url = "http://www.klmyqsh.com/wx/api/Farmer/LoadAllFarmers";

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost(url);
            StringEntity params = new StringEntity("");
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");

            com.google.gson.Gson gson = new com.google.gson.Gson();
            FarmerModel[] farmerModels = gson.fromJson(json, FarmerModel[].class);

            System.out.println("共获取" + farmerModels.length + "条记录。");
            System.out.println("准备存入文件farmers.json");

            //write converted json data to a file named "farmers.json"
            FileWriter writer = new FileWriter("farmers.json");
            writer.write(json);
            writer.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
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

    public class DateDeserializer implements JsonDeserializer<Date> {

        private static final String TAG = "DateDeserializer";
        private final String[] DATE_FORMATS = new String[]{
                "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ",
                "yyyy-MM-dd'T'HH:mm:ssZ",
                "MMM d, yyyy hh:mm:ss a",// Aug 29, 2016 09:11:08 AM
                "MMM d, yyyy HH:mm:ss",//Aug 29, 2016 16:11:08
        };

        @Override
        public Date deserialize(JsonElement jsonElement, Type typeOF, JsonDeserializationContext context) throws JsonParseException {
            //先判断是否下面的格式“/Date(1488815571000)/”
            if(jsonElement.getAsString().contains("/Date(")) {
                String elementVlue = jsonElement.getAsString().replace("/Date(", "").replace(")/", "");
                return new Date(Long.valueOf(elementVlue));
            }
            else {
                //否则，就是常见的几种预定格式
                for (String format : DATE_FORMATS) {

                    try {
                        return new SimpleDateFormat(format, Locale.US).parse(jsonElement.getAsString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            throw new JsonParseException("Unparseable date: \"" + jsonElement.getAsString() + "\". Supported formats: " + Arrays.toString(DATE_FORMATS));
        }
    }

    @Test
    public void load_ear_number_data_from_remote_server_and_store_as_local_json_file() {
        final String url = "http://www.klmyqsh.com/wx/api/ShortUrl/LoadAllEarNumbers";

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost(url);
            StringEntity params = new StringEntity("");
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());

            com.google.gson.Gson gson = gsonBuilder.create();
            EarNumberModel[] earNumberModels = gson.fromJson(json, EarNumberModel[].class);

            System.out.println("共获取" + earNumberModels.length + "条记录。");
            System.out.println("准备存入文件ear_numbers.json");

            //write converted json data to a file named "ear_numbers.json"
            FileWriter writer = new FileWriter("ear_numbers.json");
            writer.write(json);
            writer.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}