package PresentationLayer;


import BusinessLayer.Inventory.Controllers.*;
import BusinessLayer.OrderFromReportHandler;
import BusinessLayer.Supplier.SupplierController;
import PresentationLayer.Inventory.DataTransferObjects.CategoryDTO;
import PresentationLayer.Inventory.DataTransferObjects.DiscountDTO;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;
import PresentationLayer.Supplier.ServiceController;
import PresentationLayer.Workers_Transport.WorkerDTO;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.*;

/**
 * This class represents the command line interface
 */
public class CommandLineInterface {


    private final InventoryFacade inventoryFacade;
    private final OrderFromReportHandler orderFromReportHandler;
    private final ServiceController serviceController;
    private final LoginOptionsMenu loginOptionsMenu;
    private final MainOptionsMenu mainOptionsMenu;
    private WorkerDTO loggedInWorker;

    // Getters
    public InventoryFacade getFacade() {
        return inventoryFacade;
    }

    public OrderFromReportHandler getOrderFromReportHandler() {
        return orderFromReportHandler;
    }

    public ServiceController getServiceController() {
        return serviceController;
    }



    public WorkerDTO getLoggedInWorker() {
        return loggedInWorker;
    }

    public void setLoggedInWorker(WorkerDTO loggedInWorker) {
        this.loggedInWorker = loggedInWorker;
    }

    public CommandLineInterface() {
        CategoryController cCont = new CategoryController();
        ProductController pCont = new ProductController(cCont);
        ReportController rCont = new ReportController();

        DiscountController dCont = new DiscountController(pCont);
        pCont.setDiscountController(dCont);

        SupplierController sCont = new SupplierController();

        inventoryFacade = new InventoryFacade(pCont, rCont, cCont, dCont);
        orderFromReportHandler = new OrderFromReportHandler(rCont,sCont);
        serviceController = new ServiceController(sCont);

        loginOptionsMenu = new LoginOptionsMenu(this);
        mainOptionsMenu = new MainOptionsMenu(this);
    }

    // Loads sample data because there is no data access layer in this part of the project.
    public void loadSampleData(){
        try{
            String url = "jdbc:sqlite::resource:module.db";


            String schema;

            // the stream holding the file content
            InputStream is = getClass().getClassLoader().getResourceAsStream("schema.sql");

            //Build string from the input stream
            StringBuilder textBuilder = new StringBuilder();
            try (Reader reader = new BufferedReader(new InputStreamReader
                    (is, Charset.forName(StandardCharsets.UTF_8.name())))) {
                int c = 0;
                while ((c = reader.read()) != -1) {
                    textBuilder.append((char) c);
                }
            }
            schema = textBuilder.toString();
            schema = schema.replace("\n","").replace("\r", "").replace("\t", " ");

            // Get individual statements
            String[] sqls = schema.split(";");
            try (Connection conn = DriverManager.getConnection(url);)
            {
                for (String sql:
                     sqls) {
                    Statement stmt = conn.createStatement();
                    stmt.execute(sql);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        inventoryFacade.addCategory(1,"Juice");
        inventoryFacade.addCategory(new CategoryDTO(2,"<500 ML",1));
        inventoryFacade.addProduct(new ProductDTO(1, "Test Juice", "AB01","B13", "Test Company", 10.1, 5,2, -1));
        inventoryFacade.addItemToStore(1,10, 10.5,  LocalDateTime.of(2021,4,24,16,0));
        inventoryFacade.addItemToStorage(1,3, 11, LocalDateTime.of(2021,4,25,16,0));
        inventoryFacade.addItemToStore(1,20, 11.5, LocalDateTime.of(2021,4,1,16,0));
        inventoryFacade.addItemToStorage(1,4, 12, LocalDateTime.of(2021,4,1,16,0));
        ArrayList<Integer> cids = new ArrayList<>();
        ArrayList<Integer> pids = new ArrayList<>();
        cids.add(1);
        pids.add(1);

        inventoryFacade.addDiscount(new DiscountDTO(1,"Test Spring Discount", 0.1,
                            LocalDateTime.of(2021,4,1,16,0),
                            LocalDateTime.of(2022,5,1,16,0)
                ),
                        cids,
                        pids);

        serviceController.initialize();
    }

    public void run() {
        System.out.println("SUPER-LI Management System");
        loginOptionsMenu.enter();
    }

    public MainOptionsMenu getMainOptionsMenu() {
        return mainOptionsMenu;
    }
}


