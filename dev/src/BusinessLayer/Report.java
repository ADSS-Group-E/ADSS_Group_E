package BusinessLayer;

import sun.reflect.generics.tree.Tree;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Report {
    protected int rid;
    protected LocalDateTime created;
    protected ArrayList<String> records;

    public Report(int rid) {
        this.rid = rid;
        created = LocalDateTime.now();
        records = new ArrayList<>();
    }

    public String toString(){
        return headerRow() + "\n" + String.join("\n",records);
    }

    protected abstract String headerRow();
}
