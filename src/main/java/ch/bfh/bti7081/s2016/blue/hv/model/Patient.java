package ch.bfh.bti7081.s2016.blue.hv.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "patients")
public class Patient extends BaseEntity {

    private static final long serialVersionUID = -4737237051107455291L;

    @Column(name = "first_name", length = 255, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 255, nullable = false)
    private String lastName;

    @Column(name = "phone", length = 15, nullable = false)
    private int phone;

    @Column(name = "email", length = 30, nullable = false)
    private String email;

    @Column(name = "address", length = 255, nullable = false)
    private String street;

    @Column(name = "city", length = 20, nullable = false)
    private String city;

//    //TODO
//    @Column(name = "birthday", length = 10, nullable = false)
//    Component birthday = new PopupDateField("Birthday");

    public Patient() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

//    public Component getBirthday() {
//        return birthday;
//    }
//
//    public void setBirthday(Component birthday) {
//        this.birthday = birthday;
//    }
}