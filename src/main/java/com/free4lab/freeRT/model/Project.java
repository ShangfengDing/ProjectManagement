package com.free4lab.freeRT.model;
/**
 * Created by J on 2017/3/30.
 */

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table (name="project")

public class Project implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "groupid", nullable = false)
    private Integer groupid;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "startime", nullable = false)
    private Timestamp startime;

    @Column(name = "managerid")
    private Integer manager;

    @Column(name = "target")
    private String target;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "extend1")
    private Integer state;

    @Column(name = "extend2")
    private String managername;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private Collection<ReportPermission> reportPermissions;

    public Project()
    {
    }

    public Project(Integer groupid, String name, String description, Timestamp startime,  String target,Integer state){
        this.groupid=groupid;
        this.name=name;
        this.description=description;
        this.startime=startime;
        this.target=target;
        this.state=state;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getStartime() {
        return startime;
    }

    public void setStartime(Timestamp startime) {
        this.startime = startime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getManager() {
        return manager;
    }

    public void setManager(Integer manager) {
        this.manager = manager;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getManagername() {
        return managername;
    }

    public void setManagername(String managername) {
        this.managername = managername;
    }

    public Collection<ReportPermission> getReportPermissions() {
        return reportPermissions == null ? new HashSet<ReportPermission>() : reportPermissions;
    }

    public void setReportPermissions(Collection<ReportPermission> reportPermissions) {
        this.reportPermissions = reportPermissions;
    }

}
