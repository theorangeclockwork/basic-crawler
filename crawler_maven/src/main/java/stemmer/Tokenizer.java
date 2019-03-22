package stemmer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Tokenizer {

    public static void main(String[] args) {

        File folderTokenizer = new File("C:\\java projects\\crawler\\crawler_maven\\src\\main\\resources\\tokenizer_out");
        File folderCrawler = new File("C:\\java projects\\crawler\\crawler_maven\\src\\main\\resources\\crawler_out");

        for (File file : folderTokenizer.listFiles()) {
            if (!file.isDirectory())
                file.delete();
        }

        for (int i = 1; i < folderCrawler.listFiles().length; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            String rawData = getData("C:\\java projects\\crawler\\crawler_maven\\src\\main\\resources\\crawler_out\\" + i + ".txt");
            String[] rawDataSplit =  rawData.split(" ");
            for (String elem :  rawDataSplit) {
                elem = removeNonLetters(elem);
                elem = Stemmer.stem(elem);
                stringBuilder.append(elem.trim());
                stringBuilder.append("  ");
            }
            writeData(stringBuilder.toString(), i);
        }
    }

    private static String removeNonLetters(String word) {
        word = word.replace(".", "")
                .replace(")", "")
                .replace(":", "")
                .replace("(", "")
                .replace(";", "")
                .replace("=", "")
                .replace("?", "")
                .replace(",", "")
                .replace("!", "")
                .replace("[", "")
                .replace("]", "")
                .replace("%", "")
                .replace("%", "")
                .replace("→", "")
                .replace("«", "")
                .replace("»", "")
                .replace("   ", "")
                .replace("…", "")
                .replace("\\", "")
                .replace("/", "")
                .replace("\"", "")
                .replace("*", "")
                .replace(">", "")
                .replace("|", "")
                .replace("<", "")
                .replace(" ", "")
                .replace(" ", "")
                .replace("$", "")
                .replace("+", "")
                .replace("-", "")
                .replace("...", "");
        if (!(word.equals("–")) && !word.equals("—")) {
            return word;
        }
        return "";
    }

    private static String getData(String filePath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private static void writeData(String data, int i) {
        File file = new File("C:\\java projects\\crawler\\crawler_maven\\src\\main\\resources\\tokenizer_out\\" + i + ".txt");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            fr.write(data);
            fr.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}