import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class DB {


    public static void main(String[] args){

        String JDBC_DRIVER = "org.postgresql.Driver";
        String DB_URL = "jdbc:postgresql://ec2-54-80-184-43.compute-1.amazonaws.com:5432/dcjf5k489t6s9o";


       String USER = "ueygyctekddcln";
       String PASS = "c2605ef25ce571b45b4349407eb10e34e23533d291e7b347c953bb0a1a22da0f";

       Connection conn = null;
       Statement stmt = null;
       String csvFilePathCarcinogen = "Reviews-simple.csv";

       int batchSize = 20;

        try{
            // Register JDBC driver
            Class.forName("org.postgresql.Driver");

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

//            String ingredientItem = "INSERT INTO ingredients (name) VALUES (?)";
//            PreparedStatement statement1 = conn.prepareStatement((ingredientItem));
//
//            String carcinogenItem = "INSERT INTO carcinogens (agent, info, classification) VALUES (?,?,?)";
//            PreparedStatement statement2 = conn.prepareStatement((carcinogenItem));
//
//            BufferedReader linereader = new BufferedReader(new FileReader(csvFilePathCarcinogen));
//            String lineText = null;
//
//            int count = 0;
//
//            linereader.readLine();
//
//            while((lineText = linereader.readLine()) != null) {
//                String[] data = lineText.split(",");
//                String agent = data[0];
//                String info = data[1];
//                Integer classif = data[2];
//
//                statement2.setString(1, agent);
//                statement2.setString(2, info);
//                statement2.setInt(3, classif);
//
//                statement2.addBatch();
//
//                if(count % batchSize == 0){
//                    statement2.executeBatch();
//                }
//            }
//
//            linereader.close();
//
//            statement2.executeBatch();
//
//            conn.commit();
//            conn.close();
//



















        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}

// DROPPING A TABLE

//            System.out.println("Deleting table in given database...");
//            stmt = conn.createStatement();
//
//            String sql = "DROP TABLE ingredients ";
//
//            stmt.executeUpdate(sql);
//            System.out.println("Table deleted in given database");

// INSERTING DATA //

//            String ingredientItem = "INSERT INTO ingredients (name) VALUES (?)";
//            PreparedStatement statement1 = conn.prepareStatement((ingredientItem));
//
//            String carcinogenItem = "INSERT INTO carcinogens (agent, classification) VALUES (?,?)";
//            PreparedStatement statement2 = conn.prepareStatement((carcinogenItem));

//  CREATE TABLE //

//            stmt = conn.createStatement();

//            String ingredients = "CREATE TABLE INGREDIENTS" +
//                    "(id INTEGER not NULL, " +
//                    " name VARCHAR(255), " +
//                    " PRIMARY KEY ( id ))";

//            String carcinogens = "CREATE TABLE CARCINOGENS" +
//                    "(id INTEGER not NULL, " +
//                    " agent VARCHAR(255), " +
//                    " info VARCHAR(255), " +
//                    " classification INTEGER not NULL, " +
//                    " PRIMARY KEY ( id ))";
//
//
//            //stmt.executeUpdate(ingredients);
//            stmt.executeUpdate(carcinogens);



//            stmt.executeBatch();
//
//            conn.commit();
//            conn.close();

