package BusinessLayer.Inventory;

import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.Controllers.ReportController;
import BusinessLayer.Inventory.Product;
import BusinessLayer.Inventory.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReportControllerTest {

    private ReportController reportController;

    @BeforeEach
    public void setUp() {
        reportController = new ReportController();
    }

    @Test
    void generateStockReport() {
        assertNull(reportController.getReport(1));
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(1, "Test Juice", "AB01","B13", "Test Company",10.5, 10.1, 5,new Category(1,"Juice")));
        Report report = reportController.generateStockReport(products);
        assertEquals(report,reportController.getReport(1));
        assertEquals(report.getType(),"Stock");
    }

    @Test
    void generateInvalidsReport() {
        assertNull(reportController.getReport(1));
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(1, "Test Juice", "AB01","B13", "Test Company",10.5, 10.1, 5,new Category(1,"Juice")));
        Report report = reportController.generateInvalidsReport(products);
        assertEquals(report,reportController.getReport(1));
        assertEquals(report.getType(),"Invalids");
    }

    @Test
    void removeReport() {
        assertNull(reportController.getReport(1));
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(1, "Test Juice", "AB01","B13", "Test Company",10.5, 10.1, 5,new Category(1,"Juice")));
        Report report = reportController.generateStockReport(products);
        assertEquals(report,reportController.getReport(1));

        reportController.removeReport(1);
        assertNull(reportController.getReport(1));
    }

    @Test
    void generateLowStockReport() {
        assertNull(reportController.getReport(1));
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(1, "Test Juice", "AB01","B13", "Test Company",10.5, 10.1, 5,new Category(1,"Juice")));
        Report report = reportController.generateLowStockReport(products);
        assertEquals(report,reportController.getReport(1));
        assertEquals(report.getType(),"Low Stock");
    }
}