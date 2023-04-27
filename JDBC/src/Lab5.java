import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Lab5 {

public static void main(String[] args) throws ClassNotFoundException {
    
    Connection c = null;
    Statement stmt = null;
    ResultSet rs = null;
    Scanner scanner = new Scanner(System.in);

    try {
        // Connecting database:
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:headphones.db");

        // Java App Menu (GUI):
        while (true) {
            System.out.println("=============================================");
            System.out.println("|Welcome to the Headphones Database Program!|");
            System.out.println("=============================================");
            System.out.println("| Select from one of the options below:     |");
            System.out.println("| 1. View existing tables                   |");
            System.out.println("| 2. Create a new table                     |");
            System.out.println("| 3. Insert data into a table               |");
            System.out.println("| 4. Run a query                            |");
            System.out.println("| 0. Quit application                       |");
            System.out.println("=============================================");
            System.out.println("Print: ");
            int choice = scanner.nextInt();

            // User options:
            if (choice == 0) {
                // Exit menu:
                System.out.println("Shutting down... ");
                break;
            } else if (choice == 1) {
                // View tables present in headphones database:
                viewExistingTables(c);
            } else if (choice == 2) {
                // Create a brand new table within the headphones database:
                createNewTable(c);
            } else if (choice == 3) {
                // Insert data into table within the headphones database:
                insertIntoTable(c);
            } else if (choice == 4) {
                // Run one of the selected queries (10 from the previous lab):
                System.out.println("Select query (#)::");
                printQueries();
                int queryChoice = scanner.nextInt();

                // If query out of range, restart
                if (queryChoice < 1 || queryChoice > 10) {
                    System.out.println("Query # out of range. Try sleecting a number from 1-10.");
                    continue;
                }

                // Run the query
                printQueryResults(c, QUERIES[queryChoice - 1]);
            } else {
                System.out.println("Invalid choice. Please try again.");
                continue;
            }
        }
      // Catch Exceptions
    } catch (SQLException e) {
        System.err.println("Problem Encountered");
    }
    System.out.println("Opened Database Successfully");
}

    // private String object to store Queries
    private static final String[] QUERIES = {
        "SELECT * FROM headphone WHERE type = 'On-Ear';",
        "SELECT * FROM headphone WHERE wireless != 'Yes';",
        "SELECT * FROM headphone h JOIN company c ON h.companyID = c.companyID WHERE c.companyName = 'Sony';",
        "SELECT * FROM headphone WHERE price BETWEEN 100 AND 300;",
        "SELECT * FROM headphone WHERE weight < 0.5;",
        "SELECT * FROM headphone WHERE microphone = 'Yes';",
        "SELECT * FROM headphone h JOIN year y ON h.yearID = y.yearID WHERE y.year BETWEEN 2010 AND 2015;",
        "SELECT * FROM headphone WHERE material LIKE '%Foam%';",
        "SELECT companyName FROM company JOIN year ON company.yearID = year.yearID WHERE year - 50 >= year.year - 50;",
        "SELECT * FROM country WHERE population > 5000000;"
    };

    // method for accessing 10 query results:
    private static void printQueries() {
        String[] QUERIES_METH = Lab5.QUERIES;

        for (int i = 0; i < QUERIES_METH.length; i++) {
            System.out.println((i + 1) + ". " + QUERIES_METH[i]);
        }
    }
    
    // method for printing the 10 query results found in the previous lab (welcome GUI option #4): 
    private static void printQueryResults(Connection c, String query) {
    try {
        
        // Create statement to the SQL query with:
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        // Process results and run queries:
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rs.getString(i) + "\t");
            }
            System.out.println("");
        }
        System.out.println("");
        
      // errors
    } catch (SQLException ex) {
        System.err.println("Error. Failed to execute the query.");
        ex.printStackTrace();
    }
}

    // method for creating a brand new Table within the headphones database (welcome GUI option #2):  
    private static void createNewTable(Connection c) {
        
        Scanner scanner = new Scanner(System.in);

        try {
            // Step 1: Ask user for new table name:
            System.out.println("Enter name for new table:");
            String tableName = scanner.nextLine();

            // Step 2: Ask user for new columns names + types:
            List<String> columns = new ArrayList<>();
            List<String> types = new ArrayList<>();
            // run infinite loop:
            while (true) {
                System.out.println("Enter the name of a column (or type 'done' to finish):");
                String columnName = scanner.nextLine();
                if (columnName.equals("done")) {
                    break;
                }
                System.out.println("Enter the data type of column '" + columnName + "':");
                String columnType = scanner.nextLine();
                columns.add(columnName);
                types.add(columnType);
            }

            // Step 3: Call the "create table" statement from SQL
            StringBuilder sql = new StringBuilder("CREATE TABLE ");
            sql.append(tableName).append(" (");
            // Use data input by user to print appropriate table (constructing SQL statement)
            for (int i = 0; i < columns.size(); i++) {
                sql.append(columns.get(i)).append(" ").append(types.get(i));
                if (i < columns.size() - 1) {
                    sql.append(", ");
                }
            }
            sql.append(")");

            // Step 4: Run statement
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql.toString());

            // Done
            System.out.println("Table created!");
            
          // Errors:
        } catch (SQLException ex) {
            System.err.println("Error. Couldn't make table.");
            ex.printStackTrace();
        }
    }
    
    // method for Displaying a pre-existing Table within the headphones database (welcome GUI option #1):    
    private static void viewExistingTables(Connection c) {
        try {

            // Use SQL stetement to grab table names (new statement):
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name!='sqlite_sequence' ORDER BY name");

            // Grab table names (list of strings):
            List<String> tableNames = new ArrayList<>();
            while (rs.next()) {
                String tableName = rs.getString("name");
                tableNames.add(tableName);
            }


            // Prompt user to choose table number:
            Scanner scanner = new Scanner(System.in);
            System.out.println("Select table (#):");

            // Display Requested Table
            for (int i = 0; i < tableNames.size(); i++) {
                System.out.println((i + 1) + ". " + tableNames.get(i));
            }
            int choice = scanner.nextInt();


            // Through SQL query, grab selected table's data:
            String selectedTable = tableNames.get(choice - 1);
            ResultSet tableRs = stmt.executeQuery("SELECT * FROM " + selectedTable);
            ResultSetMetaData rsmd = tableRs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // FOR COSMETIC PURPOSES ONLY!!
            // This builds a GUI from text in command prompt
            // Print column headers:
            System.out.print("| ");
            for (int i = 1; i <= columnCount; i++) {
                String columnName = rsmd.getColumnName(i);
                System.out.printf("%-20s | ", columnName);
            }
            System.out.println("");
            // Print rows:
            while (tableRs.next()) {
                System.out.print("| ");
                for (int i = 1; i <= columnCount; i++) {
                    String value = tableRs.getString(i);
                    System.out.printf("%-20s | ", value);
                }
                System.out.println("");
            }

            System.out.println("");

          // Catch errors:
        } catch (SQLException ex) {
            System.err.println("Error. Couldn't view table.");
            ex.printStackTrace();
        }
    }

    // method for Inserting Data Into a pre-existing Table within the headphones database (welcome GUI option #3):
    private static void insertIntoTable(Connection c) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Step 1: Ask user for table name:
            System.out.println("Enter table name:");
            String tableName = scanner.nextLine();

            // Step 2: Get column names + types using SQL queries
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            List<String> columns = new ArrayList<>();
            List<String> types = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                columns.add(rsmd.getColumnName(i));
                types.add(rsmd.getColumnTypeName(i));
            }

            // Step 3: Ask user for data to insert:
            List<String> values = new ArrayList<>();
            for (int i = 0; i < columns.size(); i++) {
                System.out.println("Enter value for column '" + columns.get(i) + "' (" + types.get(i) + "):");
                String value = scanner.nextLine();
                values.add(value);
            }

            // Step 4: Construct the SQL "insert" statement
            StringBuilder sql = new StringBuilder("INSERT INTO ");
            sql.append(tableName).append(" (");
            for (int i = 0; i < columns.size(); i++) {
                sql.append(columns.get(i));
                if (i < columns.size() - 1) {
                    sql.append(", ");
                }
            }
            sql.append(") VALUES (");
            for (int i = 0; i < values.size(); i++) {
                sql.append("'").append(values.get(i)).append("'");
                if (i < values.size() - 1) {
                    sql.append(", ");
                }
            }
            sql.append(")");

            // Step 5: run statement
            stmt.executeUpdate(sql.toString());

            // done
            System.out.println("Data inserted!");
            
          // Error
        } catch (SQLException ex) {
            System.err.println("Error. Couldn't insert data into table.");
            ex.printStackTrace();
        }
    } 
    
}
