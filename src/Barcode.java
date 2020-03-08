import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.net.URL;

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
        boolean b = s.contains("ingredients_text_en");
        int startindex = s.indexOf("ingredients_text_en");
        int index2 = s.indexOf("_", startindex+12);
        int index3 = startindex+22;
        System.out.println(s.charAt(index3));
        if (s.charAt(startindex + 20) != ':') {
            startindex = s.indexOf("ingredients_text_en", startindex + 18);
        }
        System.out.println(s.charAt(startindex+22));
        startindex = startindex + 22;

        int endindex = s.indexOf("\"", startindex);
        String inglong = s.substring(startindex, endindex);
        System.out.println(inglong);
        if (inglong.charAt(inglong.length() -1) == '.') {
            inglong = inglong.substring(0,inglong.length()-1);
        };
        System.out.println(inglong);
        inglong = inglong.toLowerCase();
        System.out.println(inglong);
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
            System.out.println(response.toString());

        } else {
            System.out.println("GET request not worked");
            throw new IncompatibleBarcodeException();
        }
    }


//    private void parse() {
//        String temp = "";
//        for (int i = 0; i<stringlist.length(); i++) {
//            char x = stringlist.charAt(i);
//            if (x == '[') {
//                seenSquare = true;
//            } else if (x == '(') {
//                seenParenthesis = true;
//            } else if (x == ']') {
//                seenSquare = false;
//            } else if (x == ')') {
//                seenParenthesis = false;
//            } else if ((x == ',') && (seenParenthesis || seenSquare)) {
//                temp = temp + x;
//            } else if (x != ',') {
//                temp = temp + x;
//            } else {
//                System.out.println(temp);
//                ingredients.add(temp);
//                temp = "";
//                if (i != stringlist.length() - 1) {
//                    i++;
//                }
//            }
//        }
//    }
    private void parse() {
        String str[] = stringlist.split("\\s*,\\s*|\\s*\\(\\s*|\\s*\\)\\s*");
        //
        ingredients = Arrays.asList(str);
        for (String s: ingredients) {
            System.out.println(s);
        }

        for (String s: ingredients) {
            if (s.equals("")) {
                ingredients.remove(s);
            }
        }
        for (String s: ingredients) {
            System.out.println(s);
        }
    }


    public List<String> getIngredients(){
        return ingredients;
    }
}


