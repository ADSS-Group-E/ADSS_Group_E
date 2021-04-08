package BusinessLayer;

public class InvalidReport extends Report{

    public InvalidReport(int rid) {
        super(rid);
    }

    @Override
    protected String headerRow() {
        return null;
    }

    @Override
    public String getType() {
        return "Invalids";
    }
}
