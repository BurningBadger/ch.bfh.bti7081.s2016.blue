package ch.bfh.bti7081.s2016.blue.hv.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.beanutils.BeanUtils;

@Entity(name = "Contact")
@Table(name = "Contacts")
public class Contact extends BaseEntity {

    @Column(name = "City", nullable = false, length = 100)
    @Size(min = 1, max = 100)
    private String city;

    @Column(name = "Street", nullable = false, length = 100)
    @Size(min = 1, max = 100)
    private String street;

    @Column(name = "Zip", nullable = false, length = 9)
    @Size(min = 5, max = 6)
    private String zip;

    @Column(name = "Phone_Number", nullable = false, length = 9)
    @Size(min = 5, max = 12)
    private String phoneNumber;

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public String getStreet() {
	return street;
    }

    public void setStreet(String street) {
	this.street = street;
    }

    public String getZip() {
	return zip;
    }

    public void setZip(String zip) {
	this.zip = zip;
    }

    public String getPhoneNumber() {
	return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
    }

    @Override
    public Contact clone() throws CloneNotSupportedException {
	try {
	    return (Contact) BeanUtils.cloneBean(this);
	}
	catch (Exception ex) {
	    throw new CloneNotSupportedException();
	}
    }

    // @Override
    // public String toString() {
    // return "Contact{" + "id=" + id + ", firstName=" + firstName
    // + ", lastName=" + lastName + ", phone=" + phone + ", email="
    // + email + ", birthDate=" + birthDate + '}';
    // }
    
}
