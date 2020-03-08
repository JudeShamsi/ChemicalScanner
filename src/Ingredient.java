import java.util.Arrays;

public class Ingredient {
    String name;

    public void Ingredient(String name) {
        this.name = name;
    }

    public String getLink() {
        String str[] = name.split("\\s*");
        //
        String query = "";
        for (String s : str) {
            query = s + "_";
        }
        return "https://en.wikipedia.org/wiki/" + query;
    }


}
