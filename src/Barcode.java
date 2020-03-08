import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Barcode {
    List<String> ingredients;
    static String barcode;
    private StringBuilder response;
    private String stringlist;
    private boolean seenParenthesis;
    private boolean seenSquare;

    public Barcode(String barcode) throws IOException {
        ingredients = new ArrayList<>();
        seenParenthesis = false;
        seenSquare = false;
        this.barcode = barcode;
        try {
            sendGET();
        } catch (IncompatibleBarcodeException e) {
            try {
                this.barcode = "0" + this.barcode;
                sendGET();
            } catch (IncompatibleBarcodeException e1) {
                //throw new InvalidBarcodeException();
                System.out.println("Invalid barcode: please try a different barcode");
            }
        }
        organise();
        parse();
    }

    private void organise() {
        String s = response.toString();
        int startindex = s.indexOf("ingredients_text_en");

        // check if character after "ingredients_text_en" is : or , --> if , then search again
        if (s.charAt(startindex + 20) != ':') {
            startindex = s.indexOf("ingredients_text_en", startindex + 18);
        }
        // first index of ingredient string
        startindex = startindex + 22;

        // index of the next " after start index to indicate end of ingredient string
        int endindex = s.indexOf("\"", startindex);
        String inglong = s.substring(startindex, endindex);

        // remove period at the end of ingredient list if there
        if (inglong.charAt(inglong.length() -1) == '.') {
            inglong = inglong.substring(0,inglong.length()-1);
        }
        // all lower case
        inglong = inglong.toLowerCase();
        stringlist = inglong;
    }


    private void sendGET() throws IncompatibleBarcodeException, IOException {
        String theURL = "https://world.openfoodfacts.org/api/v0/product/" + barcode + "/";
        URL obj = new URL(theURL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Chemical Barcode Scanner - Web - Version 1.0");
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine+"\n");
            }
            in.close();
            //System.out.println(response.toString());

        } else {
            System.out.println("GET request not worked");
            throw new IncompatibleBarcodeException();
        }
    }


    private void parse() {
        String str[] = stringlist.split("\\s*,\\s*|\\s*\\(\\s*|\\s*\\),\\s*");
        //
        ingredients = Arrays.asList(str);
//        for (String s : ingredients) {
//            System.out.println(s);
//        }
    }


    public List<String> getIngredients(){
        return ingredients;
    }

    public void printIngredients() {
        for (String s: ingredients) {
            System.out.println(s);
        }
    }
}


