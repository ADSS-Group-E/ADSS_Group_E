package BusinessLayer.Inventory.DomainObjects;

public abstract class DomainObject {
    int id;

    public int getId() {
        return id;
    }

    public DomainObject(int id) {
        this.id = id;
    }
}
