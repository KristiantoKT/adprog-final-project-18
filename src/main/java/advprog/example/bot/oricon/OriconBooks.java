package advprog.example.bot.oricon;

import static java.lang.String.format;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class OriconBooks {
    private List<Book> topTenBookList;

    public OriconBooks(String oriconUrl, String rankingDate) {
        topTenBookList = new ArrayList<>();
        setTopTenBookList(oriconUrl, rankingDate);
    }

    private void setTopTenBookList(String url, String date) {
        try {
            String newUrl = url + "/" + date + "/";
            Document doc = Jsoup.connect(newUrl).get();
            Elements links = doc.getElementsByClass("box-rank-entry");

            for (Element element : links) {
                String name = element.getElementsByClass("title").html();
                String author = element.getElementsByClass("name").html();
                String[] data = element.getElementsByClass("list").html().split("\n");
                String releaseDate = data[1];
                String estimatedSales = data[3];

                releaseDate = releaseDate.replaceAll("<[^>]*>", "")
                        .replace("発売日：", "")
                        .replace("年", "-");
                estimatedSales = estimatedSales.replaceAll("<[^>]*>", "")
                        .replace("推定売上部数：", "")
                        .replace("部", "")
                        .replaceAll(",","");

                Book book = new Book(name, author, releaseDate, Integer.parseInt(estimatedSales));
                topTenBookList.add(book);
            }
        } catch (IOException e) {
            System.out.println("Illegal IO");
        } catch (IllegalArgumentException e) {
            System.out.println("Malformed URL");
        }
    }

    public String printTopTenList() {
        StringBuilder sb = new StringBuilder();
        int counter = 1;
        for (Book book : topTenBookList) {
            sb.append(format("(%d) %s\n", counter, book.toString()));
            counter++;
        }
        return sb.toString();
    }
}
