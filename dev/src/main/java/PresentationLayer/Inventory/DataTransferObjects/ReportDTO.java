package PresentationLayer.Inventory.DataTransferObjects;

import java.time.LocalDateTime;

/**
 * This class represents the ReportDTO.
 * A DTO is an object that is used to encapsulate data and send it from one subsystem of an application to another.
 */

public class ReportDTO extends DataTransferObject{
    private int rid;
    private LocalDateTime created;
    private String tag;

    public ReportDTO(int rid, LocalDateTime created, String tag) {
        super(rid);
        this.rid = rid;
        this.created = created;
        this.tag = tag;
    }

    public int getRid() {
        return rid;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public String getTag() {
        return tag;
    }
}
