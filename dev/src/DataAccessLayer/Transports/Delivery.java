package DataAccessLayer.Transports;

import BussinessLayer.WorkersPackage.Worker;
import DataAccessLayer.Repo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Delivery {

    public static void insertDelivery(DTO.Delivery d) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String query = "INSERT OR IGNORE INTO Deliveries VALUES (?, ?, ? ,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, d.id);
            stmt.setDate(2, (Date) d.deliveryDay);
            stmt.setTime(3, d.leavingTime);
            stmt.setString(4, d.driverId);
            stmt.setInt(5, d.srcLocation);
            stmt.setDouble(6, d.weight);
            stmt.setString(7, d.truckId);
            stmt.setString(8, d.status);
            stmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            throw e;
        }
    }

    public static void insertOrdersForDeliveries(DTO.OrdersForDelivery o) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String query = "INSERT OR IGNORE INTO OrdersForDelivery VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,o.deliveryId);
            stmt.setInt(2, o.orederId);

            stmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            throw e;
        }
    }

    public static void insertDeliveryTargetLocation(DTO.DeliverytargetLocation d) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String query = "INSERT OR IGNORE INTO LocationsForDelivery VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,d.deliveryId);
            stmt.setInt(2,d.targetLocation);

            stmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            throw e;
        }
    }

    public static BussinessLayer.DeliveryPackage.Delivery checkDelivery(String id) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From Deliveries WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,id);

            ResultSet results = pst.executeQuery();
            if(results.next()==false)
                return null;
            List<Integer> locations=getTargetLocations(id);
            List<Integer> orders=getOrdersForDelivery(id);
             return new BussinessLayer.DeliveryPackage.Delivery(results.getString(1),results.getDate(2),results.getTime(3),results.getString(4),results.getInt(5),
                     locations,results.getDouble(6),results.getString(7),orders,results.getString(8));
            } catch (Exception e) {
            throw e;
        }
    }

    public static List<Integer> getTargetLocations(String id) throws SQLException {
        List<Integer> locations=new ArrayList<>();
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From LocationsForDelivery WHERE DELIVERY_ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,id);
            ResultSet results = pst.executeQuery();
            while (results.next()) {
                locations.add(results.getInt(2));
            }
        } catch (Exception e) {
            throw e;
        }
        return locations;
    }

    public static List<Integer> getOrdersForDelivery(String id) throws SQLException {
        List<Integer> orders=new ArrayList<>();
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From OrdersForDelivery WHERE DELIVERY_ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,id);
            ResultSet results = pst.executeQuery();
            while (results.next()) {
                orders.add(results.getInt(2));
            }
        } catch (Exception e) {
            throw e;
        }
        return orders;
    }

    public static void deleteDelivery(String id) throws Exception {
        try (Connection conn = Repo.openConnection()) {
            deleteLocationsForDel(id);
            deleteOrdersForDelivery(id);
            String sql1 = "DELETE FROM Deliveries WHERE ID=? ";
            PreparedStatement pst1 = conn.prepareStatement(sql1);
            pst1.setString(1,id);
            pst1.executeUpdate();

        } catch (Exception e) {
            throw new Exception("can't delete a order that is used with a delivery/Orders or has items in it.");
        }

    }

    public static void deleteOrdersForDelivery(String id) throws Exception {
        try (Connection conn = Repo.openConnection()) {
            String sql1 = "DELETE FROM OrdersForDelivery WHERE DELIVERY_ID=? ";
            PreparedStatement pst1 = conn.prepareStatement(sql1);
            pst1.setString(1,id);
            pst1.executeUpdate();

        } catch (Exception e) {
            throw new Exception("can't delete a order that is used with a delivery/Orders or has items in it.");
        }

    }

    public static void deleteLocationsForDel(String id) throws Exception {
        try (Connection conn = Repo.openConnection()) {
            String sql1 = "DELETE FROM LocationsForDelivery WHERE DELIVERY_ID=? ";
            PreparedStatement pst1 = conn.prepareStatement(sql1);
            pst1.setString(1,id);
            pst1.executeUpdate();

        } catch (Exception e) {
            throw new Exception("can't delete a order that is used with a delivery/Orders or has items in it.");
        }

    }

    public static boolean checkDTforDate(String id, Date date, String driver, String truck) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql1 = "SELECT * From Deliveries WHERE ID<>? AND(  (DELIVERY_DATE=? AND DRIVER_ID=?) OR(DELIVERY_DATE=? AND TRUCK_ID=?)) ";
            PreparedStatement pst1 = conn.prepareStatement(sql1);
            pst1.setString(1,id);
            pst1.setDate(2,date);
            pst1.setString(3,driver);
            pst1.setDate(4,date);
            pst1.setString(5,truck);
            ResultSet results1 = pst1.executeQuery();
            if(results1.next()==false)
                return false;
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public static void printDeliveries() throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From Deliveries ";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet results = pst.executeQuery();
            while (results.next()) {
                String  id = results.getString(1);
                Date date = results.getDate(2);
                Time time=results.getTime(3);
                int driver=results.getInt(4);
                int srcLoc=results.getInt(5);
                double weight=results.getDouble(6);
                String truck=results.getString(7);
                String status=results.getString(8);
                System.out.println("Delivery "+id+"\ndelivery date: "+date+"\nleaving time: "+time+"\ndriver ID: "+driver+"\nsource location: "+srcLoc);
                printTargetLocation(id);
                System.out.println("delivery weight: "+weight+"\ntruck id: "+truck);
                printOrdersForDel(id);
                System.out.println("status: "+status+"\n");

            }

        } catch (Exception e) {
            throw e;
        }
    }

    public static void updateDeliveryDay(String id, java.util.Date deliveryDay) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "UPDATE Deliveries SET DELIVERY_DATE =? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setDate(1,new Date(deliveryDay.getTime()));
            pst.setString(2,id);

            pst.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static void updateLeavingTime(String id, Time time) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "UPDATE Deliveries SET DELIVER_TIME =? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setTime(1,time);
            pst.setString(2,id);

            pst.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static void updateDriverId(String id, String driverId) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "UPDATE Deliveries SET DRIVER_ID =? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,driverId);
            pst.setString(2,id);

            pst.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean checkDriverForDel(String id,Date date,String driver) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql1 = "SELECT * From Deliveries WHERE ID<>? AND ((DELIVERY_DATE=? AND DRIVER_ID=?)) ";
            PreparedStatement pst1 = conn.prepareStatement(sql1);
            pst1.setString(1,id);
            pst1.setDate(2,date);
            pst1.setString(3,driver);

            ResultSet results1 = pst1.executeQuery();
            if(results1.next()==false)
                return false;
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public static void updateDelWeight(String id, double weight) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "UPDATE Deliveries SET WEIGHT =? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setDouble(1,weight);
            pst.setString(2,id);

            pst.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }
    public static boolean checkTruckForDel(String id,Date date,String truck) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql1 = "SELECT * From Deliveries WHERE ID<>? AND ((DELIVERY_DATE=? AND TRUCK_ID=?)) ";
            PreparedStatement pst1 = conn.prepareStatement(sql1);
            pst1.setString(1,id);
            pst1.setDate(2,date);
            pst1.setString(3,truck);

            ResultSet results1 = pst1.executeQuery();
            if(results1.next()==false)
                return false;
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public static void updateTruckID(String id, String  truck) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "UPDATE Deliveries SET TRUCK_ID =? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,truck);
            pst.setString(2,id);

            pst.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static void removeOrderAndLocation(String id, int locationId, int orderId) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql1 = "DELETE FROM OrdersForDelivery WHERE DELIVERY_ID=? AND ORDER_ID=? ";
            PreparedStatement pst = conn.prepareStatement(sql1);
            pst.setString(1,id);
            pst.setInt(2,orderId);
            pst.executeUpdate();

            String sql2 = "DELETE FROM LocationsForDelivery WHERE DELIVERY_ID=? AND LOCATION_ID=?";
            PreparedStatement pst1 = conn.prepareStatement(sql2);
            pst1.setString(1,id);
            pst1.setInt(2,locationId);
            pst1.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static void addOrderAndLocation(String id, int locationId, int orderId) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql1 = "INSERT INTO OrdersForDelivery VALUES (?,?) ";
            PreparedStatement pst = conn.prepareStatement(sql1);
            pst.setString(1,id);
            pst.setInt(2,orderId);
            pst.executeUpdate();

            String sql2 = "INSERT INTO LocationsForDelivery VALUES (?,?)";
            PreparedStatement pst1 = conn.prepareStatement(sql2);
            pst1.setString(1,id);
            pst1.setInt(2,locationId);
            pst1.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static void updateStatus(String id,String status) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "UPDATE Deliveries SET STATUS =? WHERE ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,status);
            pst.setString(2,id);

            pst.executeUpdate();

        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean checkOrder(int id) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql1 = "SELECT * From OrdersForDelivery WHERE ORDER_ID=? AND DELIVERY_ID in(SELECT ID FROM Deliveries WHERE STATUS=InTransit OR STATUS=Delivered)";
            PreparedStatement pst1 = conn.prepareStatement(sql1);
            pst1.setInt(1,id);

            ResultSet results1 = pst1.executeQuery();
            if(results1.next()==false)
                return false;
        } catch (Exception e) {
            throw e;
        }
        return true;
    }
        public static void printTargetLocation(String id) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From LocationsForDelivery WHERE DELIVERY_ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,id);
            ResultSet results = pst.executeQuery();
            System.out.print("target locations: [");
            while (results.next()) {
                int location=results.getInt(2);
                System.out.print(location+" ");
            }
            System.out.print("]");

            System.out.println();

        } catch (Exception e) {
            throw e;
        }
    }

    public static void printOrdersForDel(String id) throws SQLException {
        try (Connection conn = Repo.openConnection()) {
            String sql = "SELECT * From OrdersForDelivery WHERE DELIVERY_ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,id);
            ResultSet results = pst.executeQuery();
            System.out.print("orders: [");
            while (results.next()) {
                int orders=results.getInt(2);
                System.out.print(orders+" ");
            }
            System.out.print("]");

            System.out.println();

        } catch (Exception e) {
            throw e;
        }
    }


}
