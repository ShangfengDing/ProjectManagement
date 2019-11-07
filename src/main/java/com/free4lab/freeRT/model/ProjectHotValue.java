package com.free4lab.freeRT.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "project_hot_value", schema = "freereport", catalog = "")
public class ProjectHotValue implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer projectid;
    private Integer hotvalue;
    private Timestamp time;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "projectid", nullable = false)
    public Integer getProjectid() {
        return projectid;
    }

    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    @Basic
    @Column(name = "hotvalue", nullable = true)
    public Integer getHotvalue() {
        return hotvalue;
    }

    public void setHotvalue(Integer hotvalue) {
        this.hotvalue = hotvalue;
    }

    @Basic
    @Column(name = "time", nullable = false)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectHotValue that = (ProjectHotValue) o;

        if (id != that.id) return false;
        if (projectid != that.projectid) return false;
        if (hotvalue != null ? !hotvalue.equals(that.hotvalue) : that.hotvalue != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + projectid;
        result = 31 * result + (hotvalue != null ? hotvalue.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }
}
