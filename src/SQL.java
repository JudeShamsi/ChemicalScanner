import java.sql.*;

public class SQL {

    void createTable(String table_name){
        Statement stmt = null;
        Connection conn = null;

        try {
            stmt = conn.createStatement();

            String ingredients = "CREATE TABLE INGREDIENTS" +
                    "(id INTEGER not NULL, " +
                    " name VARCHAR(255), " +
                    " PRIMARY KEY ( id ))";





        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}

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