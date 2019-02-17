import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Crawler crawler = new Crawler();
        crawler.crawlByURL("https://2ch.hk/");
        crawler.createResult();
    }
}
