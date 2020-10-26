package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Random;


public class Facts {
    public static void main(String[] args) throws IOException {
        String url = "https://zen.yandex.ru/media/id/5cd59c2cf9616800b423d410/samorazvitie-interesnye-fakty-obo-vsem-na-svete-50-faktov-5cd6b792c9c89500afe92211";
        Document document = Jsoup.connect(url).get();
        Elements elements = document.select("blockquote:not(0)");
        int numberOfElements = 2;
        Random random = new Random();
        for (int i = 1; i < numberOfElements; i++) {
            int randomIndex = random.nextInt(elements.size());
            System.out.println(elements.get(randomIndex).text());
            //elements.remove(randomIndex);
        }
    }

    }

