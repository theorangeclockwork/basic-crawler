import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class Crawler {

    private Document doc = null;
    private int count = 1;
    private HashMap<Integer, String> indexList = new HashMap<Integer, String>();

    void crawlByURL(String URL) throws IOException {

        try {
            doc = Jsoup.connect(URL).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert doc != null;
        Elements links = doc.select("a[href]");

        scrapAndStore(doc);
        indexList.put(count, URL);
        count++;


        for (Element link : links) {
            if (!link.attr("abs:href").contains(".jpg") && !link.attr("abs:href").contains("#") && count <= 100 && !indexList.containsValue(link.attr("abs:href"))) {
                try {
                    scrapAndStore(Jsoup.connect(link.attr("abs:href")).get());
                    indexList.put(count, link.attr("abs:href"));
                    count++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        for (Element link : links) {
            if (!link.attr("abs:href").contains(".jpg") && !link.attr("abs:href").contains("#") && count <= 100) {
                try {
                    crawlByURL(link.attr("abs:href"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void scrapAndStore(Document doc) throws IOException {

        File oFile = new File("C:\\java projects\\crawler\\crawler_maven\\src\\main\\resources\\crawler_out", count + ".txt");
        boolean success = oFile.createNewFile();
        System.out.println(success);
        FileWriter writer = new FileWriter(oFile);

        writer.write(doc.body().text());
        writer.flush();
        writer.close();


    }

    void createResult() throws IOException {
        File oFile = new File("C:\\java projects\\crawler\\crawler_maven\\src\\main\\resources\\crawler_out", "index.txt");
        boolean success = oFile.createNewFile();
        System.out.println(success);
        FileWriter writer = new FileWriter(oFile);

        for (Map.Entry<Integer, String> entry : indexList.entrySet()) {
            writer.append(String.valueOf(entry.getKey())).append(" ").append(entry.getValue());
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        }

        writer.close();
    }
}
