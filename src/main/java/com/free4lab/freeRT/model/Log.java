package com.free4lab.freeRT.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "log")
public class Log implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "logId", unique = true, nullable = false)
    private Integer logId;

    @Column(name = "type")
    private String type;

    @Column(name = "id")
    private Integer id;

    @Column(name = "pid")
    private Integer pid;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "behavior")
    private String behavior;

    @Column(name = "userName")
    private String userName;

    @Column(name = "createTime")
    private Timestamp createTime;

    @Column(name = "pname")
    private String pname;


    public Log() {
    }

    public Log(String type, Integer id, Integer pid, Integer userId, String behavior, String userName, Timestamp createTime, String pname) {
        this.type = type;
        this.id = id;
        this.pid = pid;
        this.userId = userId;
        this.behavior = behavior;
        this.userName = userName;
        this.createTime = createTime;
        this.pname = pname;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
