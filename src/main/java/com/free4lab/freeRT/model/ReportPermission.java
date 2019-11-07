package com.free4lab.freeRT.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by yph on 17-4-12.
 */

@Entity
@Table(name = "report_permission")
public class ReportPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportid")
    private Report report;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectid")
    private Project project;

    public ReportPermission() {

    }
    public ReportPermission(Report report, Project project) {
        this.report = report;
        this.project = project;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Report getReport() {
        return report;
    }
    public void setReport(Report report) {
        this.report = report;
    }

    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }

}
