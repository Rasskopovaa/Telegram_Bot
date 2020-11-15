package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Random;

public class Afirmations {
    public String getAfirmation () throws IOException {
        String url = "https://bbf.ru/magazine/26/7253/";
        Document document = Jsoup.connect(url).get();
        String str="";
        Elements elements = document.select("ol > li");
        int number = 2;
        Random rand = new Random();
        for (int i = 1; i < number; i++) {
            int randomIndex = rand.nextInt(elements.size());
            str = elements.get(randomIndex).text();
            //elements.remove(randomIndex);
        }
        return str;
    }

}
