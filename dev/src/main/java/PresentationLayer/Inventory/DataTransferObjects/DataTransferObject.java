package PresentationLayer.Inventory.DataTransferObjects;

public abstract class DataTransferObject {
    int id;

    public DataTransferObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
