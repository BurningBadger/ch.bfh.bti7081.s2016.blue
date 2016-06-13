//package ch.bfh.bti7081.s2016.blue.hv.entities;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import javax.persistence.*;
//
//@Entity(name = "Calendar")
//@Table(name = "calendars")
//public class Calendar extends BaseEntity {
//
//    private static final long serialVersionUID = -1273463230343505676L;
//
//    @Temporal(value = TemporalType.DATE)
//    @Column(name = "meetingDate")
//    private Date meetingDate;
//
//    @Temporal(value = TemporalType.TIME)
//    @Column(name = "Time_From")
//    private Date timeFrom;
//
//    @Temporal(value = TemporalType.TIME)
//    @Column(name = "Time_To")
//    private Date timeTo;
//
//    @OneToOne(mappedBy = "calendar")
//    private VisitEvent visitEvent;
//
//    public Date getTimeTo() {
//	return timeTo;
//    }
//
//    public String getFormattedTimeTo() {
//	DateFormat timeFormat = new SimpleDateFormat("HH:mm");
//	return timeFormat.format(this.getTimeTo());
//    }
//
//    public String getFormattedTimeFrom() {
//	DateFormat timeFormat = new SimpleDateFormat("HH:mm");
//	return timeFormat.format(this.getTimeFrom());
//    }
//
//    public String getFormattedMeetingDate() {
//	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//	return dateFormat.format(this.getMeetingDate());
//    }
//
//    public void setTimeTo(Date timeTo) {
//	this.timeTo = timeTo;
//    }
//
//    public Date getTimeFrom() {
//	return timeFrom;
//    }
//
//    public void setTimeFrom(Date timeFrom) {
//	this.timeFrom = timeFrom;
//    }
//
//    public Date getMeetingDate() {
//	return meetingDate;
//    }
//
//    public void setMeetingDate(Date meetingDate) {
//	this.meetingDate = meetingDate;
//    }
//
//}