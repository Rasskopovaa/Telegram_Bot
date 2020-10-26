package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Random;

public class Afirmations {
    public void getAfirmation () throws IOException {
        String url = "https://bbf.ru/magazine/26/7253/";
        Document document = Jsoup.connect(url).get();
        Elements elements = document.select("ol > li");
        int number = 2;
        Random rand = new Random();
        for (int i = 1; i < number; i++) {
            int randomIndex = rand.nextInt(elements.size());
            System.out.println(elements.get(randomIndex).text());
            //elements.remove(randomIndex);
        }
    }
    public static void main(String[] args) throws IOException {
        Afirmations afirmations= new Afirmations();
        afirmations.getAfirmation();
    }
}
