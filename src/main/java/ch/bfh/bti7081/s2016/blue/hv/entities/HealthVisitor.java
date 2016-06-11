package ch.bfh.bti7081.s2016.blue.hv.entities;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Set;

import javax.persistence.*;

@Entity(name = "HealthVisitor")
@Table(name = "health_visitors")
public class HealthVisitor extends Person {

    private static final long serialVersionUID = -4737237051107455291L;

    @Column(name = "user_name", length = 255, nullable = false)
    private String userName;

    @Column(name = "password", length = 512, nullable = false)
    private String password;

    @OneToMany(mappedBy = "visitor")
    private Set<Visit> visits;

    public Set<Visit> getVisits() {
	return visits;
    }

    public void setVisits(Set<Visit> visits) {
	this.visits = visits;
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = DigestUtils.md5Hex(password);
    }
}
