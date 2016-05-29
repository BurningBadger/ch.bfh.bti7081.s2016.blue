package ch.bfh.bti7081.s2016.blue.hv.entities;

import java.util.Set;

import javax.persistence.*;

@Entity(name = "VisitEvent")
@Table(name = "visit_events")
public class VisitEvent extends BaseEntity {

    private static final long serialVersionUID = -3616824349353093546L;

    @ManyToOne
    private Visit visit;

    @OneToOne
    @JoinColumn(name = "calendar", unique = true, nullable = false, updatable = false)
    private Calendar calendar;

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

    public Calendar getCalendar() {
	return calendar;
    }

    public void setCalendar(Calendar calendar) {
	this.calendar = calendar;
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
}
