package com.gael4j.Entity;
import com.gael4j.Gael.Annotations.PrivateData;

import javax.persistence.*;

/***
 *
 * @author Hugo Huang
 * Sampel User class with JPA annotation. We use this class as a test case.
 */
@PrivateData(schema = "2390finalsample")
@Entity
@Table(name = "users")
public class SampleUser {

    public SampleUser() {}

    public SampleUser(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Id
    @Column(name = "userid", unique=true, nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="username")
    private String name;

    @Column(name="age")
    private int age;

    private int ageWithoutColumn;

    public String getName() {
        return name;
    }

    public void setName(String userName) {
        this.name = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User: id=" + id + "; name=" + name + "; age=" + age + "; ageWithNoColumn=" + ageWithoutColumn;
    }
}
