import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ChemicalBarcodeScanner {

// INSERT BARCODE IN input STRING
    public static void main(String[] args) throws MalformedURLException, IOException {

//        Action action = new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) { // action performed: press enter or "search" button
//                // set user input to barcode
//                String input = "0850004207024"; // let this come from the user input bar
//                try {
//                    Barcode barcode = new Barcode(input);
//                } catch (IOException ex) {
//                    System.out.println("Invalid barcode: please try a different barcode");
//                }
//            }
//        };

        // FOR LOCAL TESTING PURPOSES
//        String input = "0850004207024";

//        String input = "0737628003006";
//        Barcode b = new Barcode(input);

//        input = "0017082876317";
//        b = new Barcode(input);

        String input = "0859082003610";
        Barcode b = new Barcode(input);
        //b.printIngredients();
        List<String> ingredients = b.getIngredients();
        URL url = new URL(getLink(ingredients.get(5)));
        System.out.println(url);
//        for (String i : ingredients) {
//            String link = getLink(i);
//            System.out.println(link);
//        }
    }

    public static String getLink(String name) {
        String str[] = name.split("\\s");
        String query = "";
        for (String s : str) {
            query = query + s + "_";
        }
        return "https://en.wikipedia.org/wiki/" + query;
    }
}
