package com.free4lab.freeRT.model;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="conversation")
public class Conversation implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "source_type", nullable = false)
    private Integer sourceType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_id", nullable = false)
    private User user;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "chat_id", nullable = false)
//    private Project project;

    @Column(name = "chat_id", nullable = false)
    private Integer chatId;

    @Column(name = "context")
    private String context;

    @Column(name = "time", nullable = false)
    private Timestamp time;

    @Column(name = "like_num")
    private Integer likenumber;

    @Column(name = "type")
    private String type;
    public final static String MESSAGE = "MESSAGE";
    public final static String LIKE = "LIKE";
    public final static String COMMENT = "COMMENT";
    public final static String STAR="STAR";
    public final static String DELETED="DELETED";
    public final static String DELETE="DELETE";

    @Column(name = "parent_id")
    private Integer parentId;

    public Conversation(){}

//    public Conversation(Integer sourceType, User user, Project project, String context, Timestamp time, Integer likenumber, String type, Integer parentId) {
//        this.sourceType = sourceType;
//        this.user = user;
//        this.project = project;
//        this.context = context;
//        this.time = time;
//        this.likenumber = likenumber;
//        this.type = type;
//        this.parentId = parentId;
//    }


    public Conversation(Integer sourceType, User user, Integer chatId, String context, Timestamp time, Integer likenumber, String type, Integer parentId) {
        this.sourceType = sourceType;
        this.user = user;
        this.chatId = chatId;
        this.context = context;
        this.time = time;
        this.likenumber = likenumber;
        this.type = type;
        this.parentId = parentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public Project getProject() {
//        return project;
//    }
//
//    public void setProject(Project project) {
//        this.project = project;
//    }


    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Integer getLikenumber() {
        return likenumber;
    }

    public void setLikenumber(Integer likenumber) {
        this.likenumber = likenumber;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public Integer getParentId() { return parentId; }

    public void setParentId(Integer parentId) { this.parentId = parentId; }

//    @Override
//    public String toString() {
//        return "Conversation{" +
//                "id=" + id +
//                ", sourceType=" + sourceType +
//                ", user=" + user +
//                ", project=" + project +
//                ", context='" + context + '\'' +
//                ", time=" + time +
//                ", likenumber=" + likenumber +
//                ", type='" + type + '\'' +
//                ", parentId=" + parentId +
//                '}';
//    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", sourceType=" + sourceType +
                ", user=" + user +
                ", chatId=" + chatId +
                ", context='" + context + '\'' +
                ", time=" + time +
                ", likenumber=" + likenumber +
                ", type='" + type + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}
