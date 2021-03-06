import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.List;



public class DB {


    public static void main(String[] args) {

        String JDBC_DRIVER = "org.postgresql.Driver";
        String DB_URL = "jdbc:postgresql://ec2-54-80-184-43.compute-1.amazonaws.com:5432/dcjf5k489t6s9o";


       String USER = "ueygyctekddcln";
       String PASS = "c2605ef25ce571b45b4349407eb10e34e23533d291e7b347c953bb0a1a22da0f";

       Connection conn = null;
       Statement stmt = null;
       String csvFilePathCarcinogen = "carcinogen_v1.csv";
       String carcinogenItem = null;
       PreparedStatement statement2 = null;
       PreparedStatement statement1 = null;

       // Register JDBC driver

        int count = 0;

       int batchSize = 20;

        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn.setAutoCommit(false);
            System.out.println("Connected database successfully...");
            stmt = conn.createStatement();

            //System.out.println("Deleting table in given database...");
            stmt = conn.createStatement();

            String sql1 = "DROP TABLE ingredients ";
            String sql2 = "DROP TABLE carcinogens ";


            stmt.executeUpdate(sql1);
            stmt.executeUpdate(sql2);
            //System.out.println("Table deleted in given database");

            String ingredients = "CREATE TABLE INGREDIENTS" +
                    "(id INTEGER NOT NULL, " +
                    " name VARCHAR(255), " +
                    " PRIMARY KEY ( id ))";

            String carcinogens = "CREATE TABLE CARCINOGENS" +
                    "(id INTEGER NOT NULL, " +
                    " name VARCHAR(255), " +
                    " info VARCHAR(255), " +
                    " classification INTEGER not NULL, " +
                    " PRIMARY KEY ( id ))";


            stmt.executeUpdate(ingredients);
            stmt.executeUpdate(carcinogens);

            //example barcode
            String input = "0859082003610";

            Barcode b = null;
            try {
                b = new Barcode(input);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Parsing Ingredient List...");


            List<String> array = b.getIngredients();


//          b.printIngredients();

            BufferedReader linereader = new BufferedReader(new FileReader(csvFilePathCarcinogen));
            String lineText = null;


            linereader.readLine();

            while((lineText = linereader.readLine()) != null) {
                String[] data = lineText.split(",");
                Integer id = Integer.parseInt(data[0]);
                String name = data[1];
                String info = data[2];
                Integer classif = Integer.parseInt(data[3]);

               carcinogenItem = "INSERT INTO carcinogens (id, name, info, classification) VALUES (?,?,?,?)";
               statement2 = conn.prepareStatement((carcinogenItem));

                statement2.setInt(1, id);
                statement2.setString(2, name);
                statement2.setString(3, info);
                statement2.setInt(4, classif);

                statement2.addBatch();

                if(count % batchSize == 0){
                    statement2.executeBatch();
                }
            }

            linereader.close();
            conn.commit();

            statement2.executeBatch();

            String sql = "SELECT id, name, info, classification FROM carcinogens";
            ResultSet rs = stmt.executeQuery(sql);
            //System.out.println("Inserting Carcinogens into Database...");
            while(rs.next()){
                int id  = rs.getInt("id");
                int classification = rs.getInt("classification");
                String name = rs.getString("name");
                String info = rs.getString("info");

                //Display values

//                System.out.print("ID: " + id);
//                System.out.print(", Name: " + name);
//                System.out.print(", Info: " + info);
//                System.out.println(", Classification: " + classification);
            }
            rs.close();

            int pk_val = 0;

            for(int i = 0; i < array.size()-1; i++){
                String ingredientItem = "INSERT INTO ingredients (id, name) VALUES (?,?)";
                statement1 = conn.prepareStatement((ingredientItem));
                statement1.setInt(1, pk_val);
                pk_val++;
                statement1.setString(2, array.get(i));
                statement1.addBatch();
                if(count % batchSize == 0) {
                    statement1.executeBatch();
                }
            }
                statement1.executeBatch();

            String ing_list = "SELECT id, name FROM ingredients";
            ResultSet rs_ingredient = stmt.executeQuery(ing_list);
            //System.out.println("Inserting Ingredient List into Database...");
            while(rs_ingredient.next()){
                int id  = rs_ingredient.getInt("id");
                String name = rs_ingredient.getString("name");

                //Display values
//                System.out.print("ID: " + id + " Name: " + name);
//                System.out.println(" ");
            }
            rs_ingredient.close();

            System.out.println("Searching for Carcinogens in Ingredient List...");

            String joined_list = "SELECT * FROM carcinogens B INNER JOIN ingredients A ON B.name = A.name";
            ResultSet rs_join = stmt.executeQuery(joined_list);
            System.out.println("Ingredients from Input Considered as a Carcinogen:");

            while(rs_join.next()){
                int id  = rs_join.getInt("id");
                String name = rs_join.getString("name");
                String info = rs_join.getString("info");
                int classification = rs_join.getInt("classification");

                //Display values
                System.out.print("ID: " + id + " Name: " + name + " Info: " + info + " Classification: " + classification);
                System.out.println(" ");
            }
            rs_join.close();


            System.out.println("successfully added items");
            conn.close();

        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

    }

}





