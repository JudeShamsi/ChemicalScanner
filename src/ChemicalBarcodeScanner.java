import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ChemicalBarcodeScanner {
    public static List<String> ingredientNames;
    public static List<Ingredient> ingredients;

// INSERT BARCODE IN input STRING
    public static void main(String[] args) throws IOException {

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

        String input = "0859082003610";
        Barcode b = new Barcode(input);
        ingredientNames = b.getIngredients();
    }

}
