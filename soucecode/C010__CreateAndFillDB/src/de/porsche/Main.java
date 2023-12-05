package de.porsche;

import com.kwerlingit.CreateAndFillDatabase;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    static final String DB_URL = "jdbc:h2:./testdb/testdb";
//    static final String DB_URL = "jdbc:h2:mem:test";
    // "jdbc:h2:mem:mymemdb:"
    // "jdbc:h2:./testdb"
    // "jdbc:h2:///c/temp/testdb"
    static final String USER = "sa";
    static final String PASS = "1234";

    public static void main(String[] args) {

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            CreateAndFillDatabase doDB = new CreateAndFillDatabase( conn );
            
            System.out.println("Successfully created the DB ( " + DB_URL + " ).");

        } catch ( Exception ex ) {
            System.err.println("ERROR: " + ex.getMessage());
        }
    }
}