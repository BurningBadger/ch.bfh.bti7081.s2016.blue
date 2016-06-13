package ch.bfh.bti7081.s2016.blue.hv.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "settings")
public class Setting extends BaseEntity {

    private static final long serialVersionUID = -1485395372534568600L;

    @Column(name = "key", nullable = false, length = 100)
    @Size(min = 1, max = 100)
    private String key;

    @Column(name = "value", nullable = false, length = 100)
    @Size(min = 1, max = 100)
    private String value;

    @ManyToOne
    private HealthVisitor healthVisitor;

    public String getKey() {
	return key;
    }

    public void setKey(String key) {
	this.key = key;
    }

    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }

    public HealthVisitor getHealthVisitor() {
	return healthVisitor;
    }

    public void setHealthVisitor(HealthVisitor healthVisitor) {
	this.healthVisitor = healthVisitor;
    }

}
