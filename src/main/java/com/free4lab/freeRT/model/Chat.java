package com.free4lab.freeRT.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="chat", schema = "freereport", catalog = "")
public class Chat implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "chat_id", unique = true, nullable = false)
    private int chatId;

    @Column(name = "chat_type", nullable = false)
    private String chatType;
    public final static String PROJECT = "PROJECT";
    public final static String USER = "USER";
    public final static String SELF = "SELF";

    @Column(name = "id", nullable = false)
    private int cid;

    @Column(name = "name")
    private String name;


    public Chat(){}

    public Chat(String chatType, int cid) {
        this.chatType = chatType;
        this.cid = cid;
    }

    public Chat(String chatType, int cid, String name) {
        this.chatType = chatType;
        this.cid = cid;
        this.name = name;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
