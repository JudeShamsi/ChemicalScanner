import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class ChemicalBarcodeScanner {


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

       // ArrayList<ArrayList<String>> lists = new ArrayList<>();
//        String input = "0737628003006";
//        Barcode b = new Barcode(input);
//        lists.add(b.getIngredients());

//        input = "0017082876317";
//        b = new Barcode(input);
//        lists.add(b.getIngredients());

        String input = "017082876317";
        Barcode b = new Barcode(input);
        //lists.add(b.getIngredients());
    }
}
