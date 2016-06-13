package ch.bfh.bti7081.s2016.blue.hv.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "VisitEvent")
@Table(name = "visit_events")
public class VisitEvent extends BaseEntity {

    private static final long serialVersionUID = -3616824349353093546L;

    @ManyToOne
    private Visit visit;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "date_from")
    private Date dateFrom;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "date_to")
    private Date dateTo;

    @OneToMany(mappedBy = "visitEvent")
    private Set<Report> visitReports;

    @OneToMany(mappedBy = "visitEvent")
    private Set<Note> visitNodes;

    public Visit getVisit() {
	return visit;
    }

    public void setVisit(Visit visit) {
	this.visit = visit;
    }

    public Set<Report> getVisitReports() {
	return visitReports;
    }

    public void setVisitReports(Set<Report> visitReports) {
	this.visitReports = visitReports;
    }

    public Set<Note> getVisitNodes() {
	return visitNodes;
    }

    public void setVisitNodes(Set<Note> visitNodes) {
	this.visitNodes = visitNodes;
    }

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
