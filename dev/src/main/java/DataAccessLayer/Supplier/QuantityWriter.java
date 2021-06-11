package DataAccessLayer.Supplier;

import PresentationLayer.Supplier.DataTransferObjects.DiscountStepDTO;
import PresentationLayer.Supplier.DataTransferObjects.QuantityWriterDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

class QuantityWriter {
    private DBConnection db;
    private Connection c = null;
    private Statement stmt = null;

    QuantityWriter(DataAccessLayer.Supplier.DBConnection dbConnection) {
        db = dbConnection;
    }

    private void close() throws SQLException {
        stmt.close();
        c.commit();
        c.close();
        c  = null;
        stmt = null;
    }

    void insert(QuantityWriterDTO quan) {
        try {
            String[] key = {"ID"};
            int generatedId = -1;
            c = db.connect();
            stmt = c.createStatement();
            String sql = String.format("INSERT INTO QuantityWriter (minPriceDiscount, regularCostumerDiscount, companyNumber) " +
                    "VALUES (%d, %d, %d);", quan.getMinPriceDiscount(), quan.getRegularCostumerDiscount(), quan.getCompanyNumber());
            c.prepareStatement(sql, key);
            stmt.executeUpdate(sql);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
            quan.setId(generatedId);
            key = new String[]{"QWID"};
            int id;
            for (DiscountStepDTO step : quan.getDiscounts()) {
                step.setQuantityWriterId(generatedId);
                sql = String.format("INSERT INTO StepDiscount (stepPrice, precentage, QWID) " +
                        "VALUES (%d, %d, %d);", step.getStepPrice(), step.getPercentage(), generatedId);
                c.prepareStatement(sql, key);
                stmt.executeUpdate(sql);
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                    step.setQuantityWriterId(id);
                }
            }
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    QuantityWriterDTO select(int num) {
        HashMap<QuantityWriterDTO, ArrayList<DiscountStepDTO>> quan = new HashMap<>();
        QuantityWriterDTO result = null;
        try {
            c = db.connect();
            stmt = c.createStatement();
            String sql = "SELECT * FROM QuantityWriter LEFT JOIN StepDiscount on QuantityWriter.ID = StepDiscount.QWID";
            ResultSet rs = stmt.executeQuery(sql);
            DiscountStepDTO discount;
            QuantityWriterDTO writer;
            while (rs.next()) {
                writer = new QuantityWriterDTO(rs.getInt("ID"), rs.getInt("companyNumber"), rs.getInt("regularCostumerDiscount"), rs.getInt("minPriceDiscount"));
                if (!quan.containsKey(writer)) {
                    quan.put(writer, new ArrayList<>());
                }
                discount = new DiscountStepDTO(
                        rs.getInt("ID"), rs.getInt("stepPrice"), rs.getInt("precentage"), rs.getInt("QWID"));
                quan.get(writer).add(discount);
            }
            for (QuantityWriterDTO quantityWriterDTO : quan.keySet())
                result = new QuantityWriterDTO(quantityWriterDTO.getId(), quantityWriterDTO.getCompanyNumber(), quantityWriterDTO.getRegularCostumerDiscount(), quantityWriterDTO.getMinPriceDiscount(), quan.get(quantityWriterDTO));
            close();
        }
        catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return result;
    }
}

