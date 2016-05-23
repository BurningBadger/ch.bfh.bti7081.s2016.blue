package ch.bfh.bti7081.s2016.blue.hv.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

@Entity(name = "Notes")
public class Note extends BaseEntity {

    @Column(name = "Text", nullable = false, length = 1000)
    @Size(min = 1, max = 1000)
    private String noteText;

    @ManyToOne
    private VisitEvent visitEvent;

    public String getNoteText() {
	return noteText;
    }

    public void setNoteText(String noteText) {
	this.noteText = noteText;
    }

    public VisitEvent getVisitEvent() {
	return visitEvent;
    }

    public void setVisitEvent(VisitEvent visitEvent) {
	this.visitEvent = visitEvent;
    }
}
