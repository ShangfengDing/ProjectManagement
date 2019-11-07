package com.free4lab.freeRT.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by yph on 17-3-31.
 */

@Entity
@Table(name = "report")
public class Report implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    private User user;

    @Column(name = "time", nullable = false)
    private Timestamp time;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "state", nullable = false)
    private int state;

    @Column(name = "description")
    private String description;

    @Column(name = "attachment")
    private String attachment;

    @OneToMany(mappedBy = "report", fetch = FetchType.LAZY)
    private Collection<ReportPermission> reportPermissions;

    @OneToMany(mappedBy = "report", fetch = FetchType.LAZY)
    private Collection<ReportComment> reportComments;

    public Report() {

    }
    public Report(User user, Timestamp time, String name, int state, String description, String attachment, Collection<ReportPermission> reportPermissions, Collection<ReportComment> reportComments) {
        this.user = user;
        this.time = time;
        this.name = name;
        this.state = state;
        this.description = description;
        this.attachment = attachment;
        this.reportPermissions = reportPermissions;
        this.reportComments = reportComments;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttachment() {
        return attachment;
    }
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Collection<ReportPermission> getReportPermissions() {
        return reportPermissions == null ? new HashSet<ReportPermission>() : reportPermissions;
    }
    public void setReportPermissions(Collection<ReportPermission> reportPermissions) {
        this.reportPermissions = reportPermissions;
    }

    public Collection<ReportComment> getReportComments() {
        return reportComments;
    }
    public void setReportComments(Collection<ReportComment> reportComments) {
        this.reportComments = reportComments;
    }

}
