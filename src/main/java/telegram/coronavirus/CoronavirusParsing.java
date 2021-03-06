package telegram.coronavirus;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import telegram.ReadProperties;

import javax.swing.text.Document;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Random;

public class CoronavirusParsing {

    ReadProperties prop = new ReadProperties();

    @SneakyThrows
    public String getConfirmedByCountry(String country) {
        String confirmedPeople = "";
        JSONObject json = readJsonFromUrl("https://api.covid19api.com/total/dayone/country/" + country.toLowerCase(Locale.ROOT));
        confirmedPeople = json.getJSONArray("main").getJSONObject(json.getJSONArray("main").length()-1).getInt("Confirmed") -
                json.getJSONArray("main").getJSONObject(json.getJSONArray("main").length()-2).getInt("Confirmed") + "";
        return confirmedPeople + "";
    }

    @SneakyThrows
    public String getRandomCountry() {
        String country = "";
        Random random = new Random();
        JSONObject json = readJsonFromUrl("https://countriesnow.space/api/v0.1/countries/currency");
        country = json.getJSONObject("main").getJSONArray("data").getJSONObject(random.nextInt(250)).getString("name").toString();
        return country + "";
    }

    @SneakyThrows
    public Boolean getValidateCountry(String country) {
        try {
            JSONObject json = readJsonFromUrl("https://api.covid19api.com/total/dayone/country/" + country.toLowerCase(Locale.ROOT));
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {

        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject("{ \"main\":" + jsonText + "}");
            return json;
        } finally {
            is.close();
        }
    }
}
