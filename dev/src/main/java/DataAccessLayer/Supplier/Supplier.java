package DataAccessLayer.Supplier;

import PresentationLayer.Supplier.DataTransferObjects.OrderDTO;
import PresentationLayer.Supplier.DataTransferObjects.SupplierDTO;
import PresentationLayer.Supplier.DataTransferObjects.SupplierItemDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

class Supplier {
    private DBConnection db;
    private Connection c = null;
    private Statement stmt = null;

    Supplier(DBConnection dbConnection) {
        db = dbConnection;
    }

    private void close() throws SQLException {
        stmt.close();
        c.commit();
        c.close();
        c  = null;
        stmt = null;
    }

    void insert(SupplierDTO sup) {
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = String.format("INSERT INTO Supplier (companyNumber, name, paymentMethod, bankAccount) " +
                    "VALUES (%d, '%s', '%s', '%s' );", sup.getCompanyNumber(), sup.getName(), sup.getPaymentMethod(), sup.getBankAccount());
            stmt.executeUpdate(sql);
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    ArrayList<SupplierDTO> select() {
        ArrayList<SupplierDTO> suppliers = new ArrayList<>();
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = "SELECT * FROM Supplier;";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                suppliers.add(new SupplierDTO(
                        rs.getInt("companyNumber"), rs.getString("name"), rs.getString("bankAccount"), rs.getString("paymentMethod"),
                        new ArrayList<>()));
            }
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return suppliers;
    }

    ArrayList<SupplierItemDTO> selectAll() {
        HashMap<SupplierDTO, ArrayList<OrderDTO>> supNorders = new HashMap<>();
        HashMap<OrderDTO, ArrayList<SupplierItemDTO>> orders = new HashMap<>();
        ArrayList<SupplierItemDTO> result = new ArrayList<>();
        SupplierDTO supplier;
        OrderDTO order;
        SupplierItemDTO item;
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = "SELECT * FROM SupplierItem LEFT JOIN Order O on SupplierItem.orderID = O.orderID LEFT JOIN " +
                    "Supplier S on O.companyNumber = S.companyNumber;";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                supplier = new SupplierDTO(rs.getInt("companyNumber"), rs.getString("name"), rs.getString("bankAccount"), rs.getString("paymentMethod"), new ArrayList<>());
                if (!supNorders.containsKey(supplier))
                    supNorders.put(supplier, new ArrayList<>());
                order = new OrderDTO(rs.getInt("companyNumber"), rs.getString("date"), rs.getInt("periodicDelivery"), rs.getInt("needsDelivery"), new ArrayList<>());
                if (!supNorders.get(supplier).contains(order))
                    supNorders.get(supplier).add(order);
                item = new SupplierItemDTO(rs.getInt("orderID"), rs.getString("name"), rs.getInt("quantity"), rs.getInt("price"), rs.getString("supplierCN"));
                if (!orders.get(order).contains(item))
                    orders.get(order).add(item);
            }
            for (SupplierDTO sup : supNorders.keySet()) {
                for (int i = 0; i < supNorders.get(sup).size(); i++) {
                    supNorders.get(sup).get(i).getOrderItems().addAll(orders.get(supNorders.get(sup).get(i)));
                }
            }
            for (SupplierDTO sup : supNorders.keySet()) {
                sup.getOrders().addAll(supNorders.get(sup));

            }
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return result;
    }
}

