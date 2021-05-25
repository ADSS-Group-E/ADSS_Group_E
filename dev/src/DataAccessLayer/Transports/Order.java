package DataAccessLayer.Transports;

import BussinessLayer.WorkersPackage.Worker;
import DataAccessLayer.Repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Order {
    /*
    "(ID INT PRIMARY KEY NOT NULL," +
                    "SUPPLIER           VARCHAR(100)    NOT NULL, " +
                    "TARGET_LOCATION         INT NOT NULL ," +
                    "TOTAL_WEIGHT         DOUBLE NOT NULL,"
     */
    public static void insertOrder(DTO.Order o) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String query = "INSERT OR IGNORE INTO Orders VALUES (?, ?, ? ,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1,o.id );
            stmt.setString(2, o.supplierId);
            stmt.setInt(3, o.locationId);
            stmt.setDouble(4,o.totalWeight);
            stmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            throw e;        }
    }

    public static void insertItemsForOrders(DTO.ItemsForOrders i) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String query = "INSERT OR IGNORE INTO ItemsForOrder VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1,i.orderId );
            stmt.setString(2, i.item);
            stmt.setInt(3, i.qunt);
            stmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            throw e;        }
    }

    public static BussinessLayer.DeliveryPackage.Order checkOrder(int id) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From Orders WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);

            ResultSet results = pst.executeQuery();
            if(results.next()==false)
                return null;
            HashMap<String,Integer> items=getItemsForOrder(id);
            return new BussinessLayer.DeliveryPackage.Order(results.getInt(1),items,results.getString(2),results.getInt(3),results.getDouble(4));
        } catch (Exception e) {
            throw e;        }
    }

    public static boolean checkItem(int id,String item) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From ItemsForOrder WHERE ORDER_ID=? AND ITEM=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            pst.setString(2,item);

            ResultSet results = pst.executeQuery();
            if(results.next()==false)
                return false;
        } catch (Exception e) {
            throw e;        }
        return true;
    }

    public static HashMap<String,Integer> getItemsForOrder(int id) throws SQLException {
        HashMap<String,Integer> ItemsMap=new HashMap<>();
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From ItemsForOrder WHERE ORDER_ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);

            ResultSet results = pst.executeQuery();
            if(results.next()==false)
                return null;
            String  item = results.getString(2);
            int qun=results.getInt(3);
            ItemsMap.put(item,qun);
            while (results.next()) {
                String  item1 = results.getString(2);
                int qun1=results.getInt(3);
                ItemsMap.put(item1,qun1);
            }
        } catch (Exception e) {
            throw e;        }
        return ItemsMap;
    }

    public static void deleteOrder(int id) throws Exception {
        try (Connection conn = Repo.openConnection()) {
            String sql1 = "DELETE FROM ItemsForOrder WHERE ORDER_ID=? ";
            PreparedStatement pst1 = conn.prepareStatement(sql1);
            pst1.setInt(1,id);
            pst1.executeUpdate();
            String sql = "DELETE FROM Orders WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);

            pst.executeUpdate();

        } catch (Exception e) {
            throw new Exception("can't delete a order that is used with a delivery/Orders or has items in it.");
        }

    }

    public static void deleteItem(int id,String item) throws Exception {
        try (Connection conn = Repo.openConnection()) {
            String sql = "DELETE FROM ItemsForOrder WHERE ORDER_ID=? AND ITEM=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            pst.setString(2,item);

            pst.executeUpdate();

        } catch (Exception e) {
            throw new Exception("can't delete a order that is used with a delivery/Orders or has items in it.");
        }

    }

    public static void updatQunt(int id, String item, int qunt) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "UPDATE ItemsForOrder SET QUINTITY =? WHERE ORDER_ID=? AND ITEM=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,qunt);
            pst.setInt(2,id);
            pst.setString(3,item);

            pst.executeUpdate();

        } catch (Exception e) {
            throw e;        }
    }

    public static void updateTotal(int id, double total) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "UPDATE Orders SET TOTAL_WEIGHT =? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setDouble(1,total);
            pst.setInt(2,id);

            pst.executeUpdate();

        } catch (Exception e) {
            throw e;        }
    }

    public static void printOrders() throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From Orders ";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet results = pst.executeQuery();
            while (results.next()) {
                int  id = results.getInt(1);
                String supId = results.getString(2);
                int location=results.getInt(3);
                double total=results.getDouble(4);
                System.out.println("Order "+id);
                printItems(id);
                System.out.println("supplier Id: "+supId+"\ntotal weight: "+total+"\n");

            }


        } catch (Exception e) {
            throw e;        }
    }

    public static void printItems(int id) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From ItemsForOrder WHERE ORDER_ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet results = pst.executeQuery();
            System.out.print("items: ");
            while (results.next()) {
                String  item = results.getString(2);
                int qun=results.getInt(3);
                System.out.print("[ "+item+","+qun+" ] ");
            }
            System.out.println();

        } catch (Exception e) {
            throw e;        }
    }
}