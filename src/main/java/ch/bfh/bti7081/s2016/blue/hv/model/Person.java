package ch.bfh.bti7081.s2016.blue.hv.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
public class Person extends BaseEntity {

    @Column(name = "first_name", nullable = false, length = 100)
    @Size(min = 1, max = 100)
    private String firstname;

    @Column(name = "last_name", nullable = false, length = 100)
    @Size(min = 1, max = 100)
    private String lastname;

    @Column(name = "BIRTHDAY")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date birthday;

    @OneToOne(optional = false)
    @JoinColumn(name = "Contact_Id", unique = true, nullable = false, updatable = false)
    private Contact contact;

    public Date getBirthday() {
	return birthday;
    }

    public void setBirthday(Date birthday) {
	this.birthday = birthday;
    }

    public Contact getContact() {
	return contact;
    }

    public void setContact(Contact contact) {
	this.contact = contact;
    }

    public String getFirstname() {
	return firstname;
    }

    public void setFirstname(String firstname) {
	this.firstname = firstname;
    }

    public String getLastname() {
	return lastname;
    }

    public void setLastname(String lastname) {
	this.lastname = lastname;
    }
}
