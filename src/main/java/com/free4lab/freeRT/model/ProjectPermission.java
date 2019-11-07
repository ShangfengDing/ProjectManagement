package com.free4lab.freeRT.model;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "project_permission")
public class ProjectPermission implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "projectid")
    private  Integer projectid;

    @Column(name = "userid")
    private Integer userid;

    @Column(name = "role")
    private  Integer role;
    //role 0代表在职成员，1表示管理员，2表示离职

public ProjectPermission(){}
public ProjectPermission(Integer projectid,Integer userid,Integer role){
    this.projectid = projectid;
    this.userid = userid;
    this.role = role;
}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
