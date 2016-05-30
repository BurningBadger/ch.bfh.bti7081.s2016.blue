package ch.bfh.bti7081.s2016.blue.hv.entities;

import java.util.Date;

import javax.persistence.*;

@Entity(name = "Calendar")
@Table(name = "calendars")
public class Calendar extends BaseEntity {

    @Temporal(value = TemporalType.DATE)
    @Column(name = "Date_From")
    private Date dateFrom;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "Date_To")
    private Date dateTo;

    public Date getDateFrom() {
	return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
	this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
	return dateTo;
    }

    public void setDateTo(Date dateTo) {
	this.dateTo = dateTo;
    }
}