package com.gael4j.Gael.JPA.Entity;
import com.gael4j.Gael.Annotations.PrivateData;

import javax.persistence.*;

/***
 *
 * @author Hugo Huang
 * Sampel User class with JPA annotation. We use this class as a test case.
 */
@PrivateData(column = {"id", "userName"})
@Entity
@Table(name = "users")
public class SampleUser {

    public SampleUser() {}

    public SampleUser(String userName, int age) {
        this.userName =  userName;
        this.age = age;
    }

    @Id
    @Column(name = "userid", unique=true, nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="username")
    private String userName;

    @Column(name="age")
    private int age;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User: id=" + id + "; name=" + userName + "; age=" + age;
    }
}
