package BusinessLayer.Workers_Transport.DeliveryPackage;

import PresentationLayer.Supplier.DataTransferObjects.OrderDTO;
import PresentationLayer.Supplier.DataTransferObjects.SupplierItemDTO;

import java.util.*;

public class Order {

    private int id;
    private Map<Integer, Integer> items;
    private String supplierId;
    private int locationId;
    private double totalWeight;

    public Order(int id, Map<Integer, Integer> items, String supplierId, int locationId, double totalWeight) {
        this.id = id;
        this.items = items;
        this.supplierId = supplierId;
        this.locationId = locationId;
        this.totalWeight = totalWeight;
    }

    public Order(OrderDTO dto){
        items = new HashMap<>();
        this.id=dto.getId();
        ArrayList<SupplierItemDTO> supplierItemDTOS=dto.getOrderItems();
        for(SupplierItemDTO item : supplierItemDTOS){
            items.put(item.getId(), item.getQuantity());
        }
        this.supplierId=String.valueOf(dto.getOrderItems().get(0).getCompanyNumber());
        this.locationId=dto.getLocationID();
        this.totalWeight=dto.getWeight();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<Integer, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Integer, Integer> items) {
        this.items = items;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    @Override
    public String toString() {
        return "Order {" +
                "id='" + id + '\'' +
                ", items=" + items +
                ", supplierId='" + supplierId + '\'' +
                ", locationId='" + locationId + '\'' +
                ", weight=" + totalWeight +
                '}';
    }
}
