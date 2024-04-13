package org.pgs.postp.dto;

import org.pgs.postp.model.RoleModel;

import java.math.BigInteger;
import java.util.Set;

public class UserDTO {
    private Long userID;
    private String username;
    private String password;
    private String name;
    private String email;
    private BigInteger phone;
    private Set<RoleModel> roles;

    public UserDTO() {
    }

    public UserDTO(Long userID, String username, String password, String name, String email, BigInteger phone, Set<RoleModel> roles) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.roles = roles;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigInteger getPhone() {
        return phone;
    }

    public void setPhone(BigInteger phone) {
        this.phone = phone;
    }

    public Set<RoleModel> getRoles() {return roles;}

    public void setRoles(Set<RoleModel> roles) {this.roles = roles;}

    @Override
    public String toString() {
        return "UserDTO{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }
}
