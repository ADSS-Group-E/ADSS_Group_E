package BusinessLayer;

public class Category {
    private int cid;
    private String name;
    private Category superCategory = null;

    public Category(int cid, String name) {
        this.cid = cid;
        this.name = name;
    }

    public Category(int cid, String name, Category superCategory) {
        this.cid = cid;
        this.name = name;
        this.superCategory = superCategory;
    }
}
