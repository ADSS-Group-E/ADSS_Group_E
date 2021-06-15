package BusinessLayer;

import BusinessLayer.Inventory.Controllers.ProductController;
import BusinessLayer.Inventory.Controllers.ReportController;
import BusinessLayer.Supplier.SupplierController;
import BusinessLayer.Workers_Transport.DeliveryPackage.Order;
import BusinessLayer.Workers_Transport.DeliveryPackage.OrderController;
import PresentationLayer.Inventory.DataTransferObjects.ProductDTO;

import java.util.Dictionary;
import java.util.HashMap;

public class AcceptOrderHandler {
    private final ProductController pCont;
    private final OrderController oCont = OrderController.getInstance();

    public AcceptOrderHandler(ProductController pCont) {
        this.pCont = pCont;
    }

    public boolean checkOrderExists(int orderId){
        try{
            oCont.getOrder(orderId);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean checkOrderHasItem(int orderId, int productId){
        try{
            Order order = oCont.getOrder(orderId);
            HashMap<Integer,Integer> items = new HashMap<>(order.getItems());
            if (items.containsKey(productId))
                return true;
            else
                return false;
        }
        catch (Exception e){
            return false;
        }
    }

    // Dismissals is a map between a product and the amount which needs to be dismissed from the order
    public void acceptOrder(int orderId, HashMap<Integer, Integer> dismissals) throws Exception {
        Order order = oCont.getOrder(orderId);
        pCont.acceptOrder(order,dismissals);
    }
}
