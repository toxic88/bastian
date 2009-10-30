package de.bastian.db;

import java.io.Serializable;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "false")
public class User implements Serializable {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    @Column(allowsNull = "false")
    private String firstName;

    @Persistent
    @Column(allowsNull = "false")
    private String lastName;

    @Persistent
    @Column(allowsNull = "false")
    private byte[] password;

    public User(String firstName, String lastName, String password) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPassword(password);
    }

    public Long getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public byte[] getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = this.passwordToHash(password);
    }

    public String[] toArray() {
        return new String[]{ this.getId().toString(), this.getFirstName(), this.getLastName(), this.getPassword().toString() };
    }

    private byte[] passwordToHash(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            return md5.digest(password.getBytes());
        } catch(Exception e) {
            return null;
        }
    }

    public boolean checkPassword(String password) {
        return Arrays.equals(this.password, this.passwordToHash(password));
    }

}
