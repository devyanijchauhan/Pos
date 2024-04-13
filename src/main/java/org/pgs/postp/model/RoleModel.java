package org.pgs.postp.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Roles")
public class RoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoleID")
    private Long roleID;

    @Column(name = "RoleName", unique = true, nullable = false)
    private String roleName;




    public RoleModel() {
    }

    public RoleModel(String roleName) {
        this.roleName = roleName;
    }


    public Long getRoleID() {
        return roleID;
    }

    public void setRoleID(Long roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


}
