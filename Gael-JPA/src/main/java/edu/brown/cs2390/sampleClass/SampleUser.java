package edu.brown.cs2390.sampleClass;

import edu.brown.cs2390.annotations.PrivateData;

import javax.persistence.*;

/***
 *
 * @author Hugo Huang
 * Sampel User class with JPA annotation. We use this class as a test case.
 */
@PrivateData(column = {"id", "name"})
@Entity
@Table(name = "USERS", uniqueConstraints=
    @UniqueConstraint(columnNames = {"USER_ID", "USER_NAME"}))
public class SampleUser {

    public SampleUser() {}

    public SampleUser(String userName, String age) {
        this.userName =  userName;
        this.age = age;
    }

    @Id
    @Column(name = "USER_ID", unique=true, nullable=false)
    @TableGenerator(
            name = "sampleGen",
            table = "sampleTable",
            pkColumnName = "GEN_KEY",
            valueColumnName = "GEN_VALUE",
            pkColumnValue = "myId")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "sampleGen ")
    private Long id;

    @Column(name="USER_NAME")
    private String userName;

    @Column(name="AGE")
    private String age;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
