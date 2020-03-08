import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.net.URL;

public class Barcode {
    List<String> in;
    List<String> additives;
    List<String> ingredients;
    static String barcode;
    private StringBuilder response;
    private String stringlisti;
    private String stringlista;

    public Barcode(String barcode) throws IOException {
        in = new ArrayList<>();
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
        organiseIngredients();
        organiseAdditives();
        parseIngredients();
        parseAdditives();
        int capacity = in.size() + additives.size();
        ingredients = new ArrayList<>(capacity);

        for (int i = 0; i<in.size() -1; i++) {
            String s = in.get(i);
            ingredients.add(s);
        }
        for (int i = 0; i<additives.size() - 1; i++) {
            String s = additives.get(i);
            ingredients.add(s);
        }
    }

    private void organiseAdditives() {
        String s = response.toString();
        int startindex = s.indexOf("additives_original_tags");

        // first index of ingredient string
        startindex = startindex + 26;

        // index of the next " after start index to indicate end of ingredient string
        int endindex = s.indexOf("]", startindex);
        String inglong = s.substring(startindex, endindex);

        stringlista = inglong;
    }

    private void organiseIngredients() {
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
        stringlisti = inglong;
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
//            System.out.println(response.toString());

        } else {
            System.out.println("GET request not worked");
            throw new IncompatibleBarcodeException();
        }
    }


    private void parseIngredients() {
        String str[] = stringlisti.split("\\s*,\\s*|\\s*\\(\\s*|\\s*\\),\\s*");
        //
        in = Arrays.asList(str);
    }

    private void parseAdditives() {
        String str[] = stringlista.split("\\s*,\\s*");
        additives = Arrays.asList(str);
        for (int i = 0; i < additives.size() -1; i++) {
            additives.set(i, additives.get(i).substring(4,8));
        }
    }


    public List<String> getIngredients(){
        return ingredients;
    }

    public void printIngredients() {
        for (String s : in) {
            System.out.println(s);
        }
    }
}


