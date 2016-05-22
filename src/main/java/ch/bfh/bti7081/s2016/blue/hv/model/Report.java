package ch.bfh.bti7081.s2016.blue.hv.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

@Entity(name = "Reports")
public class Report extends BaseEntity {

    @Column(name = "Title", nullable = false, length = 100)
    @Size(min = 1, max = 100)
    private String title;

    @Column(name = "Text", nullable = false, length = 1000)
    @Size(min = 1, max = 1000)
    private String reportText;

    @ManyToOne
    private VisitEvent visitEvent;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReportText() {
        return reportText;
    }

    public void setReportText(String reportText) {
        this.reportText = reportText;
    }

    public VisitEvent getVisitEvent() {
        return visitEvent;
    }

    public void setVisitEvent(VisitEvent visitEvent) {
        this.visitEvent = visitEvent;
    }
}

