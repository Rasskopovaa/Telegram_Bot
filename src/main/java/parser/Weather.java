package parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Weather {
    public String getWeather() throws IOException {
        String myURL = "";

            myURL = "https://api.openweathermap.org/data/2.5/weather?q=Almaty&appid=984f6a857d463eb2a1ba01bbbb5a2ebf";



        URL url = new URL(myURL);
        URLConnection req=url.openConnection();
        req.connect();
        JsonElement elem = JsonParser.parseReader(new InputStreamReader((InputStream) req.getContent()));

        JsonObject elemObj = elem.getAsJsonObject();
        JsonObject main = elemObj.getAsJsonObject("main");

        double temp = Double.parseDouble(main.get("temp").getAsString());
        double feelsLike = Double.parseDouble(main.get("feels_like").getAsString());
        int result = (int) (temp- 273.15);
        int resultFeels= (int) (feelsLike - 273.15);

        return  "Погода в Алматы сейчас: " + " " + result + " "+ "C." + " "+
                "Ощущается как: " + " " + resultFeels + " "+ "C";
    }

}
