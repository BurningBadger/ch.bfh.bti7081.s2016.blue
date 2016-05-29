package ch.bfh.bti7081.s2016.blue.hv.entities;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity(name = "Prescription")
@Table(name = "prescriptions")
public class Prescription extends BaseEntity{

    @Valid
    @ManyToOne(optional = false)
    private Drug drug;

    @Valid
    @ManyToOne(optional = false)
    private Visit visit;

    @Column(nullable = false, length = 45)
    @Size(min = 1, max = 45)
    private String dose;

    @Column(nullable = false, length = 1)
    private int timesPerDay;

    @Column(name = "Monday")
    private boolean mo;

    @Column(name = "Tuesday")
    private boolean tu;

    @Column(name = "Wednesday")
    private boolean we;

    @Column(name = "Thursday")
    private boolean th;

    @Column(name = "Friday")
    private boolean fr;

    @Column(name = "Saturday")
    private boolean sa;

    @Column(name = "Sunday")
    private boolean so;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date prescriptionDate;

    public Drug getDrug() { return drug; }

    public void setDrug(Drug drug) { this.drug = drug; }

    public Visit getVisit() { return visit; }

    public void setVisit(Visit appointment) { this.visit = appointment; }

    public String getDose() { return dose; }

    public void setDose(String dose) { this.dose = dose; }

    public int getTimesPerDay() { return timesPerDay; }

    public void setTimesPerDay(int timesPerDay) { this.timesPerDay = timesPerDay; }

    public boolean isMo() { return mo; }

    public void setMo(boolean mo) { this.mo = mo; }

    public boolean isTu() { return tu; }

    public void setTu(boolean tu) { this.tu = tu; }

    public boolean isWe() { return we; }

    public void setWe(boolean we) { this.we = we; }

    public boolean isTh() { return th; }

    public void setTh(boolean th) { this.th = th; }

    public boolean isFr() { return fr; }

    public void setFr(boolean fr) { this.fr = fr; }

    public boolean isSa() { return sa; }

    public void setSa(boolean sa) { this.sa = sa; }

    public boolean isSo() { return so; }

    public void setSo(boolean so) { this.so = so; }

    public Date getPrescriptionDate() { return prescriptionDate == null ? null : new Date(prescriptionDate.getTime()); }

    public void setPrescriptionDate(Date givenTime) { this.prescriptionDate = givenTime == null ? null : new Date(prescriptionDate.getTime()); }
}