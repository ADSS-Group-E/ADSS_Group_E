package DataAccessLayer;

import java.io.File;
import java.sql.*;

public class Repo {
    private static final String databaseName = "Workers_Transport.db";

    public static Connection openConnection() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
            connection.createStatement().execute("PRAGMA foreign_keys = ON");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void createDatabase()
    {
        Connection conn=openConnection();
        try (Statement stmt = conn.createStatement();) {
            createWorkers(conn);
            createBankAccount(conn);
            createAvailableWorkDays(conn);
            createQualifications(conn);
            createHiringConditions(conn);
            createWorkerConstraints(conn);
            //createWorkerRoles(conn);
            createDrivers(conn);
            createTruck(conn);
            createLocations(conn);
            createOrders(conn);
            createItemsForOrder(conn);
            createDeliveries(conn);
            createOrdersForDelivery(conn);
            createLocationsForDelivery(conn);
            createShifts(conn);
            createWorkersShifts(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static boolean openDatabase() {
        File f = new File(databaseName);
        if (!f.exists())
        {
            createDatabase();
            return false;
        }
        return true;

    }

    public static void createTruck(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS Trucks " +
                    "(ID VARCHAR(100) PRIMARY KEY NOT NULL," +
                    "MODEL           VARCHAR(100)    NOT NULL, " +
                    "NETO_WEIGHT         DOUBLE NOT NULL ," +
                    "MAX_WEIGHT         DOUBLE NOT NULL, " +
                    "ISUSED INT NOT NULL)";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //TODO : I changed the table to be BankAccounts and not BankAccount
    public static void createBankAccount(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = """
                    CREATE TABLE IF NOT EXISTS BankAccounts  (
                    ID	TEXT NOT NULL,
                    Bank_Name	TEXT NOT NULL,
                    Branch	TEXT NOT NULL,
                    BankAccountID	TEXT NOT NULL,
                    FOREIGN KEY (ID) REFERENCES Workers(ID) ON DELETE CASCADE,
            PRIMARY KEY(ID))
            """
                   ;
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createAvailableWorkDays(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = """
                            CREATE TABLE IF NOT EXISTS "AvailableWorkingDays" (
                            	"ID"	TEXT NOT NULL,
                            	"Day"	TEXT NOT NULL,
                            	"Shift_Type"	TEXT NOT NULL,
                            	"IsAvailable"	INTEGER NOT NULL,
                            	PRIMARY KEY("ID","Day","Shift_Type")
                            )
                    """
                    ;
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createQualifications(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = """
                                CREATE TABLE IF NOT EXISTS "Qualifications" (
                                	"ID"	TEXT NOT NULL,
                                	"Qualification"	TEXT NOT NULL,
                                	FOREIGN KEY (ID) REFERENCES Workers(ID) ON DELETE CASCADE ,
                                	PRIMARY KEY("ID","Qualification")
                                )
                    """
                    ;
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void createLocations(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS Locations " +
                    "(ID INT PRIMARY KEY NOT NULL," +
                    "NAME           VARCHAR(100)    NOT NULL, " +
                    "ADDRESS         VARCHAR(100) NOT NULL ," +
                    "TEL_NUMBER         VARCHAR(100) NOT NULL, "+
                    "CONTACT_NAME  VARCHAR(100) NOT NULL,"+
                    "SHIIPING_EREA VARCHAR(100) NOT NULL )";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createOrders(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS Orders" +
                    "(ID INT PRIMARY KEY NOT NULL," +
                    "SUPPLIER           VARCHAR(100)    NOT NULL, " +
                    "TARGET_LOCATION         INT NOT NULL ," +
                    "TOTAL_WEIGHT         DOUBLE NOT NULL,"+
                    "FOREIGN KEY (TARGET_LOCATION) REFERENCES Locations(ID) ON DELETE Restrict )";
            stmt.executeUpdate(sql1);
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createItemsForOrder(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS ItemsForOrder " +
                    "(ORDER_ID INT NOT NULL," +
                    "ITEM   VARCHAR(100)     NOT NULL, " +
                    "QUANTITY         INT NOT NULL,"+
                    " PRIMARY KEY (ORDER_ID, ITEM),"+
                    "FOREIGN KEY (ORDER_ID) REFERENCES Orders(ID) ON DELETE RESTRICT )";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createDrivers(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS Drivers" +
                    "(ID TEXT PRIMARY KEY NOT NULL," +
                    "License_Type VARCHAR (10)   NOT NULL, " +
                    "Expiration_Date DATE NOT NULL," +
                    "STATUS INT NOT NULL,"+
                    "FOREIGN KEY (ID) REFERENCES Workers (ID) ON DELETE CASCADE )";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createDeliveries(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS Deliveries" +
                    "(ID VARCHAR(100) PRIMARY KEY NOT NULL," +
                    "DELIVERY_DATE DATE    NOT NULL, " +
                    "DELIVER_TIME  TIME NOT NULL ," +
                    "DRIVER_ID TEXT NOT NULL, "+
                    "SOURCE_LOCATION INT NOT NULL, "+
                    "WEIGHT DOUBLE NOT NULL, "+
                    "TRUCK_ID VARCHAR (100) NOT NULL, "+
                    "STATUS VARCHAR (100) NOT NULL,"+
                    "FOREIGN KEY (DRIVER_ID) REFERENCES Drivers(ID) ON DELETE RESTRICT ,"+
                    "FOREIGN KEY (SOURCE_LOCATION) REFERENCES Locations(ID) ON DELETE RESTRICT ,"+
                    "FOREIGN KEY (TRUCK_ID) REFERENCES Trucks(ID) ON DELETE RESTRICT )";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createOrdersForDelivery(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS OrdersForDelivery" +
                    "(DELIVERY_ID VARCHAR(100)  NOT NULL," +
                    "ORDER_ID INT  NOT NULL, "+
                    "PRIMARY KEY (DELIVERY_ID, ORDER_ID),"+
                    "FOREIGN KEY (DELIVERY_ID) REFERENCES Deliveries(ID) ON DELETE RESTRICT ,"+
                    "FOREIGN KEY (ORDER_ID) REFERENCES Orders(ID) ON DELETE RESTRICT )";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createLocationsForDelivery(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS LocationsForDelivery " +
                    "(DELIVERY_ID VARCHAR(100)  NOT NULL," +
                    "LOCATION_ID INT  NOT NULL, " +
                    "PRIMARY KEY (DELIVERY_ID, LOCATION_ID),"+
                    "FOREIGN KEY (DELIVERY_ID) REFERENCES Deliveries(ID) ON DELETE RESTRICT ,"+
                    "FOREIGN KEY (LOCATION_ID) REFERENCES Locations(ID) ON DELETE RESTRICT )";


            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createWorkers(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

                String sql1 = """
                        CREATE TABLE IF NOT EXISTS "Workers" (
                        	"ID"	TEXT NOT NULL,
                        	"BranchID"	INTEGER NOT NULL,
                        	"First_Name"	INTEGER NOT NULL,
                        	"Last_Name"	INTEGER NOT NULL,
                        	"Start_Working_Day"	DATE NOT NULL,
                        	PRIMARY KEY("ID")
                        )
                        """;
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createDriver(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = """
                    CREATE TABLE IN NOT EXISTS "Drivers" (
                    	"ID"	TEXT NOT NULL,
                    	"licenseType"	TEXT NOT NULL,
                    	"expLicenseDate"	DATE NOT NULL,
                    	"busy"	INTEGER,
                    	PRIMARY KEY("ID")
                    )
                        """;
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createHiringConditions(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = """
                    CREATE TABLE IF NOT EXISTS "HiringConditions" (
                    	"ID"	TEXT NOT NULL,
                    	"salaryPerHour"	REAL NOT NULL,
                    	"fund"	TEXT NOT NULL,
                    	"vacationDays"	INTEGER NOT NULL,
                    	"sickLeavePerMonth"	INTEGER NOT NULL,
                    	PRIMARY KEY("ID"),
                    	FOREIGN KEY (ID) REFERENCES Workers(ID) ON DELETE CASCADE)
                    )
                        """;
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public static void createWorkerRoles(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS WorkersRoles" +
                    "(ID INT NOT NULL," +
                    "Role VARCHAR (20) NOT NULL," +
                    "PRIMARY KEY (ID,Role),"+
                    "FOREIGN KEY (ID) REFERENCES Workers(ID) ON DELETE CASCADE)";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void createWorkerConstraints(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS WorkersConstraints" +
                    "(ID INT NOT NULL," +
                    "DayConstraint VARCHAR (20) NOT NULL, " +
                    "KindConstraint VARCHAR (20) NOT NULL," +
                    "PRIMARY KEY (ID,DayConstraint,KindConstraint),"+
                    "FOREIGN KEY (ID) REFERENCES Workers(ID) ON DELETE CASCADE)";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
//Todo we stoppen in shift demands
    public static void createShiftDemands(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = """
                    CREATE TABLE IF NOT EXISTS ShiftDemands (
                                        	Date	DATE NOT NULL,
                                        	ShiftType	TEXT NOT NULL,
                                        	cashierAmount	INTEGER NOT NULL,
                                        	storeKeeperAmount	INTEGER NOT NULL,
                                        	arrangerAmount	INTEGER NOT NULL,
                                        	guardAmount	INTEGER NOT NULL,
                                        	assistantAmount	INTEGER NOT NULL,
                                        	deliveryRequired	INTEGER NOT NULL,
                                        	BranchID            INTEGER NOT NULL,
                                        	FOREIGN KEY (Date,ShiftType,BranchID) REFERENCES Shifts(Date,ShiftType,BranchID) ON DELETE CASCADE ,
                                        	PRIMARY KEY(Date,ShiftType,BranchID)
                                        )
                    """;
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createShifts(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = """
                    CREATE TABLE Shifts (
                    	Date	DATE NOT NULL,
                    	ShiftType	TEXT NOT NULL,
                    	BranchID	INTEGER NOT NULL,
                    	ShiftManagerID	TEXT NOT NULL,
                    	DriverID	TEXT,
                    	FOREIGN KEY(ShiftManagerID) REFERENCES Workers(ID) ON DELETE CASCADE ,
                    	FOREIGN KEY(DriverID) REFERENCES Drivers(ID) ON DELETE CASCADE ,
                    	PRIMARY KEY(Date,ShiftType,BranchID)
                    )
                    """;
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createWorkersShifts(Connection conn)  {
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "CREATE TABLE IF NOT EXISTS WorkersShifts" +
                    "(Date DATE NOT NULL," +
                    "Kind VARCHAR (20) NOT NULL, " +
                    "ID INTEGER NOT NULL, "+
                    "Role VARCHAR (20) NOT NULL,"+
                    "PRIMARY KEY (Date,Kind,ID),"+
                    "FOREIGN KEY (Date,Kind) REFERENCES Shifts(Date,Kind) ON DELETE CASCADE ," +
                    "FOREIGN KEY (ID) REFERENCES Workers(ID) ON DELETE CASCADE)";
            stmt.executeUpdate(sql1);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void deleteDataBase()
    {
        Connection conn=openConnection();
        try (Statement stmt = conn.createStatement();) {

            String sql1 = "DROP TABLE OrdersForDelivery";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE LocationsForDelivery";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE ItemsForOrder";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE Deliveries";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE Trucks";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE Orders";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE Locations";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE Drivers";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE WorkersShifts";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE Shifts";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE WorkersConstraints";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE WorkersRoles";
            stmt.executeUpdate(sql1);
            sql1 = "DROP TABLE Workers";
            stmt.executeUpdate(sql1);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


}



