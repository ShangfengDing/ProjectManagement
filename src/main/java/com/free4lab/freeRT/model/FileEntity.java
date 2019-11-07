package com.free4lab.freeRT.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "file", schema = "freereport", catalog = "")
public class FileEntity {
    private int id;
    private int source;
    private User user;
    private Project project;
    private String realname;
    private String url;
    private String description;
    private Timestamp time;
    //private User starUser;
    private int star;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "source")
    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Column(name = "realname")
    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Column(name = "star")
    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "star_id", nullable = true)
//    public User getStarUser() {
//        return starUser;
//    }
//
//    public void setStarUser(User starUser) {
//        this.starUser = starUser;
//    }

    @Override
    public String toString() {
        return "FileEntity{" +
                "id=" + id +
                ", source=" + source +
                ", user=" + user +
                ", project=" + project +
                ", realname='" + realname + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", time=" + time +
                '}';
    }
}
