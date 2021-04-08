package BusinessLayer;

import java.time.LocalDateTime;

public class StockReport extends Report {
    private int rid;
    private LocalDateTime created;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public StockReport(int rid, LocalDateTime created, LocalDateTime startDate, LocalDateTime endDate) {
        this.rid = rid;
        this.created = created;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getRid() {
        return rid;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public
}
