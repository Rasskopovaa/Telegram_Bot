package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Random;


public class Facts {
    public String getFact() throws IOException {
        String url = "https://zen.yandex.ru/media/id/5cd59c2cf9616800b423d410/samorazvitie-interesnye-fakty-obo-vsem-na-svete-50-faktov-5cd6b792c9c89500afe92211";
        Document document = Jsoup.connect(url).get();
        Elements elements = document.select("blockquote:not(0)");
        int numberOfElements = 2;
        String str="";
        Random random = new Random();
        for (int i = 1; i < numberOfElements; i++) {
            int randomIndex = random.nextInt(elements.size());
             str =elements.get(randomIndex).text();
        }
        return str;

    }

   public static void main(String[] args) throws IOException {
        Facts facts = new Facts();
       System.out.println(facts.getFact());
        //elements.remove(randomIndex);
    }
    }


