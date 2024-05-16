/*Task 4
Java & SQL task: Create a new model class in Java that would map the input fields you
created in the first task. Make sure to have all the field types similar. If you are going to use
Hibernate JPA, please make sure you use the proper adnotations. Also, please write the
SQL code necessary for the creation of a table that follows the rules mentioned above.
NB: Don't worry if the editor is set for Java, I don't search for SQL sintax perfection:)*/

package com.example.eksamen2023part2;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "citizens")
public class Citizen {
    private String firstname;
    private String lastname;
    private LocalDate birthday;
    private String ssn;
    private String phone;
    private String email;
    private String city;
    private String street;

    @Id
    @GeneratedValue
    private Long id;

    public Citizen() {

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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        Period age = Period.between(LocalDate.now(),getBirthday());
        return age.getYears();
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public Long getId() {
        return id;
    }
}
