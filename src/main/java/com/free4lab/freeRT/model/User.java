package com.free4lab.freeRT.model;

import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="user")

public class User implements java.io.Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    @Column(name = "userid")
    private  Integer userid;
    @Column(name = "name")
    private  String name;
    @Column(name = "email")
    private  String email;
    @Column(name = "avatar")
    private  String avatar;


    public User(){}
    public User(Integer userid,String name,String email,String avatar){
        this.email = email;
        this.name = name;
        this.userid = userid;
        this.avatar = avatar;
    }
    public User(JSONObject json) throws JSONException {
        this.userid = json.getInt("userId");
        this.name = json.getString("name");
        this.email = json.getString("email");
        this.avatar = json.getString("avatar");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
