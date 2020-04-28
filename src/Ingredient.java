//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;

//import javax.swing.text.Document;
import java.io.IOException;

public class Ingredient {
    String name;
    Boolean carcinogenic;
    String link;

    public Ingredient(String name) throws IOException {
        this.name = name;
        carcinogenic = false;
        setLink();
        //isCarcinogenic();
    }

    public String getLink() {
        return link;
    }

    private void setLink() {
        String str[] = name.split("\\s");
        //
        String query = "";
        for (String s : str) {
            query = query + s + "_";
        }
        link = "https://en.wikipedia.org/wiki/" + query;
    }

//    public void isCarcinogenic() throws IOException {
//        Document doc;
//        System.out.println(link);
//        String html = Jsoup.connect(link).get().html();
//        boolean b = html.contains("carcinogenic");
////        String s = doc.body().text();
//        System.out.println(html);
////        Elements newsHead

//    }


}
